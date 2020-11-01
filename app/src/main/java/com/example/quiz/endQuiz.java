package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class endQuiz extends AppCompatActivity {
    private String total, attempts, skip, mark, timer, type;
    private TextView attempted, skipped, marked, covered, timertv, resume;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);
        getSupportActionBar().hide();
        init();
        getIntentData();
        setData();
    }

    private void getIntentData() {
        total = i.getStringExtra("total");
        attempts = i.getStringExtra("attempted");
        skip = i.getStringExtra("skipped");
        mark = i.getStringExtra("marked");
        type = i.getStringExtra("type");
        timer = i.getStringExtra("time");
    }

    private void init() {
        attempted = findViewById(R.id.attemptedTv);
        skipped = findViewById(R.id.skippedTv);
        marked = findViewById(R.id.markedTv);
        covered = findViewById(R.id.amountCovered);
        timertv = findViewById(R.id.timer);
        resume = findViewById(R.id.test);
        i = getIntent();
    }

    private void setData() {
        int num = ((Integer.parseInt(attempts) + Integer.parseInt(skip) + Integer.parseInt(mark)) / Integer.parseInt(total));
        Log.e("num", attempts);
        attempted.setText(attempts);
        skipped.setText(skip);
        marked.setText(mark);
        timertv.setText(timer);
        covered.setText(num * 100 + "% Covered");
        resume.setText(type);
    }

    @Override
    public void onBackPressed() {
//Overridden to do nothing.
    }

    public void play(View v) {
        if (resume.getText().toString().equalsIgnoreCase("resume test")) {
            super.onBackPressed();
        } else {
            resume.setText("Test Ended");
            Toast.makeText(getApplicationContext(), "Proceed to close Application", Toast.LENGTH_SHORT).show();
        }

    }

}