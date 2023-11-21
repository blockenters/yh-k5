import streamlit as st

# 스트림릿 프레임워크는, main 함수가 있어야 한다.

def main():
    # st 는 웹 화면에 표시하는 역할을 한다.
    st.title('헬로우')

if __name__ == '__main__' :
    print(__name__)
    main()