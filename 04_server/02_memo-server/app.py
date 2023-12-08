from flask import Flask
from flask_jwt_extended import JWTManager
from flask_restful import Api
from config import Config
from resources.memo import MemoListResource, MemoResource

from resources.user import UserLoginResource, UserRegisterResource 

app = Flask(__name__)

# 환경변수 셋팅
app.config.from_object(Config)
# JWT 매니저 초기화 
jwt = JWTManager(app)

api = Api(app)

# 경로와 리소스를 연결한다.
api.add_resource( UserRegisterResource , '/user/register')
api.add_resource( UserLoginResource , '/user/login')
api.add_resource( MemoListResource , '/memo')
api.add_resource( MemoResource , '/memo/<int:memo_id>')

if __name__ == '__main__' :
    app.run()





