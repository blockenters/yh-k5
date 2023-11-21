import streamlit as st 
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sb

def main() :
    st.title('차트 그리기 1')

    df = pd.read_csv('./data/iris.csv')

    st.dataframe( df )

    # petal_length 와 petal_width 의 관계를 차트로 그리시오.

    # matplotlib 이나 seaborn 인 경우.

    fig1 = plt.figure()
    plt.scatter(data= df, x='petal_length', y='petal_width')
    plt.title('Petal length vs width')
    plt.xlabel('tepal length')
    plt.ylabel('tepal width')
    st.pyplot(fig1)

    fig2 = plt.figure()
    sb.regplot(data= df, x='petal_length', y='petal_width')
    st.pyplot(fig2)

    # petal_length 로 히스토그램 그리기.
    fig3 = plt.figure()
    plt.hist(data= df, x='petal_length', rwidth=0.8, bins=20)
    st.pyplot(fig3)


    # 하나의 차트 영역에, 2개의 차트를 그리자.

    fig4 = plt.figure( figsize=(10,4) )

    plt.subplot(1, 2, 1)
    plt.hist(data= df, x='petal_length', rwidth=0.8, bins=10)

    plt.subplot(1, 2, 2)
    plt.hist(data= df, x='petal_length', rwidth=0.8, bins=20)

    st.pyplot(fig4)

    
    # 판다스의 차트를 이용하는 방법 

    fig6 = plt.figure()
    df['petal_length'].hist()
    st.pyplot(fig6)

    fig7 = plt.figure()
    df['species'].value_counts().plot(kind='bar')
    st.pyplot(fig7)

    fig8 = plt.figure()
    sb.countplot(data= df, x = 'species')
    st.pyplot(fig8)


    # plt, seaborn, 판다스의 차트 는
    # 차트 영역을 plt.figure 를 이용해서 변수저장하고,
    # st.pyplot 을 이용해서 차트를 그리면 된다.
 

if __name__ == '__main__' :
    main()



