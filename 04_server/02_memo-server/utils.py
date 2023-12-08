from passlib.hash import pbkdf2_sha256

from config import Config

# 원문 비밀번호를, 단방향 암호화 하는 함수 
def hash_password(original_passwod) :    
    password = original_passwod + Config.SALT
    password = pbkdf2_sha256.hash(password)
    return password

# 유저가 로그인할때, 입력한 비밀번호가 맞는지 체크하는 함수
def check_password(original_password, hashed_password) :    
    password = original_password + Config.SALT
    check = pbkdf2_sha256.verify(password , hashed_password)
    return check



