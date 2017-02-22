package com.hj.diaryproject.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hj.diaryproject.R;
import com.hj.diaryproject.models.Page;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2017-02-09.
 */

public class PageAdapter extends BaseAdapter {
    private List<Page> mData;
    private ViewHolder viewHolder;


    public PageAdapter(List<Page> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // convertView : 재사용 되는 뷰
        if (convertView == null) {
            viewHolder = new ViewHolder();

            // 뷰를 새로 만들 때
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_page, parent, false);

            // 레이아웃 들고 오기
            TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textiview);
            TextView contentTextView = (TextView) convertView.findViewById(R.id.content_textview);
            TextView imageTextView = (TextView) convertView.findViewById(R.id.image_textview);
            ImageView pictureImageView = (ImageView) convertView.findViewById(R.id.picture_imageview);
            TextView commentTextView = (TextView) convertView.findViewById(R.id.comment_textview);


            // 뷰 홀더에 넣는다
            viewHolder.titleTextView = titleTextView;
            viewHolder.contentTextView = contentTextView;
            viewHolder.imageTextView = imageTextView;
            viewHolder.pictureImageView = pictureImageView;
            viewHolder.commentTextView = commentTextView;

            convertView.setTag(viewHolder);
        } else {
            // 재사용 할 때
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 데이터
        Page page = mData.get(position);

        // 화면에 뿌리기
        viewHolder.titleTextView.setText(page.getTitle());
        viewHolder.contentTextView.setText(page.getContent());
        viewHolder.imageTextView.setText(page.getImage());
        // TODO 사진도 가져와 뿌리기
        if (page.getImage() == null) {
            viewHolder.pictureImageView.setImageDrawable(null);
        }
        viewHolder.pictureImageView.setImageURI(Uri.parse(page.getImage()));

        viewHolder.commentTextView.setText(page.getComment());


        return convertView;
    }

    private static class ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView imageTextView;
        ImageView pictureImageView;
        TextView commentTextView;
    }


}
