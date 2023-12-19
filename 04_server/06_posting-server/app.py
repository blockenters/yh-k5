from flask import Flask
from flask_jwt_extended import JWTManager 
from flask_restful import Api
from config import Config
from resources.follow import FollowResource
from resources.posting import PostingListResource
from resources.user import UserLoginResource, UserLogoutResource, UserRegisterResource, jwt_blocklist

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

api.add_resource( UserRegisterResource , '/user/register')
api.add_resource( UserLoginResource, '/user/login')
api.add_resource( UserLogoutResource , '/user/logout')
api.add_resource( PostingListResource , '/posting')
api.add_resource( FollowResource , '/follow/<int:followee_id>')

if __name__ == '__main__':
    app.run()





