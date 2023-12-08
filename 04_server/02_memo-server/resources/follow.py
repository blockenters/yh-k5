from flask import request
from flask_jwt_extended import jwt_required
from flask_restful import Resource
from mysql.connector import Error

from flask_jwt_extended import get_jwt_identity

from mysql_connection import get_connection

class FollowResource(Resource) :
    @jwt_required()
    def post(self, followee_id) :

        user_id = get_jwt_identity()

        if followee_id == user_id :
            return {'error' : '내 자신은 팔로우 할수없습니다.'}, 400

        try : 
            connection = get_connection()

            query = '''insert into follow
                    (followerId, followeeId)
                    values
                    ( %s, %s);'''
            record = (user_id, followee_id)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()
            cursor.close()
            connection.close()
        except Error as e:
            print(e)
            cursor.close()
            connection.close()
            return {'error' : str(e)}, 500

        return {'result' : 'success'}, 200



    @jwt_required()
    def delete(self, followee_id):
        user_id = get_jwt_identity()

        try : 
            connection = get_connection()
            query = '''delete from follow
                    where followerId = %s and followeeId = %s;'''
            record = (user_id, followee_id)
            cursor = connection.cursor()
            cursor.execute(query, record)
            connection.commit()
            cursor.close()
            connection.close()
        except Error as e:
            print(e)
            cursor.close()
            connection.close()
            return {'error' : str(e)}, 500

        return {'result' : 'success'}, 200





