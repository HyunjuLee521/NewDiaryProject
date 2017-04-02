package com.hj.diaryproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.hj.diaryproject.db.PageFacade;
import com.hj.diaryproject.models.Page;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int CREATE_NEW_PAGE = 1000;


    public List<Page> mPageList;
    public PageFacade mPageFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // 버튼 클릭시 호출
        findViewById(R.id.write_button).setOnClickListener(this);


        mPageFacade = new PageFacade(this);
        mPageList = mPageFacade.getPageList();

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String image = data.getStringExtra("image");
            String comment = data.getStringExtra("comment");
            int state = data.getIntExtra("state", 1);


            // TODO MainActivity로 Intent를 보내는게 나을듯
            Toast.makeText(this, "onActivityResult로 돌아옴", Toast.LENGTH_SHORT).show();
            // ★1 새로운 페이지 생성 - 저장
            if (requestCode == CREATE_NEW_PAGE) {
                long newRowId = mPageFacade.insert(title, content, image, comment, state);
                if (newRowId == -1) {
                    // 에러
                    Toast.makeText(this, "저장이 실패하였습니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 성공
                    // 리스트 갱신
//                    mPageList = mPageFacade.getPageList();
                    Toast.makeText(this, "저장성공", Toast.LENGTH_SHORT).show();
                }
            }

        } else {
            // ★3 새로운 페이지 생성 or 기존의 페이지 수정 - 삭제 버튼
            long id = data.getLongExtra("id", -1);

            int deleted = mPageFacade.delete(id);
//            if (deleted != 0) {
//                mPageList = mPageFacade.getPageList();
//            }

//            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();

        }

        /**
         * {@link com.hj.diaryproject.MainActivity#createNewPage(int)}
         */

        EventBus.getDefault().post(resultCode);

    }
}
