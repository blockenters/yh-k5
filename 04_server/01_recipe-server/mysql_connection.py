import mysql.connector
# 파이썬으로 MySQL에 접속할 수 있는 함수.
def get_connection() :
    connection = mysql.connector.connect(
        host = '',
        database = 'recipe_db',
        user = 'recipe_db_user',
        password = ''
    )
    return connection