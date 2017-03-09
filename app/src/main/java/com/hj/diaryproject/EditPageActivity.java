package com.hj.diaryproject;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hj.diaryproject.models.Page;

import java.util.Date;

public class EditPageActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, CompoundButton.OnCheckedChangeListener, View.OnTouchListener {

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
    private ImageView mDeleteImageview;

    // 현재 앞장이면 true, 뒷장이면 false
//    private boolean isFrontPage;
    // 현재 앞장이면 1, 뒷장이면 0
    private int state = 1;

    // (앞장) 앨범에서 사진 가져올 때
    // request code
    private static final int SELECT_PICTRUE = 1001;
    // 가져온 사진 Uri
    private Uri mSelectedImageUri;
    private long mId = -1;
    private String mSelectedImagePath;


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
        mDeleteImageview = (ImageView) findViewById(R.id.delete_imageview);
        mDeleteImageview.setOnTouchListener(this);




        // < 클릭 or 롱클릭시 or 체크시 리스너 호출>
        // 전체 화면, 클릭시 앞장 뒷장 전환
        mLayout.setOnClickListener(this);
        // (앞장) 이미지 클릭시, 앞장 뒷장 전환
        mPictureImageview.setOnClickListener(this);


        // (앞장) 이미지 롱 클릭시, 앨범에서 사진 가져오기
        mPictureImageview.setOnLongClickListener(this);

        // (뒷장) 스위치 체크 상태가 변화했을때
        mEditSwitch.setOnCheckedChangeListener(this);
        // (뒷장) 레이아웃 롱클릭했을 때, 편집모드로 전환
        mBackLayout.setOnLongClickListener(this);
        // (뒷장) 레이아웃 클릭했을 때, 앞장 뒷장 전환
        mBackLayout.setOnClickListener(this);



        // < 페이지 업데이트시>
        // Intente에 key="page"로 page(object)를 담아 보냈을때
        if (getIntent() != null) {
            if (getIntent().hasExtra("page")) {

                mId = getIntent().getLongExtra("id", -1);

                Page page = (Page) getIntent().getSerializableExtra("page");


                // 앞장
                //TODO 수정시 기존의 사진 뿌려주기
//                mPictureImageview.setImageURI(Uri.parse(page.getImage()));
//                Glide.with(this).load(page.getImage()).into(mPictureImageview);

                // 썸네일로 뿌려주기
//                Glide.with(this).loadFromMediaStore(Uri.parse(page.getImage())).thumbnail(0.2f).into(mPictureImageview);
                mSelectedImageUri = Uri.parse(page.getImage());

                // 실제 패스를 구해 이미지 뿌려주기
                Uri uri = Uri.parse(page.getImage());
                mSelectedImagePath = getRealPath(uri);

                Glide.with(this).load(mSelectedImagePath).into(mPictureImageview);


                mCommentTextview.setText(page.getComment());

                // 뒷장
                mTitleEdittext.setText(page.getTitle());
                mContentEdittext.setText(page.getContent());

                state = page.getState();

                if (state == 1) {
                    mFrontLayout.setVisibility(View.VISIBLE);
                    mBackLayout.setVisibility(View.INVISIBLE);
                } else {
                    mFrontLayout.setVisibility(View.INVISIBLE);
                    mBackLayout.setVisibility(View.VISIBLE);
                }

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

    // TODO 페이지 앞장 뒷장 전환 ★
    // 전체 화면 레이아웃, 클릭시 앞장 뒷장 전환
    @Override
    public void onClick(View v) {
        // 현재 앞장인지 뒷장인지 확인
        // 현재 앞장이라면 isFrontPage = true;
        if (mFrontLayout.getVisibility() == View.VISIBLE) {
            state = 1;
        } else {
            state = 0;
        }

        // 뒤집기
        if (state == 1) {
            // 앞장이라면 -> 뒷장으로 뒤집기
            mFrontLayout.setVisibility(View.INVISIBLE);
            mBackLayout.setVisibility(View.VISIBLE);

            state = 0;
        } else {
            // 뒷장이라면 -> 앞장으로 뒤집기
            mFrontLayout.setVisibility(View.VISIBLE);
            mBackLayout.setVisibility(View.INVISIBLE);
            // switch check 안되게(view 모드로) 하기
            mEditSwitch.setChecked(false);
            mContentEdittext.setEnabled(false);
            mTitleEdittext.setEnabled(false);

            state = 1;
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


            case R.id.back_layout:
                Toast.makeText(this, "뒷장 롱클릭", Toast.LENGTH_SHORT).show();
                mTitleEdittext.setEnabled(true);
                mContentEdittext.setEnabled(true);
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
            // 라이브러리에서 가져오는 방법
            Glide.with(this).load(mSelectedImageUri.toString()).into(mPictureImageview);


            // 비트맵으로 가져오기
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mSelectedImageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }




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

    // 뒤로가기 버튼 눌렀을 때 -> 저장
    @Override
    public void onBackPressed() {
        save();
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();
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

        intent.putExtra("state", state);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private void delete() {
        Intent intent = new Intent();
        intent.putExtra("id", mId);
        setResult(RESULT_CANCELED, intent);
        finish();
        overridePendingTransition(0, 0);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //TODO 눌렸을 때 효과 바꾸기
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mDeleteImageview.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mDeleteImageview.setColorFilter(0x00000000, PorterDuff.Mode.SRC_OVER);
        }

        delete();
        return true;
    }

    // 이미지 패스 가져오기
    public String getRealPath(Uri uri) {
        String strDocId = DocumentsContract.getDocumentId(uri);
        String[] strSplittedDocId = strDocId.split(":");
        String strId = strSplittedDocId[strSplittedDocId.length - 1];

        Cursor crsCursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.MediaColumns.DATA},
                "_id=?",
                new String[]{strId},
                null
        );
        crsCursor.moveToFirst();
        String filePath = crsCursor.getString(0);

        return filePath;
    }

}
