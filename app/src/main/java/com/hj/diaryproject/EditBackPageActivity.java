package com.hj.diaryproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class EditBackPageActivity extends AppCompatActivity {

    private EditText mTitleEdittext;
    private EditText mContentEdittext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_back_page);

        mTitleEdittext = (EditText) findViewById(R.id.title_edittext);
        mContentEdittext = (EditText) findViewById(R.id.content_edittext);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_back_page, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_backpage:
                Intent intent = new Intent(this, EditBackPageActivity.class);
                intent.putExtra("titleEdittext", mTitleEdittext.getText().toString());
                intent.putExtra("contentEdittext", mContentEdittext.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_cancle:
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_save:
                Intent intent2 = new Intent(this, EditBackPageActivity.class);
                intent2.putExtra("titleEdittext", mTitleEdittext.getText().toString());
                intent2.putExtra("contentEdittext", mContentEdittext.getText().toString());
                intent2.putExtra("requestSave", true);
                setResult(RESULT_OK, intent2);
                finish();
                overridePendingTransition(0, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
