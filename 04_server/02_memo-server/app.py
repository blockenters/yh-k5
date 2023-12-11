from flask import Flask
from flask_restful import Api
from resources.follow import FollowResource
from resources.memo import FollowMemoListResource, MemoListResource, MemoResource

from resources.user import UserLoginResource, UserLogoutResource, UserRegisterResource, jwt_blocklist

from config import Config
from flask_jwt_extended import JWTManager


app = Flask(__name__)

print('app 변수 생성')

# 환경변수 셋팅
app.config.from_object(Config)

# JWT 매니저 초기화 
jwt = JWTManager(app)

# 로그아웃된 토큰으로 요청하는 경우! 이 경우는 비정상적인 경우
# 이므로, jwt 가 알아서 처리하도록 코드작성.
@jwt.token_in_blocklist_loader
def check_if_token_is_revoked(jwt_header, jwt_payload):
    jti = jwt_payload['jti']
    return jti in jwt_blocklist

api = Api(app)

api.add_resource(UserRegisterResource , '/user/register')
api.add_resource( UserLoginResource  , '/user/login')
api.add_resource( UserLogoutResource , '/user/logout')
api.add_resource( MemoListResource , '/memo')
api.add_resource( MemoResource, '/memo/<int:memo_id>')
api.add_resource( FollowResource , '/follow/<int:followee_id>')
api.add_resource( FollowMemoListResource , '/follow/memo')

if __name__ == '__main__' :
    app.run()

