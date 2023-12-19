from datetime import datetime
from flask import request
from flask_jwt_extended import get_jwt_identity, jwt_required
from flask_restful import Resource
from config import Config
from mysql_connection import get_connection
from mysql.connector import Error

import boto3

class PostingListResource(Resource) :

    @jwt_required()
    def post(self) :

        # 1 클라이언트로부터 데이터 받아온다.
        file = request.files.get('image')
        content = request.form.get('content')

        user_id = get_jwt_identity()

        # 2. 사진을 s3에 저장한다.
        if file is None :
            return {'error' : '파일을 업로드 하세요'}, 400
        

        # 파일명을 회사의 파일명 정책에 맞게 변경한다.
        # 파일명은 유니크 해야 한다. 

        current_time = datetime.now()

        new_file_name = current_time.isoformat().replace(':', '_') + str(user_id) + '.jpg'  

        # 유저가 올린 파일의 이름을, 
        # 새로운 파일 이름으로 변경한다. 
        file.filename = new_file_name

        s3 = boto3.client('s3',
                    aws_access_key_id = Config.AWS_ACCESS_KEY_ID,
                    aws_secret_access_key = Config.AWS_SECRET_ACCESS_KEY )

        try :
            s3.upload_fileobj(file, 
                              Config.S3_BUCKET,
                              file.filename,
                              ExtraArgs = {'ACL' : 'public-read' , 
                                           'ContentType' : 'image/jpeg'} )
        except Exception as e :
            print(e)
            return {'error' : str(e)}, 500
        
        # rekogintion 서비스를 이용해서
        # object detection 하여, 태그 이름을 가져온다.

        tag_list = self.detect_labels(new_file_name, Config.S3_BUCKET)

        print(tag_list)

        # DB의 posting 테이블에 데이터를 넣어야 하고,
        # tag_name 테이블과 tag 테이블에도 데이터를
        # 넣어줘야 한다.

        try :
            connection = get_connection()

            # 1. posting 테이블에 데이터를 넣어준다.
            query = '''insert into posting
                    (userId, imgUrl, content)
                    values
                    (%s, %s, %s);'''
            record = (user_id, 
                      Config.S3_LOCATION+new_file_name,
                      content)
            cursor = connection.cursor()
            cursor.execute(query, record)

            posting_id = cursor.lastrowid

            # 2. tag_name 테이블 처리를 해준다.
            #    리코그니션을 이용해서 받아온 label이,
            #    tag_name테이블에 이미 존재하면, 
            #    그 아이디만 가져오고,
            #    그렇지 않으면, 테이블에 인서트 한후에
            #    그 아이디를 가져온다. 

            for tag in tag_list :
                tag = tag.lower()
                query = '''select *
                        from tag_name
                        where name = %s;'''
                record = (tag , )
                
                cursor = connection.cursor(dictionary=True)
                cursor.execute(query, record)

                result_list = cursor.fetchall()

                # 태그가 이미 테이블에 있으면, 아이디만 가져오고
                if len(result_list) != 0 :
                    tag_name_id = result_list[0]['id']
                else :
                    # 태그가 테이블에 없으면, 인서트 한다.
                    query = '''insert into tag_name
                            (name)
                            values
                            (%s);'''
                    record = (tag, )
                    cursor = connection.cursor()
                    cursor.execute(query, record)

                    tag_name_id = cursor.lastrowid

                # 3. 위의 태그네임 아이디와, 포스팅 아이디를
                #    이용해서, tag 테이블에 데이터를 넣어준다.   
            
                query = '''insert into tag
                        (postingId, tagNameId)
                        values
                        (%s, %s);'''
                record = (posting_id, tag_name_id)

                cursor = connection.cursor()
                cursor.execute(query, record)                            
            
            # 트랜잭션 처리를 위해서
            # 커밋은 테이블 처리를 다 하고나서
            # 마지막에 한번 해준다.
            # 이렇게 해주면, 중간에 다른 테이블에서
            # 문제가 발생하면, 모든 테이블이 원상복구(롤백)
            # 된다. 이 기능을 트랜잭션 이라고 한다.
            connection.commit()

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            cursor.close()
            connection.close()
            return {'error' : str(e)}, 500


        return {'result' : 'success'}, 200
    



    def detect_labels(self, photo, bucket):

        client = boto3.client('rekognition',
                              'ap-northeast-2',
                              aws_access_key_id=Config.AWS_ACCESS_KEY_ID,
                              aws_secret_access_key=Config.AWS_SECRET_ACCESS_KEY)

        response = client.detect_labels(Image={'S3Object':{'Bucket':bucket,'Name':photo}},
        MaxLabels=5,
        # Uncomment to use image properties and filtration settings
        #Features=["GENERAL_LABELS", "IMAGE_PROPERTIES"],
        #Settings={"GeneralLabels": {"LabelInclusionFilters":["Cat"]},
        # "ImageProperties": {"MaxDominantColors":10}}
        )

        print('Detected labels for ' + photo)
        print()

        label_list = []
        for label in response['Labels']:
            print("Label: " + label['Name'])
            print("Confidence: " + str(label['Confidence']))

            if label['Confidence'] >= 90 :
                label_list.append(label['Name'])
            

        return label_list

