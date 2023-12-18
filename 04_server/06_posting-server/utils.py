from passlib.hash import pbkdf2_sha256

from config import Config

# 원문비밀번호를  단방향 암호화 하는 함수 
def hash_password(original_password) :
    original_password = original_password + Config.PASSWORD_SALT
    password=pbkdf2_sha256.hash(original_password)
    return password

# 유저가 로그인할때,
# 입력한 비밀번호가 암호화된 비번과 맞는지 
# 체크하는 함수 
def check_password(original_password, hashed_password):
    original_password = original_password + Config.PASSWORD_SALT
    check = pbkdf2_sha256.verify(original_password, hashed_password)
    return check

