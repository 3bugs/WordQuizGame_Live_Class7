package com.example.wordquizgame;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends ActionBarActivity {

    private final String TAG = "GameActivity";
    private final int TOTAL_QUESTIONS = 5;

    private int difficulty;

    private ArrayList<String> fileNameList;
    private ArrayList<String> quizWordsList;
    private ArrayList<String> choiceWords;

    private String answerFileName;
    private int totalGuesses;
    private int score;
    private int numChoices;

    private TextView questionNumberTextView;
    private ImageView questionImageView;
    private TableLayout buttonTableLayout;
    private TextView answerTextView;

    private Random random;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        difficulty = intent.getIntExtra(MainActivity.DIFFICULTY_KEY, -1);

        Toast t = Toast.makeText(this, "ผู้ใช้เลือก " + String.valueOf(difficulty), Toast
                .LENGTH_LONG);
        t.show();

        Log.i(TAG, "ผู้ใช้เลือก " + String.valueOf(difficulty));

        switch (difficulty) {
            case 0:
                numChoices = 2;
                break;
            case 1:
                numChoices = 4;
                break;
            case 2:
                numChoices = 6;
                break;
            default:
                Log.e(TAG, "Invalid difficulty value!!!");
                break;
        }

        questionNumberTextView = (TextView) findViewById(R.id.questionNumberTextView);
        questionImageView = (ImageView) findViewById(R.id.questionImageView);
        buttonTableLayout = (TableLayout) findViewById(R.id.buttonTableLayout);
        answerTextView = (TextView) findViewById(R.id.answerTextView);

        fileNameList = new ArrayList<String>();
        quizWordsList = new ArrayList<String>();
        choiceWords = new ArrayList<String>();

        random = new Random();
        handler = new Handler();

        getImageFileName();
    }

    private void getImageFileName() {
        String[] categories = { "animals", "body", "colors", "numbers", "objects" };

        AssetManager assets = getAssets();

        for (String category : categories) {
            try {
                String[] fileNames = assets.list(category);

                for (String fileName : fileNames) {
                    fileNameList.add(fileName.replace(".png", ""));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "***** รายชื่อไฟล์ทั้งหมด *****");
        for (String fileName : fileNameList) {
            Log.i(TAG, fileName);
        }

        startQuiz();
    }

    private void startQuiz() {
        totalGuesses = 0;
        score = 0;
        quizWordsList.clear();

        while (quizWordsList.size() < TOTAL_QUESTIONS) {
            int randomIndex = random.nextInt(fileNameList.size());
            String fileName = fileNameList.get(randomIndex);

            if (quizWordsList.contains(fileName) == false) {
                quizWordsList.add(fileName);
            }
        }

        Log.i(TAG, "***** ชื่อไฟล์สำหรับตั้งโจทย์ที่สุ่มได้ *****");
        for (String fileName : quizWordsList) {
            Log.i(TAG, fileName);
        }

        loadNextQuestion();
    }

    private void loadNextQuestion() {
        answerTextView.setText(null);
        answerFileName = quizWordsList.remove(0);

        String msg = String.format("คำถามข้อที่ %d จากทั้งหมด %d ข้อ", score + 1, TOTAL_QUESTIONS);
        questionNumberTextView.setText(msg);

        loadQuestionImage();
        prepareChoiceWords();
    }

    private void loadQuestionImage() {
        String category = answerFileName.substring(0, answerFileName.indexOf('-'));
        String filePath = category + "/" + answerFileName + ".png";

        AssetManager assets = getAssets();
        InputStream stream;

        try {
            stream = assets.open(filePath);

            Drawable image = Drawable.createFromStream(stream, filePath);
            questionImageView.setImageDrawable(image);

        } catch (IOException e) {
            Log.e(TAG, "Error loading file: " + filePath);
            e.printStackTrace();
        }
    }

    private void prepareChoiceWords() {
        choiceWords.clear();

        while (choiceWords.size() < numChoices) {
            int randomIndex = random.nextInt(fileNameList.size());
            String randomWord = getWord(fileNameList.get(randomIndex));

            if (choiceWords.contains(randomWord) == false
                    && randomWord.equals(getWord(answerFileName)) == false) {
                choiceWords.add(randomWord);
            }
        }

        int randomIndex = random.nextInt(numChoices);
        choiceWords.set(randomIndex, getWord(answerFileName));

        Log.i(TAG, "***** คำศัพท์ตัวเลือกที่สุ่มได้ *****");
        for (String word : choiceWords) {
            Log.i(TAG, word);
        }
    }

    private String getWord(String fileName) {
        String word = fileName.substring(fileName.indexOf('-') + 1);
        return word;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
