package com.hj.diaryproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.hj.diaryproject.adapters.PageColumn1Adapter;
import com.hj.diaryproject.adapters.PageColumn3Adapter;
import com.hj.diaryproject.adapters.PageLandAdapter;
import com.hj.diaryproject.db.PageFacade;
import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {


    private GridView mGridView;
    private List<Page> mPageList;
    private PageColumn3Adapter mColumn3Adapter;
    private PageColumn1Adapter mColumn1Adapter;
    private PageLandAdapter mPageLandAdapter;

    private PageFacade mPageFacade;

    private static final int CREATE_NEW_PAGE = 1000;
    private static final int UPTDATE_EXIT_PAGE = 1001;

    private int mColumn = 3;

    private Toolbar mMainToolbar;

    OrientationEventListener orientEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPageFacade = new PageFacade(this);

        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setTitle("툴바 잘 들어갔는지 테스트");


        // gridVIew 연결l
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


        if (mColumn == 3) {
            mGridView.setNumColumns(3);
            mColumn3Adapter = new PageColumn3Adapter(mPageList);
            mGridView.setAdapter(mColumn3Adapter);
        } else {
            mGridView.setNumColumns(1);
            mColumn1Adapter = new PageColumn1Adapter(mPageList);
            mGridView.setAdapter(mColumn1Adapter);
        }


        // 버튼 클릭시 호출
        findViewById(R.id.write_button).setOnClickListener(this);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("columnMode", mColumn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 복원 (null 체크 불필요)
        mColumn = savedInstanceState.getInt("columnMode");


        // 어댑터
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridView.setNumColumns(3);
            mPageLandAdapter = new PageLandAdapter(mPageList);
            mGridView.setAdapter(mPageLandAdapter);

            Toast.makeText(this, "가로모드 실행", Toast.LENGTH_SHORT).show();
        } else if (mColumn == 3) {
            mGridView.setNumColumns(3);
            mColumn3Adapter = new PageColumn3Adapter(mPageList);
            mGridView.setAdapter(mColumn3Adapter);
        } else {
            mGridView.setNumColumns(1);
            mColumn1Adapter = new PageColumn1Adapter(mPageList);
            mGridView.setAdapter(mColumn1Adapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
         */
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_modec_change:
                if (mColumn == 1) {
                    mGridView.setNumColumns(3);
                    mColumn3Adapter = new PageColumn3Adapter(mPageList);
                    mGridView.setAdapter(mColumn3Adapter);
                    mColumn = 3;
                } else {
                    mGridView.setNumColumns(1);
                    mColumn1Adapter = new PageColumn1Adapter(mPageList);
                    mGridView.setAdapter(mColumn1Adapter);
                    mColumn = 1;
                }

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    // gridView item 클릭시 -> 앞장 뒷장 전환
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        int state = 1;
        state = mPageList.get(position).getState();

        if (state == 1) {
            mPageList.get(position).setState(0);
        } else {
            mPageList.get(position).setState(1);
        }


        mPageFacade.update(
                mPageList.get(position).getId(),
                mPageList.get(position).getTitle(),
                mPageList.get(position).getContent(),
                mPageList.get(position).getImage(),
                mPageList.get(position).getComment(),
                mPageList.get(position).getState()
        );

        // 데이터가 변경됨을 알려줌 = 다시 그려라
//        mColumn3Adapter.notifyDataSetChanged();


        // 어댑터
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPageLandAdapter.notifyDataSetChanged();
        } else if (mColumn == 3) {
            mColumn3Adapter.notifyDataSetChanged();
        } else {
            mColumn1Adapter.notifyDataSetChanged();
        }


//        mColumn3Adapter = new PageColumn3Adapter(mPageList);
//        mGridView.setAdapter(mColumn3Adapter);


        /*
        if (mPageFacade.update(id, title, content, image, comment, state) > 0) {
                    mPageList = mPageFacade.getPageList();
                }
         */


    }

    // girdVIew item 롱클릭시 -> edit모드 전환
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        // 클릭된 아이템이 현재 앞면인지 뒷면인지 상태를 표시
//        boolean isFrontPage = false;
//        if (view.findViewById(R.id.picture_imageview).getVisibility() == View.VISIBLE) {
//            isFrontPage = true;
//        }

        Page page = mPageFacade.getPageList().get(position);
//        Page page = mPageList.get(position);

        Intent intent = new Intent(this, EditPageActivity.class);
        intent.putExtra("id", id);
//        intent.putExtra("isFrontPage", isFrontPage);
        intent.putExtra("page", page);
        startActivityForResult(intent, UPTDATE_EXIT_PAGE);
        return true;
    }

    // write 버튼 클릭시, 편집모드로 전환되어 EditPageActivity로 이동
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditPageActivity.class);
//        startActivity(intent);
        // 주거니 받거니
        startActivityForResult(intent, CREATE_NEW_PAGE);
        overridePendingTransition(0, 0);
    }


    // EditPageActivity에 보낸 두가지 요청
    // (CREATE_NEW_PAGE, UPTDATE_EXIT_PAGE이 되돌아올때
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String image = data.getStringExtra("image");
            String comment = data.getStringExtra("comment");
            int state = data.getIntExtra("state", 1);

            // ★1 새로운 페이지 생성 - 저장
            if (requestCode == CREATE_NEW_PAGE) {
                long newRowId = mPageFacade.insert(title, content, image, comment, state);
                if (newRowId == -1) {
                    // 에러
                    Toast.makeText(this, "저장이 실패하였습니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 성공
                    // 리스트 갱신
                    mPageList = mPageFacade.getPageList();
                }

            } else if (requestCode == UPTDATE_EXIT_PAGE) {
                // ★2 기존의 페이지 수정 - 저장

                long id = data.getLongExtra("id", -1);
                // 수정
                if (mPageFacade.update(id, title, content, image, comment, state) > 0) {
                    mPageList = mPageFacade.getPageList();
                }

            }

            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();


        } else {
            // ★3 새로운 페이지 생성 or 기존의 페이지 수정 - 삭제 버튼
            long id = data.getLongExtra("id", -1);

            int deleted = mPageFacade.delete(id);
            if (deleted != 0) {
                mPageList = mPageFacade.getPageList();
            }

            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
        }

        //                        mColumn3Adapter.notifyDataSetChanged();
//        // TODO 위에꺼가 이상하게 안되니까 일단 아래 코드로 땜빵
//        mColumn3Adapter = new PageColumn3Adapter(mPageList);
//        mGridView.setAdapter(mColumn3Adapter);

        // 어댑터

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mGridView.setNumColumns(3);
            mPageLandAdapter = new PageLandAdapter(mPageList);
            mGridView.setAdapter(mPageLandAdapter);

            Toast.makeText(this, "가로모드 실행", Toast.LENGTH_SHORT).show();
        } else if (mColumn == 3) {
            mGridView.setNumColumns(3);
            mColumn3Adapter = new PageColumn3Adapter(mPageList);
            mGridView.setAdapter(mColumn3Adapter);
        } else {
            mGridView.setNumColumns(1);
            mColumn1Adapter = new PageColumn1Adapter(mPageList);
            mGridView.setAdapter(mColumn1Adapter);
        }


    }


}
