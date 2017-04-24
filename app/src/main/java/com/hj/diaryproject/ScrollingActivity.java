package com.hj.diaryproject;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hj.diaryproject.adapters.Column1Adapter;
import com.hj.diaryproject.adapters.Column3Adapter;
import com.hj.diaryproject.adapters.LandAdapter;
import com.hj.diaryproject.db.PageFacade;
import com.hj.diaryproject.managers.PageManager;
import com.hj.diaryproject.models.Page;
import com.hj.diaryproject.utils.MyUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import static com.hj.diaryproject.TypefaceManager.mKopubDotumBoldTypeface;
import static com.hj.diaryproject.TypefaceManager.mKopubDotumLightTypeface;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private static final int CREATE_NEW_PAGE = 1000;
    private static final int UPTDATE_EXIT_PAGE = 1001;


    private List<Page> mPageList;
    private PageFacade mPageFacade;

    private RecyclerView mRecyclerView;


    private Column1Adapter mColumn1Adapter;
    private Column3Adapter mColumn3Adapter;
    private LandAdapter mLandAdapter;

    private int mColumn = 3;
    private int index;
    private Menu mMenu;


    private TextView mMainTitleTextview;
    private TextView mMainSubtitleTextview;
    private ImageView mMainMenuModeImageview;

    private AppBarLayout mAppBarLayout;

    private ImageView mScrollTopImageview;
    private ImageView mScrollBottomImageview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overridePendingTransition(0, 0);

        getSupportActionBar().setTitle("");


        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        mMainTitleTextview = (TextView) findViewById(R.id.main_title_textview);
        mMainSubtitleTextview = (TextView) findViewById(R.id.main_subtitle_textview);
        mMainMenuModeImageview = (ImageView) findViewById(R.id.main_menu_mode_imageview);

        mMainTitleTextview.setText("폴다");
        mMainSubtitleTextview.setText("Polaroid Diary");

        mMainTitleTextview.setTypeface(mKopubDotumBoldTypeface);
        mMainSubtitleTextview.setTypeface(mKopubDotumLightTypeface);

        // 버튼 클릭시 호출
//        findViewById(R.id.write_button).setOnTouchListener(this);
        findViewById(R.id.write_button).setOnClickListener(this);

        // mPageFacade 초기화
        mPageFacade = new PageFacade(this);
        PageManager pageManager = PageManager.newInstance();

        //  mPageList 초기화
//        mPageList = new ArrayList<>();
        mPageList = mPageFacade.getPageList();

        mRecyclerView = (RecyclerView) findViewById(R.id.grid_view);

        // 어댑터 초기화
        mColumn3Adapter = new Column3Adapter(this, mPageList);
        mColumn1Adapter = new Column1Adapter(this, mPageList);
        mLandAdapter = new LandAdapter(this, mPageList);

        changeColmnMode(mColumn);

        if (mColumn == 3) {
            mRecyclerView.setAdapter(mColumn3Adapter);
        } else {
            mRecyclerView.setAdapter(mColumn1Adapter);
        }


        mScrollTopImageview = (ImageView) findViewById(R.id.scroll_top_imageview);
        mScrollBottomImageview = (ImageView) findViewById(R.id.scroll_bottom_imageview);

        mAppBarLayout.setOnClickListener(this);
        mScrollBottomImageview.setOnTouchListener(this);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("columnMode", mColumn);
        outState.putInt("index", index);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 복원 (null 체크 불필요)
        mColumn = savedInstanceState.getInt("columnMode");
        index = savedInstanceState.getInt("index");


        // 어댑터
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mGridView.setNumColumns(3);
//            mLandAdapter = new LandAdapter(this, mPageList);
            changeColmnMode(3);
            mRecyclerView.setAdapter(mLandAdapter);
            mMainMenuModeImageview.setEnabled(false);
            mMainMenuModeImageview.setVisibility(View.INVISIBLE);

