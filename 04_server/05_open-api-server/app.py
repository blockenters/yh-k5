from flask import Flask
from flask_jwt_extended import JWTManager 
from flask_restful import Api
from config import Config
from resources.naver import ChineseResource, NewsResource

app = Flask(__name__)

# 환경변수 셋팅
app.config.from_object(Config)
# JWT 매니저를 초기화 
jwt = JWTManager(app)

api = Api(app)

api.add_resource( ChineseResource , '/chinese')
api.add_resource( NewsResource , '/news')

if __name__ == '__main__':
    app.run()