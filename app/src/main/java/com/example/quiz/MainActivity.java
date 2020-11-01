package com.example.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

   /* All String values are not translated in the Strings.xml file,
    but will be if the application is intended to be worked on further.
    Inappropriate String concatenations will be removed and proper comments added as need be.*/

    private ArrayList<questionmodel> questionBank = new ArrayList<>();
    private TextView timer, question, op1, op2, op3, op4, questionNumber, previous, next, progress, tv;
    private View op1main, op2main, op3main, op4main, op1num, op2num, op3num, op4num;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private CountDownTimer mCountDownTimer;
    private int score, attempted = 0, skipped = 0, marked = 0;
    private int questionstart = 0;
    private long mStartTimeInMillis;
    private long mTimeLeftInMillis = 600000;
    private long mEndTime;
    private String selectedAnswer = "null";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        init();
        questionSetup();
        tv.setMovementMethod(new ScrollingMovementMethod());
        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("time_left", mTimeLeftInMillis);
        editor.apply();
        startTimer();
        setlistener();
        if (questionstart < questionBank.size()) {
            start();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void start() {
        //  timerresume();
        selectedAnswer = "null";
        next.setText("Skip");
        resetBackground();
        progressBar.setMax(100);
        float num = (float) (questionstart) / questionBank.size();
        num *= 100;
        int numInt = (int) num;
        progress.setText(numInt + "%");
        progressBar.setProgress(numInt);
        Log.e("val", String.valueOf(num));
        if (questionBank.size() > 0 && questionstart < questionBank.size()) {
            question.setText(questionBank.get(questionstart).getQuestion());
            op1.setText(questionBank.get(questionstart).getOptionA());
            op2.setText(questionBank.get(questionstart).getOptionB());
            op3.setText(questionBank.get(questionstart).getOptionC());
            op4.setText(questionBank.get(questionstart).getOptionD());
            questionNumber.setText(questionstart + 1 + " of " + questionBank.size());

        } else {
            mCountDownTimer.cancel();
            Intent i = new Intent(MainActivity.this, endQuiz.class);
            i.putExtra("total", String.valueOf(questionBank.size()));
            i.putExtra("attempted", String.valueOf(attempted));
            i.putExtra("skipped", String.valueOf(skipped));
            i.putExtra("type", "End Test");
            i.putExtra("marked", String.valueOf(marked));
            i.putExtra("time", String.format(Locale.getDefault(), "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % 60));
            startActivity(i);
        }
    }

    private void init() {
        timer = findViewById(R.id.timerTv);
        question = findViewById(R.id.question);
        op1 = findViewById(R.id.op1);
        op2 = findViewById(R.id.op2);
        op3 = findViewById(R.id.op3);
        op4 = findViewById(R.id.op4);
        tv = findViewById(R.id.question);
        questionNumber = findViewById(R.id.questionnumber);
        progressBar = findViewById(R.id.progressbar);
        previous = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        op1main = (MaterialCardView) findViewById(R.id.op1mainlay);
        op2main = findViewById(R.id.op2mainlay);
        op3main = findViewById(R.id.op3mainlay);
        op4main = findViewById(R.id.op4mainlay);
        op1num = findViewById(R.id.op1num);
        op2num = findViewById(R.id.op2num);
        op3num = findViewById(R.id.op3num);
        op4num = findViewById(R.id.op4num);
        progress = findViewById(R.id.prog);

    }

    private void setlistener() {
        op1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                resetBackground();
                op1.setTextColor(Color.parseColor("#ffffff"));
                op1main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.blue));
                op1num.setBackgroundColor(Color.parseColor("#ffffff"));
                questionBank.get(questionstart).setAnswerSelected(op1.getText().toString());
                selectedAnswer = op1.getText().toString();
                next.setText("Next");
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                resetBackground();
                op2.setTextColor(Color.parseColor("#ffffff"));
                op2main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.blue));
                op2num.setBackgroundColor(Color.parseColor("#ffffff"));
                questionBank.get(questionstart).setAnswerSelected(op2.getText().toString());
                selectedAnswer = op2.getText().toString();
                next.setText("Next");
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                resetBackground();
                op3.setTextColor(Color.parseColor("#ffffff"));
                op3main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.blue));
                op3num.setBackgroundColor(Color.parseColor("#ffffff"));
                questionBank.get(questionstart).setAnswerSelected(op3.getText().toString());
                selectedAnswer = op3.getText().toString();
                next.setText("Next");
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                resetBackground();
                op4.setTextColor(Color.parseColor("#ffffff"));
                op4main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.blue));
                op4num.setBackgroundColor(Color.parseColor("#ffffff"));
                questionBank.get(questionstart).setAnswerSelected(op4.getText().toString());
                selectedAnswer = op4.getText().toString();
                next.setText("Next");
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (questionstart < questionBank.size()) {
                    if (next.getText().toString().equalsIgnoreCase("next")) {
                        if (selectedAnswer.equals(questionBank.get(questionstart).getAnswerSelected())) {
                            score += 1;
                            questionBank.get(questionstart).setPointawarded(1);

                        }
                        attempted += 1;
                    } else {
                        questionBank.get(questionstart).setSkipped(1);
                        skipped += 1;
                    }
                }

                questionstart += 1;
                if (questionstart < questionBank.size()) {
                    start();
                } else {
                    Intent i = new Intent(MainActivity.this, endQuiz.class);
                    Log.e("num", String.valueOf(attempted));
                    mCountDownTimer.cancel();
                    i.putExtra("total", String.valueOf(questionBank.size()));
                    i.putExtra("attempted", String.valueOf(attempted));
                    i.putExtra("skipped", String.valueOf(skipped));
                    i.putExtra("marked", String.valueOf(marked));
                    i.putExtra("type", "End Test");
                    i.putExtra("time", String.format(Locale.getDefault(), "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % 60));
                    startActivity(i);

                }
            }


        });
        previous.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (questionstart > 0) {
                    questionstart -= 1;
                    skipped -= questionBank.get(questionstart).getSkipped();
                    score -= questionBank.get(questionstart).getPointawarded();
                    attempted -= questionBank.get(questionstart).getPointawarded();
                    questionBank.get(questionstart).setSkipped(0);
                    questionBank.get(questionstart).setPointawarded(0);
                    start();
                } else {
                    Toast.makeText(getApplicationContext(), "This is the first question", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void resetBackground() {

        op1.setTextColor(Color.parseColor("#bdbdbd"));
        op1main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.white));
        op1num.setBackgroundColor(Color.parseColor("#eeeeee"));
        op2.setTextColor(Color.parseColor("#bdbdbd"));
        op2main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.white));
        op2num.setBackgroundColor(Color.parseColor("#eeeeee"));
        op3.setTextColor(Color.parseColor("#bdbdbd"));
        op3main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.white));
        op3num.setBackgroundColor(Color.parseColor("#eeeeee"));
        op4.setTextColor(Color.parseColor("#bdbdbd"));
        op4main.getBackground().setTint(MainActivity.this.getResources().getColor(R.color.white));
        op4num.setBackgroundColor(Color.parseColor("#eeeeee"));
    }

    public void pause(View v) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("time_left", mTimeLeftInMillis);
        editor.apply();
        mCountDownTimer.cancel();
        Intent i = new Intent(MainActivity.this, endQuiz.class);
        i.putExtra("total", String.valueOf(questionBank.size()));
        i.putExtra("attempted", String.valueOf(attempted));
        i.putExtra("skipped", String.valueOf(skipped));
        i.putExtra("marked", String.valueOf(marked));
        i.putExtra("type", "Resume Test");
        i.putExtra("time", String.format(Locale.getDefault(), "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mTimeLeftInMillis) % 60,
                TimeUnit.MILLISECONDS.toSeconds(mTimeLeftInMillis) % 60));
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mCountDownTimer.cancel();
        mTimeLeftInMillis = sharedPreferences.getLong("time_left", 0);
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("time_left", mTimeLeftInMillis);
        editor.apply();
        mCountDownTimer.cancel();
    }

    private void startTimer() {
        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Exam Finish", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
        timer.setText(timeLeftFormatted);
    }

    private void questionSetup() {
        questionBank.add(new questionmodel("Which of the following are not ionic compounds?" +
                "\n(I) KCl \t(II) HCl\t(III) CCl4\t(IV) NaCl", "(I) and (II)", "(II) and (III)", "(III) and (IV)", "(I) and (III)", "(I) and (III)"));
        questionBank.add(new questionmodel("Ethanol reacts with sodium and forms two products. These are?", "Sodium ethanoate and hydrogen", "Sodium ethanoate and oxygen", "Sodium ethoxide and hydrogen", "Sodium ethoxide and oxygen", "Sodium ethoxide and hydrogen"));
        questionBank.add(new questionmodel("Which of the following is classified as a metal?", "Ge", "As", "F", "V", "V"));
        questionBank.add(new questionmodel("The precipitate formed when barium chloride is treated with sulphuric acid is?", "BaS2O4", "BaSO3", "BaSO2", "BaSO4", "BaSO4"));
        questionBank.add(new questionmodel("Determine the oxidation number of carbon in K2CO3.", "0", "+2", "+4", "-2", "+4"));
        questionBank.add(new questionmodel("What is the total number of electrons in the correct Lewis dot formula of the sulfite ion?", "8", "24", "26", "30", "26"));

        questionBank.add(new questionmodel("The valence electrons of representative elements are?", "in s orbital only.", "located in the outermost occupied major energy level.", "located closest to the nucleus.", "located in the d orbital.", "located in the outermost occupied major energy level."));

    }
}