//            Toast.makeText(this, "가로모드 실행", Toast.LENGTH_SHORT).show();


        } else if (mColumn == 3) {
//            mGridView.setNumColumns(3);
//            mColumn3Adapter = new Column3Adapter(this, mPageList);
            changeColmnMode(mColumn);
            mRecyclerView.setAdapter(mColumn3Adapter);
        } else {
//            mGridView.setNumColumns(1);
//            mColumn1Adapter = new Column1Adapter(this, mPageList);
            changeColmnMode(mColumn);
            mRecyclerView.setAdapter(mColumn1Adapter);
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // < RecyclerView 아이템 Click시 >
    // 어댑터로부터 이벤트버스로 position값 받음
    @Subscribe
    public void itemClick(MyUtils.ItemClickEvent event) {
        Log.d("ScrollingActivity", "itemClick: ");
        int position = event.position;

        int state = mPageList.get(position).getState();

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


        // 어댑터
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLandAdapter.notifyDataSetChanged();
        } else if (mColumn == 3) {
            mColumn3Adapter.notifyDataSetChanged();
        } else {
            mColumn1Adapter.notifyDataSetChanged();
        }


//        Toast.makeText(this, "메인에서 state " + mPageList.get(position).getState(), Toast.LENGTH_SHORT).show();
//        mColumn3Adapter = new PageColumn3Adapter(mPageList);
//        mGridView.setAdapter(mColumn3Adapter);


        /*
        if (mPageFacade.update(id, title, content, image, comment, state) > 0) {
                    mPageList = mPageFacade.getPageList();
                }
         */


    }


    // < RecyclerVIew의 아이템 Long Click시 >
    // 어댑터로부터 이벤트버스로 position값 받음
    @Subscribe
    public void itemLongClick(MyUtils.ItemLongClickEvent event) {
        int position = event.position;
        Log.d("ScrollingActivity", "itemLongClick: ");
        //        // 클릭된 아이템이 현재 앞면인지 뒷면인지 상태를 표시
//        boolean isFrontPage = false;
//        if (view.findViewById(R.id.picture_imageview).getVisibility() == View.VISIBLE) {
//            isFrontPage = true;
//        }

        Page page = mPageFacade.getPageList().get(position);
//        Page page = mPageList.get(position);

        Intent intent = new Intent(this, EditPageActivity.class);
        intent.putExtra("id", page.getId());
//        intent.putExtra("isFrontPage", isFrontPage);
        intent.putExtra("page", page);
        startActivityForResult(intent, UPTDATE_EXIT_PAGE);
        overridePendingTransition(0, 0);

        index = position;
    }


    // write 버튼 클릭시, 편집모드로 전환되어 EditPageActivity로 이동
    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.app_bar:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE || mColumn == 3) {
                    if (mPageList.size() <= 200) {
                        mRecyclerView.smoothScrollToPosition(0);
                    } else {
                        mRecyclerView.scrollToPosition(0);
                    }
                } else {
                    mRecyclerView.scrollToPosition(0);
                }
                break;


            case R.id.write_button:
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
////                    image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
//                    image.setColorFilter(Color.parseColor("#353336"));
//
//                } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                    image.setColorFilter(Color.parseColor("#343840"));
//                }


                Intent intent = new Intent(this, EditPageActivity.class);
