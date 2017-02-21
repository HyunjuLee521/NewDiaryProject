package com.hj.diaryproject.managers;

import com.hj.diaryproject.models.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2017-02-09.
 */

public class PageManager {


    private static PageManager sInstance = new PageManager();
    private ArrayList<Page> mPageList;

    // 싱글턴 패턴
    public static PageManager newInstance() {
        return sInstance;
    }

    private PageManager() {
        mPageList = new ArrayList<>();
    }

    /**
     * 페이지 추가
     *
     * @param Page
     */
    public void create(Page page) {
        mPageList.add(page);
    }


    /**
     * 페이지 리스트(모든 페이지) 반환
     *
     * @return ArrayList<Page>
     */
    public ArrayList<Page> getPageList() {
        return mPageList;
    }


    @Override
    public String toString() {
        return "PageManager{" +
                "mPageList=" + mPageList +
                '}';
    }
}
