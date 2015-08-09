package com.example.wordquizgame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    private final String TAG = "MainActivity";

    private Button btnPlayGame, btnHighScore;
    private String[] diffLabel = {"ง่าย", "ปานกลาง", "ยาก"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnPlayGame = (Button) findViewById(R.id.playGameButton);
        btnPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("เลือกระดับความยากที่ต้องการ");
                dialog.setItems(diffLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG, "ผู้ใช้เลือก " + String.valueOf(which));

                        Intent intent = new Intent(MainActivity.this, GameActivity.class);
                        intent.putExtra("diff", which);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });

        btnHighScore = (Button) findViewById(R.id.highScoreButton);
        btnHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        MainActivity.this,
                        "ปุ่มคะแนนสูงสุด",
                        Toast.LENGTH_LONG
                ).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


/*
    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Toast t = Toast.makeText(MainActivity.this, "Hello Android", Toast.LENGTH_LONG);
            t.show();
        }
    }
*/
}
















