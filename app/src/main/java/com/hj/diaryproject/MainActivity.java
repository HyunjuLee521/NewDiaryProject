package com.hj.diaryproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.diaryproject.adapters.PageAdapter;
import com.hj.diaryproject.db.PageFacade;
import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {


    private GridView mGridView;
    private List<Page> mPageList;
    private PageAdapter mAdapter;

    private PageFacade mPageFacade;


    private static final int CREATE_NEW_PAGE = 1000;
    private static final int UPTDATE_EXIT_PAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPageFacade = new PageFacade(this);

        // gridVIew 연결
        mGridView = (GridView) findViewById(R.id.grid_view);
        // gridVIew 클릭시 호출
        mGridView.setOnItemClickListener(this);
        // gridVIew 롱클릭시 호출
        mGridView.setOnItemLongClickListener(this);

        // 데이터 초기화
        mPageList = new ArrayList<>();

        PageManager pageManager = PageManager.newInstance();
//        mPageFacade.insert("타이틀1", "콘텐츠1", "이미지주소1", "날짜1");
//        mPageFacade.insert("타이틀2", "콘텐츠2", "이미지주소2", "날짜2");


        mPageList = mPageFacade.getPageList();

        // 어댑터
        mAdapter = new PageAdapter(mPageList);

        mGridView.setAdapter(mAdapter);

        // 버튼 클릭시 호출
        findViewById(R.id.write_button).setOnClickListener(this);


    }


    // gridView 클릭시 -> 앞장 뒷장 전환
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//        TextView titleTextview = (TextView) view.findViewById(R.id.title_textiview);
//        TextView contentTextview = (TextView) view.findViewById(R.id.content_textview);
//        TextView imageTextview = (TextView) view.findViewById(R.id.image_textview);
//        ImageView pictureImageview = (ImageView) view.findViewById(R.id.picture_imageview);
//        TextView commentTextview = (TextView) view.findViewById(R.id.comment_textview);
//
//        int isFront = imageTextview.getVisibility();
//
//        if (isFront == View.VISIBLE) {
//            titleTextview.setVisibility(View.VISIBLE);
//            contentTextview.setVisibility(View.VISIBLE);
//            imageTextview.setVisibility(View.INVISIBLE);
//            pictureImageview.setVisibility(View.INVISIBLE);
//            commentTextview.setVisibility(View.INVISIBLE);
//
//
//        } else {
//            titleTextview.setVisibility(View.INVISIBLE);
//            contentTextview.setVisibility(View.INVISIBLE);
//            imageTextview.setVisibility(View.VISIBLE);
//            pictureImageview.setVisibility(View.VISIBLE);
//            commentTextview.setVisibility(View.VISIBLE);
//
//
//        }

        mAdapter.setSelect(id);
        // 데이터가 변경됨을 알려줌 = 다시 그려라
        mAdapter.notifyDataSetChanged();

    }

    // girdVIew 롱클릭시 -> edit모드 전환
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Page page = mPageList.get(position);

        Intent intent = new Intent(this, EditPageActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("page", page);

        startActivityForResult(intent, UPTDATE_EXIT_PAGE);
        return true;
    }

    // write 버튼 클릭시
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditPageActivity.class);
//        startActivity(intent);
        // 주거니 받거니
        startActivityForResult(intent, CREATE_NEW_PAGE);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String image = data.getStringExtra("image");
            String comment = data.getStringExtra("comment");

            // ★1 새로운 페이지 생성
            if (requestCode == CREATE_NEW_PAGE) {
                long newRowId = mPageFacade.insert(title, content, image, comment);
                if (newRowId == -1) {
                    // 에러
                    Toast.makeText(this, "저장이 실패하였습니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 성공
                    // 리스트 갱신
                    mPageList = mPageFacade.getPageList();
                }


            } else if (requestCode == UPTDATE_EXIT_PAGE) {
                // ★2 기존의 페이지 수정

                long id = data.getLongExtra("id", -1);
                // 수정
                if (mPageFacade.update(id, title, content, image, comment) > 0) {
                    mPageList = mPageFacade.getPageList();
                }

            }

            //            mAdapter.notifyDataSetChanged();
            // TODO 위에꺼가 이상하게 안되니까 일단 아래 코드로 땜빵
            mAdapter = new PageAdapter(mPageList);
            mGridView.setAdapter(mAdapter);
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();

        } else {
            // ★3 취소 버튼
            Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
        }



        /*

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");

            if (requestCode == REQUEST_CODE_NEW_MEMO) {
                // 새 메모
                long newRowId = mMemoFacade.insert(title, content);
                if (newRowId == -1) {
                    // 에러
                    Toast.makeText(this, "저장이 실패하였습니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 성공
                    // 리스트 갱신
                    mMemoList = mMemoFacade.getMemoList();
                }

            } else if (requestCode == REQUEST_CODE_UPDATE_MEMO) {
                long id = data.getLongExtra("id", -1);
                // 수정
                if (mMemoFacade.update(id, title, content) > 0) {
                    mMemoList = mMemoFacade.getMemoList();
                }
            }
//            mAdapter.notifyDataSetChanged();
            // TODO 위에꺼가 이상하게 안되니까 일단 아래 코드로 땜빵
            mAdapter = new MemoAdapter(mMemoList);
            mMemoListView.setAdapter(mAdapter);

            Log.d(TAG, "onActivityResult: " + title + ", " + content);
            Toast.makeText(this, "저장 되었습니다", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "취소 되었습니다", Toast.LENGTH_SHORT).show();
        }


         */


    }


}
