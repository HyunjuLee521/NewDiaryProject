package com.hj.diaryproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.diaryproject.adapters.PageAdapter;
import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {


    private GridView mGridView;
    private ArrayList<Page> mPageList;
    private PageAdapter mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // gridVIew 연결
        mGridView = (GridView) findViewById(R.id.grid_view);
        // gridVIew 클릭시 호출
        mGridView.setOnItemClickListener(this);
        // gridVIew 롱클릭시 호출
        mGridView.setOnItemLongClickListener(this);

        // 데이터 초기화
        mPageList = new ArrayList<>();

        PageManager pageManager = PageManager.newInstance();
        pageManager.create(new Page(0, "타이틀1", "콘텐트1", "이미지주소1"));
        pageManager.create(new Page(1, "타이틀2", "콘텐트2", "이미지주소2"));
        pageManager.create(new Page(2, "타이틀3", "콘텐트3", "이미지주소3"));
        pageManager.create(new Page(3, "타이틀4", "콘텐트4", "이미지주소4"));


        // 어댑터
        mAdapter = new PageAdapter(pageManager.getPageList());

        mGridView.setAdapter(mAdapter);

        // 버튼 클릭시 호출
        findViewById(R.id.write_button).setOnClickListener(this);


    }


    // gridView 클릭시 -> 앞장 뒷장 전환
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        TextView titleTextview = (TextView) view.findViewById(R.id.title_textiview);
        TextView contentTextview = (TextView) view.findViewById(R.id.content_textview);
        TextView imageTextview = (TextView) view.findViewById(R.id.image_textview);

        int isFront = imageTextview.getVisibility();

        if (isFront == View.VISIBLE) {
            titleTextview.setVisibility(View.VISIBLE);
            contentTextview.setVisibility(View.VISIBLE);
            imageTextview.setVisibility(View.INVISIBLE);

        } else {
            titleTextview.setVisibility(View.INVISIBLE);
            contentTextview.setVisibility(View.INVISIBLE);
            imageTextview.setVisibility(View.VISIBLE);
        }


    }

    // girdVIew 롱클릭시 -> edit모드 전환
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        return false;
    }

    // write 버튼 클릭시
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, EditFrontPageActivity.class);
//        startActivity(intent);
        // 주거니 받거니
        startActivityForResult(intent, 1000);
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mAdapter.notifyDataSetChanged();
    }
}
