# flask 프레임워크를 이용한, Restful API 서버 개발
from flask import Flask
from flask_jwt_extended import JWTManager 
from flask_restful import Api
from config import Config
from resources.movie import MovieListResource, MovieResource, MovieSearchResource
from resources.review import ReviewResource
from resources.user import UserLoginResource, UserRegisterResource, jwt_blocklist, UserLogoutResource


app = Flask(__name__)

# 환경변수 셋팅
app.config.from_object(Config)
# JWT 매니저를 초기화 
jwt = JWTManager(app)


# 로그아웃된 토큰으로 요청하는 경우,
# 실행되지 않도록 처리하는 코드.
@jwt.token_in_blocklist_loader
def check_if_token_is_revoked(jwt_header, jwt_payload): 
    jti = jwt_payload['jti']
    return jti in jwt_blocklist


api = Api(app)

# API를 구분해서 실행시키는 것은,
# HTTP METHOD 와 URL 의 조합이다. 

# 경로(Path)와 리소스(API코드)를 연결한다.

api.add_resource( UserRegisterResource , '/user/register')
api.add_resource( UserLoginResource , '/user/login')
api.add_resource( UserLogoutResource , '/user/logout')
api.add_resource( ReviewResource  , '/review')
api.add_resource( MovieResource , '/movie/<int:movie_id>')
api.add_resource( MovieListResource , '/movie')
api.add_resource( MovieSearchResource , '/movie/search')

if __name__ == '__main__':
    app.run()



