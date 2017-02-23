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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.diaryproject.models.Page;

import java.util.Date;

public class EditPageActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener {

    // 최상단 레이아웃
    private RelativeLayout mLayout;

    // 레이아웃 앞장
    private LinearLayout mFrontLayout;
    private ImageView mPictureImageview;
    private TextView mCommentTextview;

    // 레이아웃 뒷장 - Edit 모드
    private LinearLayout mBackLayout;
    private EditText mTitleEdittext;
    private EditText mContentEdittext;
    private Switch mEditSwitch;

    // 현재 앞장이면 true, 뒷장이면 false
    private boolean isFrontPage;

    // (앞장) 앨범에서 사진 가져올 때
    // request code
    private static final int SELECT_PICTRUE = 1001;
    // 가져온 사진 Uri
    private Uri mSelectedImageUri;
    private long mId = -1;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        // < 레이아웃 view들 가져오기 >
        // 가장 상단의 전체 레이아웃
        mLayout = (RelativeLayout) findViewById(R.id.activity_page);

        // 앞장에 해당하는 view들 연결
        mFrontLayout = (LinearLayout) findViewById(R.id.front_layout);
        mPictureImageview = (ImageView) findViewById(R.id.picture_imageview);
        mCommentTextview = (TextView) findViewById(R.id.comment_textview);
        // content textview 부분에 오늘 날짜 가져와서 넣기
        mCommentTextview.setText(getNowTime());

        // 뒷장에 해당하는 view들 연결
        mBackLayout = (LinearLayout) findViewById(R.id.back_layout);
        mTitleEdittext = (EditText) findViewById(R.id.title_edittext);
        mContentEdittext = (EditText) findViewById(R.id.content_edittext);
        mEditSwitch = (Switch) findViewById(R.id.edit_switch);


        // < 클릭 or 롱클릭시 or 체크시 리스너 호출>
        // 전체 화면, 클릭시 앞장 뒷장 전환
        mLayout.setOnClickListener(this);

        // (앞장) 이미지 롱 클릭시, 앨범에서 사진 가져오기
        mPictureImageview.setOnLongClickListener(this);

        // (뒷장) 스위치 체크 상태가 변화했을때
        mEditSwitch.setOnCheckedChangeListener(this);


        // < 페이지 업데이트시>
        // Intente에 key="page"로 page(object)를 담아 보냈을때
        if (getIntent() != null) {
            if (getIntent().hasExtra("page")) {
                mId = getIntent().getLongExtra("id", -1);

                Page page = (Page) getIntent().getSerializableExtra("page");

                // 앞장
                //TODO 수정시 기존의 사진 뿌려주기
//                mPictureImageview.setImageURI(Uri.parse(page.getImage()));
                Glide.with(this).load(page.getImage()).into(mPictureImageview);
                mSelectedImageUri = Uri.parse(page.getImage());

                mCommentTextview.setText(page.getComment());

                // 뒷장
                mTitleEdittext.setText(page.getTitle());
                mContentEdittext.setText(page.getContent());

            }
        }

    }

    // content textview 부분에 오늘 날짜 가져와서 넣기 위해서 만든
    // 오늘 날짜를 String으로 반환하는 메서드
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getNowTime() {

        SimpleDateFormat fm1 = new SimpleDateFormat("yyyy.MM.dd");
        String date = fm1.format(new Date());

        return date;
    }

    // 전체 화면, 한번 클릭시 앞장 뒷장 전환
    @Override
    public void onClick(View v) {
        // 현재 앞장인지 뒷장인지 확인
        // 현재 앞장이라면 isFrontPage = true;

        if (mFrontLayout.getVisibility() == View.VISIBLE) {
            isFrontPage = true;
        } else {
            isFrontPage = false;
        }

        if (isFrontPage) {
            mFrontLayout.setVisibility(View.INVISIBLE);
            mBackLayout.setVisibility(View.VISIBLE);
            isFrontPage = false;
        } else {
            mFrontLayout.setVisibility(View.VISIBLE);
            mBackLayout.setVisibility(View.INVISIBLE);
            isFrontPage = true;
        }
    }


    // 롱클릭시 처리
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.picture_imageview:
                // (앞장) 이미지 롱 클릭시, 앨범에서 사진 가져오기
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), SELECT_PICTRUE);
                break;

            default:
                break;
        }

        return true;
    }

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

    }

    // (뒷장) 스위치 체크 상태가 변화했을때
    // 체크 되었을 때 -> edit 모드, 체크 되지 않았을 때는 -> view모드
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mTitleEdittext.setEnabled(true);
            mContentEdittext.setEnabled(true);
        } else {
            mTitleEdittext.setEnabled(false);
            mContentEdittext.setEnabled(false);
        }
    }

    // 메뉴 연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_page, menu);
        return true;
    }

    // 메뉴(다음페이지, 취소, 저장) 클릭시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    // 저장
    private void save() {
        Intent intent = new Intent();
        intent.putExtra("title", mTitleEdittext.getText().toString());
        intent.putExtra("content", mContentEdittext.getText().toString());

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


}
