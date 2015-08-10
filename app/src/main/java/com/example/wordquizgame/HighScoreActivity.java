package com.example.wordquizgame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.wordquizgame.db.DatabaseHelper;

public class HighScoreActivity extends ActionBarActivity {

    private final String TAG = "HighScoreActivity";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        listView = (ListView) findViewById(R.id.listView);

        showHighScore();
    }

    private void showHighScore() {
        String sqlSelect = "SELECT * FROM " + DatabaseHelper.TABLE_NAME + " ORDER BY " +
                DatabaseHelper.COL_SCORE + " DESC";

        Log.i(TAG, sqlSelect);

        Cursor cursor = database.rawQuery(sqlSelect, null);
        Log.i(TAG, "จำนวนแถวใน Cursor = " + cursor.getCount());

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] { DatabaseHelper.COL_SCORE, DatabaseHelper.COL_DIFFICULTY },
                new int[] { android.R.id.text1, android.R.id.text2 },
                0
        );

        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        database.close();
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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
