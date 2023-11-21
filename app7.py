import streamlit as st
import os
from PIL import Image
from datetime import datetime

# 1. 이미지파일 업로드
# 2. csv 파일 업로드 


# 디렉토리(폴더)명과 파일을 알려주면,
# 해당 디렉토리에 파일을 저장해 주는 함수!
def save_uploaded_file(directory, file) :
    # 1. 위의 directory 가 있는지 먼저 확인해서,
    #    없으면 디렉토리를 새로 생성하고, 
    #    그렇지 않으면 새로생성할 필요없다.
    if not os.path.exists(directory) : 
        os.makedirs(directory)

    # 2. 디렉토리가 존재하니까, 파일을 이 디렉토리 안에 저장한다.
    with open(os.path.join(directory, file.name) , 'wb') as f:
        f.write(file.getbuffer())
    
    # 3. 파일저장이 성공했으니까, 유저한테 웹화면에 보여주자.
    return st.success(  directory+'에 '+ file.name +'파일이 잘 저장되었습니다.' )


def main():
    st.title('파일 업로드 프로젝트')

    choice = st.sidebar.selectbox('메뉴' ,  ['Image' , 'CSV'] )

    if choice == 'Image' :
        st.subheader('이미지 파일 업로드')

        file = st.file_uploader('이미지파일을 업로드하세요', type= ['jpg', 'jpeg', 'png', 'webp'] )
    
        if file is not None :

            # 파일명을 일관성있게 관리할수 있도록
            # 회사에 들어가면, 파일명 작명 규칙대로 바꿔준다.

            # 현재시간을 조합하여 파일명을 만들면,
            # 유니크하게 파일명을 지을수 있다.

            current_time = datetime.now()

            print( current_time.isoformat().replace(':', '_').replace('.', '_') + '.jpg' )

            file.name = current_time.isoformat().replace(':', '_').replace('.', '_') + '.jpg'

            # 파일이 업로드 되었을때만, 파일 저장하는 코드를 작성한다.

            save_uploaded_file('tmp', file )

            # 웹 화면에 저장된 이미지파일을 보여주자.
            img = Image.open(file)
            st.image(img)

    
    elif choice == 'CSV' :
        st.subheader('CSV 파일 업로드')


if __name__ == '__main__' :
    main()