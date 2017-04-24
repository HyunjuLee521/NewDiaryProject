package com.hj.diaryproject;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hj.diaryproject.adapters.CursorRecyclerViewAdapter;

public class SelectImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
    }


    private class ImageRecyclerAdapter extends CursorRecyclerViewAdapter<ViewHolder> {

        public ImageRecyclerAdapter(Context context, Cursor cursor) {
            super(context, cursor);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }
    }

//    // mUriArrayList return하는 메서드
//    public ArrayList<Uri> getSelectedSongUriArrayList() {
//        return mUriArrayLIst;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView titleTextView;
        TextView artistTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(android.R.id.text1);
            artistTextView = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}
