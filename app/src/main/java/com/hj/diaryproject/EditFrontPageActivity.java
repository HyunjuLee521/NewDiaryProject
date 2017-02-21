package com.hj.diaryproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import com.hj.diaryproject.adapters.PageAdapter;
import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;

public class EditFrontPageActivity extends AppCompatActivity {

    private EditText mImageEdittext;
    private String mTitleEdittext = "";
    private String mContentEdittext = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_front_page);

        mImageEdittext = (EditText) findViewById(R.id.image_edittext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_front_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nextpage:
                Intent intent = new Intent(this, EditBackPageActivity.class);
                intent.putExtra("imageEdittext", mImageEdittext.getText().toString());
                startActivityForResult(intent, 1000);
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_cancle:
                finish();
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_save:
                PageManager pageManager = PageManager.newInstance();
                pageManager.create(new Page(4,
                        mTitleEdittext,
                        mContentEdittext,
                        mImageEdittext.getText().toString()));

                finish();
                overridePendingTransition(0, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            boolean isRequestSave = data.getBooleanExtra("isRequestSave", false);
            mTitleEdittext = data.getStringExtra("titleEdittext");
            mContentEdittext = data.getStringExtra("contentEdittext");

            if (isRequestSave) {
                PageManager pageManager = PageManager.newInstance();
                pageManager.create(new Page(4,
                        mTitleEdittext,
                        mContentEdittext,
                        mImageEdittext.getText().toString()));
                finish();
            }

        } else if (requestCode == 1000 && resultCode == RESULT_CANCELED) {
            finish();
        }
    }
}
