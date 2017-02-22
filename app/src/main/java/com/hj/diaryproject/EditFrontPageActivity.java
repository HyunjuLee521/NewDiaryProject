package com.hj.diaryproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;

public class EditFrontPageActivity extends AppCompatActivity implements View.OnClickListener {

    private String mTitleEdittext = "";
    private String mContentEdittext = "";
    private ImageView mPictureImageview;

    private static final int MOVE_BACKPAGE = 1000;

    private static final int SELECT_PICTRUE = 1001;
    private Uri mSelectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_front_page);

        // 레이아웃의 이미지뷰 연결
        mPictureImageview = (ImageView) (findViewById(R.id.picture_imageview));
        // 레이아웃의 버튼 클릭시 온클릭메서드 소환
        findViewById(R.id.get_picture_button).setOnClickListener(this);

    }

    // 메뉴 연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_front_page, menu);
        return true;
    }

    // 메뉴(다음페이지, 취소, 저장) 클릭시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_nextpage:
                Intent intent = new Intent(this, EditBackPageActivity.class);
                startActivityForResult(intent, MOVE_BACKPAGE);
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_cancle:
                finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_save:
                PageManager pageManager = PageManager.newInstance();
                //TODO
                // mSelectedImageUri가 null일 때 = 아무런 사진도 선택되지 않았을 때
                // 아래 onactivitresult의 save 부분도 마찬가지의 오류 발생생
                 pageManager.create(new Page(4,
                        mTitleEdittext,
                        mContentEdittext,
                        mSelectedImageUri.toString()));

                finish();
                overridePendingTransition(0, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 사진앨범 or 뒷 페이지에 갔다가 돌아올 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SELECT_PICTRUE 에서 돌아온 값
        if (requestCode == SELECT_PICTRUE && resultCode == RESULT_OK && data.getData() != null) {
            mSelectedImageUri = data.getData();
            // TODO 이미지 크기 조정
            mPictureImageview.setImageURI(mSelectedImageUri);
        }

        // MOVE_BACKPAGE 에서 돌아온 값
        if (requestCode == MOVE_BACKPAGE && resultCode == RESULT_OK) {
            boolean requestSave = data.getBooleanExtra("requestSave", false);

            // 앞장으로 되돌아가기
            mTitleEdittext = data.getStringExtra("titleEdittext");
            mContentEdittext = data.getStringExtra("contentEdittext");

            // 저장
            if (requestSave) {
                PageManager pageManager = PageManager.newInstance();
                pageManager.create(new Page(4,
                        mTitleEdittext,
                        mContentEdittext,
                        mSelectedImageUri.toString()));


                finish();
                overridePendingTransition(0, 0);
            }

        } else if (requestCode == MOVE_BACKPAGE && resultCode == RESULT_CANCELED) {
            //취소
            finish();
            overridePendingTransition(0, 0);
        }
    }

    // 사진가져오기 버튼 눌렀을때
    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTRUE);

    }




}
