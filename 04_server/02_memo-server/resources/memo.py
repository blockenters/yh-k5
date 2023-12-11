from flask_jwt_extended import get_jwt_identity, jwt_required
from flask_restful import Resource
from flask import request
import mysql.connector
from mysql.connector import Error

from mysql_connection import get_connection


class FollowMemoListResource(Resource):

    @jwt_required()
    def get(self):

        # 1. 클라이언트로부터 데이터를 받아온다. 

        ### query params 는, 딕셔너리로 받아오고,
        ### 없는 키값을 억세스해도 에러 발생하지 않도록
        ### 딕셔너리의 get 함수를 사용해서 데이터를 받아온다.

        offset = request.args.get('offset')
        limit = request.args.get('limit')

        user_id = get_jwt_identity()

        try :
            connection = get_connection()
            query = '''select m.*, u.nickname
                    from follow f
                    join memo m
                        on f.followeeId = m.userId
                    join user u 
                        on m.userId = u.id
                    where f.followerId = %s
                    order by date desc
                    limit '''+ offset +''', '''+ limit +''';'''
            record = (user_id, )
            cursor = connection.cursor(dictionary=True)
            cursor.execute(query, record)

            result_list = cursor.fetchall()
            print(result_list)

            cursor.close()
            connection.close()

        except Error as e:
            print(e)        
            return {'result':'fail', 'error':str(e)}, 500
       
        i = 0
        for row in result_list :
            result_list[i]['createdAt'] = row['createdAt'].isoformat()    
            result_list[i]['updatedAt'] = row['updatedAt'].isoformat()
            result_list[i]['date'] = row['date'].isoformat()     
            i = i + 1

        return {'result':'success' ,
                'count' : len(result_list),
                'items' : result_list}


class MemoResource(Resource) :

    @jwt_required()
    def put(self, memo_id) :

        data = request.get_json()
        userId = get_jwt_identity()

        try :
            connection = get_connection()

            query = '''update memo
                    set title = %s , date = %s , content = %s
                    where id = %s and userId = %s;'''
            
            record = (data['title'],
                      data['date'],
                      data['content'],
                      memo_id,
                      userId)
            
            cursor = connection.cursor()
            cursor.execute(query, record)

            connection.commit()

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail', 'error':str(e)}, 500
        
        return {'result':'success'}
    
    
    @jwt_required()
    def delete(self, memo_id) :

        user_id = get_jwt_identity()

        try :
            connection = get_connection()
            query = '''delete from memo 
                    where id = %s and userId = %s;'''
            record = (memo_id, user_id)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()
            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail', 'error':str(e)}, 500
        
        return {'result':'success'}


class MemoListResource(Resource) :
    
    @jwt_required()
    def post(self) :

        data = request.get_json()
        user_id = get_jwt_identity()

        try:
            connection = get_connection()
            query = '''insert into memo
                    (title, date, content, userId)
                    values
                    (%s, %s ,%s, %s);'''
            record = ( data['title'], 
                      data['date'],
                       data['content'],
                        user_id )
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail', 'error':str(e)}, 500

        return {'result' : 'success'}

    @jwt_required()
    def get(self) :

        user_id = get_jwt_identity()

        try :
            
            connection = get_connection()

            query = '''select *
                    from memo
                    where userId = %s
                    order by date desc;'''
            record = (user_id, )

            cursor = connection.cursor(dictionary=True)
            cursor.execute(query, record)

            result_list = cursor.fetchall()            

            cursor.close()
            connection.close()

        except Error as e:
            print(e)
            return {'result':'fail', 'error':str(e)}, 500
        
        i = 0
        for row in result_list :
            result_list[i]['createdAt'] = row['createdAt'].isoformat()    
            result_list[i]['updatedAt'] = row['updatedAt'].isoformat()
            result_list[i]['date'] = row['date'].isoformat()     
            i = i + 1

        return {'result' : 'success' , 
                'count' : len(result_list) , 
                'items' : result_list}

