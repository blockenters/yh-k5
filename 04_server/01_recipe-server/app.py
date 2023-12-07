# flask 프레임워크를 이용한, Restful API 서버 개발

from flask import Flask
from flask_jwt_extended import JWTManager 
from flask_restful import Api
from config import Config

from resources.recipe import RecipeListResource, RecipePublishResource, RecipeResource
from resources.user import UserLoginResource, UserRegisterResource

app = Flask(__name__)

# 환경변수 셋팅
app.config.from_object(Config)
# JWT 매니저를 초기화 
JWTManager(app)


api = Api(app)

# API를 구분해서 실행시키는 것은,
# HTTP METHOD 와 URL 의 조합이다. 

# 경로(Path)와 리소스(API코드)를 연결한다.
api.add_resource( RecipeListResource , '/recipes' )
api.add_resource( RecipeResource  , '/recipes/<int:recipe_id>')
api.add_resource( RecipePublishResource, '/recipes/<int:recipe_id>/publish')
api.add_resource( UserRegisterResource , '/user/register')
api.add_resource( UserLoginResource , '/user/login')

if __name__ == '__main__':
    app.run()


