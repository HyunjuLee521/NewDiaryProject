package com.hj.diaryproject.db;

/**
 * Created by USER on 2017-02-22.
 */


import android.provider.BaseColumns;

/**
 * 메모장의 스키마
 */
public final class PageContract {
    /**
     * CREATE TABLE memo
     * (
     * _id INTEGER PRIMARY KEY AUTOINCREMENT,
     * title TEXT,
     * content TEXT,
     * image TEXT,
     * comment TEXT
     * );
     */
    public static final String SQL_CREATE_MEMO_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);",
                    PageEntry.TABLE_NAME,
                    PageEntry._ID,
                    PageEntry.COLUMN_NAME_TITLE,
                    PageEntry.COLUMN_NAME_CONTENT,
                    PageEntry.COLUMN_NAME_IMAGE,
                    PageEntry.COLUMN_NAME_COMMENT);

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private PageContract() {
    }

    /* Inner class that defines the table contents */
    public static class PageEntry implements BaseColumns {
        public static final String TABLE_NAME = "page";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_COMMENT = "comment";
    }
}
