package com.block.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.block.quizapp.model.Quiz;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtQuiz;
    ProgressBar progressBar;
    TextView txtResult;
    Button btnTrue;
    Button btnFalse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtQuiz = findViewById(R.id.txtQuiz);
        progressBar = findViewById(R.id.progressBar);
        txtResult = findViewById(R.id.txtResult);
        btnTrue = findViewById(R.id.btnTrue);
        btnFalse = findViewById(R.id.btnFalse);

        // 퀴즈는, 문제와 정답으로 되어있다.
        // 문제는 문자열이고, 정답은  true / false 로 할거다.

        // 퀴즈 클래스를 설계한다.

        // 설계한 클래스에 데이터를 넣어준다.
        //  => 객체를 생성한다. => 메모리에 공간 확보
        setQuiz();

    }


    void setQuiz(){
        ArrayList<Quiz> quizArrayList = new ArrayList<>();

        Quiz q = new Quiz(R.string.q1, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q2, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q3, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q4, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q5, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q6, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q7, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q8, false);
        quizArrayList.add(q);

        q = new Quiz(R.string.q9, true);
        quizArrayList.add(q);

        q = new Quiz(R.string.q10, false);
        quizArrayList.add(q);
    }

}