//        startActivity(intent);
                // 주거니 받거니
                startActivityForResult(intent, CREATE_NEW_PAGE);
                overridePendingTransition(0, 0);
                break;

            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        overridePendingTransition(0, 0);
        long selectedItemId = -1;


        if (resultCode == RESULT_OK) {
            String title = data.getStringExtra("title");
            String content = data.getStringExtra("content");
            String image = data.getStringExtra("image");
            String comment = data.getStringExtra("comment");
            int state = data.getIntExtra("state", 1);

//            Toast.makeText(this, "메인 돌아왔을때 state " + state, Toast.LENGTH_SHORT).show();

            if (requestCode == CREATE_NEW_PAGE) {
                // ★1 새로운 페이지 생성 - < 저장 > 뒤로가기 버튼
                long newRowId = mPageFacade.insert(title, content, image, comment, state);
                selectedItemId = newRowId;
                if (newRowId == -1) {
                    // 에러
                    Toast.makeText(this, "저장이 실패하였습니다", Toast.LENGTH_SHORT).show();
                }


                // scroll 첫번째 position(맨 위로) 이동
                mRecyclerView.scrollToPosition(0);
//                    // 성공
//                    // 리스트 갱신
////                    mPageList = mPageFacade.getPageList();
//                    Toast.makeText(this, "저장성공", Toast.LENGTH_SHORT).show();
//
//                    int count = mPageFacade.getPageList().size();
//                    Toast.makeText(this, "저장된 pageLIst " + count, Toast.LENGTH_SHORT).show(


            } else if (requestCode == UPTDATE_EXIT_PAGE) {
//                Toast.makeText(this, "UPDATE_EXIT_PAGE로 돌아옴", Toast.LENGTH_SHORT).show();
                // ★2 기존의 페이지 수정 - 저장
                long id = data.getLongExtra("id", -1);
                selectedItemId = id;
                if (mPageFacade.update(id, title, content, image, comment, state) < 1) {
                    // 에러
                    Toast.makeText(this, "수정이 실패하였습니다 id " + id, Toast.LENGTH_SHORT).show();
                }


                // 수정
//                        if (mPageFacade.update(id, title, content, image, comment, state) > 0) {
//                            mPageList = mPageFacade.getPageList();
//                        }

            }

            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();


        } else {
            // ★3 새로운 생성 중 or 기존의 페이지 수정 중 - < 삭제 > 버튼
            long id = data.getLongExtra("id", -1);
            selectedItemId = id;
            int deleted = mPageFacade.delete(id);

            if (deleted == 0) {
                Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
//                mPageList = mPageFacade.getPageList();
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }

        // 페이지 리스트 업데이트
        mPageList = mPageFacade.getPageList();


        // 어댑터
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

            mLandAdapter.swap(mPageList);
            mLandAdapter.notifyDataSetChanged();

            Toast.makeText(this, "가로모드 실행", Toast.LENGTH_SHORT).show();
        } else if (mColumn == 3) {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

            mColumn3Adapter.swap(mPageList);
            mColumn3Adapter.notifyDataSetChanged();


        } else {

//            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            mColumn1Adapter.swap(mPageList);
            mColumn1Adapter.notifyDataSetChanged();
        }


    }


    public void onClickToChangeColumnMode(View view) {
        if (mColumn == 1) {
            mColumn = 3;
            changeColmnMode(mColumn);
            mRecyclerView.setAdapter(mColumn3Adapter);

            mMainMenuModeImageview.setImageDrawable(getResources().getDrawable(R.drawable.list1));

            mColumn3Adapter.swap(mPageList);
            mColumn3Adapter.notifyDataSetChanged();

        } else if (mColumn == 3) {
            mColumn = 1;
            changeColmnMode(mColumn);
            mRecyclerView.setAdapter(mColumn1Adapter);

            mMainMenuModeImageview.setImageDrawable(getResources().getDrawable(R.drawable.list3));

            mColumn1Adapter.swap(mPageList);
            mColumn1Adapter.notifyDataSetChanged();
        }


    }

    private void changeColmnMode(int mColumn) {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, mColumn));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView image = (ImageView) v;
        switch (v.getId()) {
            case R.id.scroll_bottom_imageview:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    image.setColorFilter(0xaa111111, PorterDuff.Mode.SRC_OVER);
                    image.setColorFilter(Color.parseColor("#353336"));

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    image.setColorFilter(Color.parseColor("#828085"));
                }

                if (mPageList.size() > 0) {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE || mColumn == 3) {
                        if (mPageList.size() <= 200) {
                            mRecyclerView.smoothScrollToPosition(mPageList.size() - 1);
                        } else {
                            mRecyclerView.scrollToPosition(mPageList.size() - 1);
                        }
                    } else {
                        mRecyclerView.scrollToPosition(mPageList.size() - 1);
                    }
                }


                break;


            default:
                break;
        }


        return true;
    }


//    // SmoothScroller를 사용하기 위해 Custom LayoutManager를 만들어준다다
//    private class GridLayoutManagerWithSmoothScroller extends GridLayoutManager {
//        public GridLayoutManagerWithSmoothScroller(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//            super(context, attrs, defStyleAttr, defStyleRes);
//        }
//
//        public GridLayoutManagerWithSmoothScroller(Context context, int spanCount) {
//            super(context, spanCount);
//        }
//
//        public GridLayoutManagerWithSmoothScroller(Context context, int spanCount, int orientation, boolean reverseLayout) {
//            super(context, spanCount, orientation, reverseLayout);
//        }
//
//
//        @Override
//        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state,
//                                           int position) {
//            RecyclerView.SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
//            smoothScroller.setTargetPosition(position);
//            startSmoothScroll(smoothScroller);
//        }
//
//        private class TopSnappedSmoothScroller extends LinearSmoothScroller {
//            public TopSnappedSmoothScroller(Context context) {
//                super(context);
//
//            }
//
//            @Override
//            public PointF computeScrollVectorForPosition(int targetPosition) {
//                return GridLayoutManagerWithSmoothScroller.this
//                        .computeScrollVectorForPosition(targetPosition);
//            }
//
//            @Override
//            protected int getVerticalSnapPreference() {
//                return SNAP_TO_START;
//            }
//        }
//    }

}
