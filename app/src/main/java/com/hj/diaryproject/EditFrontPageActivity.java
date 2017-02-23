package com.hj.diaryproject;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.diaryproject.db.PageFacade;
import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;

import java.util.Date;

public class EditFrontPageActivity extends AppCompatActivity implements View.OnClickListener {

    // 뒷면
    private String mTitleEdittext = "";
    private String mContentEdittext = "";
    // 앞면(해당 액티비티)
    private ImageView mPictureImageview;
    private TextView mCommentTextview;
    private long mId = -1;

    private static final int MOVE_BACKPAGE = 1000;

    private static final int SELECT_PICTRUE = 1001;
    private Uri mSelectedImageUri;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_front_page);

        // 레이아웃의 이미지뷰 연결
        mPictureImageview = (ImageView) (findViewById(R.id.picture_imageview));
        // 레이아웃의 버튼 클릭시 온클릭메서드 소환
        findViewById(R.id.get_picture_button).setOnClickListener(this);

        // 코멘트 텍스트뷰 연결
        mCommentTextview = (TextView) findViewById(R.id.comment_textview);
        mCommentTextview.setText(getNowTime());

        if (getIntent() != null) {
            if (getIntent().hasExtra("page")) {
                mId = getIntent().getLongExtra("id", -1);

                Page page = (Page) getIntent().getSerializableExtra("page");
                mTitleEdittext = page.getTitle();
                mContentEdittext = page.getContent();

                //TODO 수정시 기존의 사진 뿌려주기
//                mPictureImageview.setImageURI(Uri.parse(page.getImage()));
                Glide.with(this).load(page.getImage()).into(mPictureImageview);
                mSelectedImageUri = Uri.parse(page.getImage());

                mCommentTextview.setText(page.getComment());
            }
        }

    }

    // TODO comment 부분
    // 현재 년월일 - String으로 가져오기

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getNowTime() {

        SimpleDateFormat fm1 = new SimpleDateFormat("yyyy.MM.dd");
        String date = fm1.format(new Date());

        return date;
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
                if (getIntent().hasExtra("page")) {
                    intent.putExtra("title", mTitleEdittext);
                    intent.putExtra("content", mContentEdittext);
                    intent.putExtra("id", mId);
                }
                startActivityForResult(intent, MOVE_BACKPAGE);
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_cancle:
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(0, 0);
                return true;

            case R.id.action_save:
                //TODO
                // mSelectedImageUri가 null일 때 = 아무런 사진도 선택되지 않았을 때
                // 아래 onactivitresult의 save 부분도 마찬가지의 오류 발생
                save();
                finish();
                overridePendingTransition(0, 0);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void save() {
        Intent intent = new Intent();
        intent.putExtra("title", mTitleEdittext);
        intent.putExtra("content", mContentEdittext);

        // TODO 이미지가 선택되지 않았을경우
        if (mSelectedImageUri == null) {
            intent.putExtra("image", "nothing");
        } else {
            intent.putExtra("image", mSelectedImageUri.toString());
        }
        intent.putExtra("comment", mCommentTextview.getText().toString());
        intent.putExtra("id", mId);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);
    }

    // 사진앨범 or 뒷 페이지에 갔다가 돌아올 때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // SELECT_PICTRUE 에서 돌아온 값
        if (requestCode == SELECT_PICTRUE && resultCode == RESULT_OK && data.getData() != null) {
            mSelectedImageUri = data.getData();
            // TODO 이미지 크기 조정
//            mPictureImageview.setImageURI(mSelectedImageUri);

            // 이미지 뿌리는 코드 수정
            // Glide.with(parent.getContext()).load(page.getImage()).into(viewHolder.pictureImageView);
            Glide.with(this).load(mSelectedImageUri.toString()).into(mPictureImageview);

        }

        // MOVE_BACKPAGE 에서 돌아온 값
        if (requestCode == MOVE_BACKPAGE && resultCode == RESULT_OK) {
            boolean requestSave = data.getBooleanExtra("requestSave", false);

            // 앞장으로 되돌아가기 - 를 눌러 현재 페이지로 돌아옴
            mTitleEdittext = data.getStringExtra("title");
            mContentEdittext = data.getStringExtra("content");

            // 저장 - 을 눌러 현재 페이지로 돌아옴
            if (requestSave) {
                save();
                finish();
                overridePendingTransition(0, 0);
            }

        } else if (requestCode == MOVE_BACKPAGE && resultCode == RESULT_CANCELED) {
            // 취소 - 를 눌러 현재 페이지로 돌아옴
            setResult(RESULT_CANCELED);
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
