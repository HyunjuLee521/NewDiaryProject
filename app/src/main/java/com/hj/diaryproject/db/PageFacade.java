package com.hj.diaryproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hj.diaryproject.models.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2017-02-22.
 */

public class PageFacade {


    private PageDbHelper mDbHelper;

    public PageFacade(Context context) {
        mDbHelper = new PageDbHelper(context);
    }

    /**
     * 페이지를 추가한다
     *
     * @param title   제목
     * @param content 내용
     * @param image   이미지URI String값으로 저장
     * @param comment 날짜등 이미지 밑의 코멘트
     * @param state   앞면인지 뒷면인지 상태
     * @return 추가된 row 의 id, 만약 에러가 발생되면 -1
     */
    public long insert(String title, String content, String image, String comment, int state) {
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // 이거 한 줄로 됨
//                db.execSQL("INSERT INTO memo (title, contents) VALUES ('" + title + "', '" + content + "')");

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PageContract.PageEntry.COLUMN_NAME_TITLE, title);
        values.put(PageContract.PageEntry.COLUMN_NAME_CONTENT, content);
        values.put(PageContract.PageEntry.COLUMN_NAME_IMAGE, image);
        values.put(PageContract.PageEntry.COLUMN_NAME_COMMENT, comment);
        values.put(PageContract.PageEntry.COLUMN_NAME_STATE, state);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PageContract.PageEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    /**
     * 전체 페이지 리스트
     *
     * @return 전체 페이지
     */
    public List<Page> getPageList() {
        ArrayList<Page> pageArrayList = new ArrayList<>();

        // DB에서 읽어오기
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 이거랑 아래 코드랑 같다
//        Cursor cursor = db.rawQuery("SELECT * FROM memo", null);


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PageContract.PageEntry._ID,
                PageContract.PageEntry.COLUMN_NAME_TITLE,
                PageContract.PageEntry.COLUMN_NAME_CONTENT,
                PageContract.PageEntry.COLUMN_NAME_IMAGE,
                PageContract.PageEntry.COLUMN_NAME_COMMENT,
                PageContract.PageEntry.COLUMN_NAME_STATE

        };

        // How you want the results sorted in the resulting Cursor
        // ORDER BY _id DESC
        String sortOrder =
                PageContract.PageEntry._ID + " DESC";

        Cursor c = db.query(
                PageContract.PageEntry.TABLE_NAME,        // The table to query
                null,                                     // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c != null) {
            // 커서를 Memo로 변환

            // 커서를 처음 항목이로 이동
//            c.moveToFirst();
            while (c.moveToNext()) {
                String title = c.getString(
                        c.getColumnIndexOrThrow(
                                PageContract.PageEntry.COLUMN_NAME_TITLE)
                );
                String content = c.getString(
                        c.getColumnIndexOrThrow(
                                PageContract.PageEntry.COLUMN_NAME_CONTENT
                        )
                );
                String image = c.getString(
                        c.getColumnIndexOrThrow(
                                PageContract.PageEntry.COLUMN_NAME_IMAGE
                        )
                );

                String comment = c.getString(
                        c.getColumnIndexOrThrow(
                                PageContract.PageEntry.COLUMN_NAME_COMMENT
                        )
                );


                long id = c.getLong(
                        c.getColumnIndexOrThrow(
                                PageContract.PageEntry._ID
                        ));

                int state = c.getInt(
                        c.getColumnIndexOrThrow(
                                PageContract.PageEntry.COLUMN_NAME_STATE
                        )
                );

                Page page = new Page(title, content, image, comment, state);
                page.setId(id);
                pageArrayList.add(page);
            }

            // 커서 닫기
            c.close();
        }
        return pageArrayList;
    }

    /**
     * 메모 삭제
     *
     * @param id 삭제할 메모 id
     * @return 삭제된 행의 수
     */
    public int delete(long id) {
        // Define 'where' part of query.
        String selection = PageContract.PageEntry._ID + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {String.valueOf(id)};
        // Issue SQL statement.
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int deleted = db.delete(PageContract.PageEntry.TABLE_NAME,
                selection,
                selectionArgs);

        // 이 코드로만으로 위 코드를 대체할 수 있다
//        int deleted = db.delete(MemoContract.MemoEntry.TABLE_NAME,
//                "_id=" + id,
//                null);
        return deleted;
    }

    /**
     * 메모 수정
     *
     * @param id      수정할 메모 id
     * @param title   제목
     * @param content 내용
     * @param image   이미지URI String값으로 저장
     * @param comment 날짜등 이미지 밑의 코멘트
     * @param state   앞면(1)인지 뒷면(0)인지 상태 표시
     * @return 수정된 메모 수
     */
    public int update(long id, String title, String content, String image, String comment, int state) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(PageContract.PageEntry.COLUMN_NAME_TITLE, title);
        values.put(PageContract.PageEntry.COLUMN_NAME_CONTENT, content);
        values.put(PageContract.PageEntry.COLUMN_NAME_IMAGE, image);
        values.put(PageContract.PageEntry.COLUMN_NAME_COMMENT, comment);
        values.put(PageContract.PageEntry.COLUMN_NAME_STATE, state);

        int count = db.update(
                PageContract.PageEntry.TABLE_NAME,
                values,
                PageContract.PageEntry._ID + "=" + id,
                null);

        return count;
    }


}
