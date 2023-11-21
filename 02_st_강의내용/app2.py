import streamlit as st

def main() :
    st.title('웹 대시보드')

    print('웹 대시보드')

    st.text('웹 대시보드 개발하기')

    st.header('이 영역은 헤더 영역')

    st.subheader('이 영역은 서브 헤더 영역')

    st.write('안녕하세요')


    st.success('성공했을때의 메세지를 보여줄때')

    st.warning('경고 메세지를 보여줄때')

    st.info('정보성 메세지를 보여줄때')

    st.error('문제가 발생했음을 보여주고 싶을때')


if __name__ == '__main__' :
    main()


