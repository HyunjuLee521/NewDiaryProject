package com.hj.diaryproject.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.diaryproject.R;
import com.hj.diaryproject.models.Page;

import java.util.List;

import static com.hj.diaryproject.TypefaceManager.mKopubBatangLightTypeface;
import static com.hj.diaryproject.TypefaceManager.mKopubBatangMediumTypeface;

/**
 * Created by USER on 2017-03-09.
 */

public class PageColumn1Adapter extends BaseAdapter {

    private List<Page> mData;

    public PageColumn1Adapter(List<Page> mData) {
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
        ViewHolder viewHolder;


        // convertView : 재사용 되는 뷰
        if (convertView == null) {
            viewHolder = new ViewHolder();

            // 뷰를 새로 만들 때
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.column1_item_page, parent, false);


            // 레이아웃 들고 오기
            TextView titleTextView = (TextView) convertView.findViewById(R.id.title_textiview);
            TextView contentTextView = (TextView) convertView.findViewById(R.id.content_textview);
            TextView imageTextView = (TextView) convertView.findViewById(R.id.image_textview);
            ImageView pictureImageView = (ImageView) convertView.findViewById(R.id.picture_imageview);
            TextView commentTextView = (TextView) convertView.findViewById(R.id.comment_textview);

            LinearLayout frontcoverLayout = (LinearLayout) convertView.findViewById(R.id.frontcover_layout);
            LinearLayout backbottomLayout = (LinearLayout) convertView.findViewById(R.id.backcbottom_layout);



            // 뷰 홀더에 넣는다
            viewHolder.titleTextView = titleTextView;
            viewHolder.contentTextView = contentTextView;
            viewHolder.imageTextView = imageTextView;
            viewHolder.pictureImageView = pictureImageView;
            viewHolder.commentTextView = commentTextView;

            viewHolder.frontcoverLayout = frontcoverLayout;
            viewHolder.backbottomLayout = backbottomLayout;


            convertView.setTag(viewHolder);

        } else {
            // 재사용 할 때
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // 데이터
        Page page = mData.get(position);

        // font 지정

        viewHolder.titleTextView.setTypeface(mKopubBatangMediumTypeface);
        viewHolder.contentTextView.setTypeface(mKopubBatangLightTypeface);
        viewHolder.commentTextView.setTypeface(mKopubBatangMediumTypeface);


        // 화면에 뿌리기
        viewHolder.titleTextView.setText(page.getTitle());
        viewHolder.contentTextView.setText(page.getContent());

//        viewHolder.imageTextView.setText(page.getImage());
        viewHolder.commentTextView.setText(page.getComment());


        // TODO 사진도 가져와 뿌리기
        // 선택된 사진이 없다면
        // page.getImage(String) = null 이라면
        if (page.getImage().equals("nothing")) {
//            viewHolder.pictureImageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            // 1. Glide로 이미지
            Glide.with(parent.getContext()).load(page.getImage()).into(viewHolder.pictureImageView);

            // 2. Glide로 썸네일 주기 -> 오류
//            Glide.with(parent.getContext())
//                    .loadFromMediaStore(Uri.parse(page.getImage()))
//                    .thumbnail(0.5f)
//                    .into(viewHolder.pictureImageView);

            // 3. 비트맵 형식으로 썸네일 주기
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(parent.getContext().getContentResolver(), Uri.parse(page.getImage()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
//            viewHolder.pictureImageView.setImageBitmap(thumbnail);
        }

        if (mData.get(position).getState() == 0) {
            viewHolder.titleTextView.setVisibility(View.VISIBLE);
            viewHolder.contentTextView.setVisibility(View.VISIBLE);
//            viewHolder.imageTextView.setVisibility(View.INVISIBLE);
            viewHolder.pictureImageView.setVisibility(View.INVISIBLE);
            viewHolder.commentTextView.setVisibility(View.INVISIBLE);

            viewHolder.frontcoverLayout.setVisibility(View.INVISIBLE);
            viewHolder.backbottomLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.titleTextView.setVisibility(View.INVISIBLE);
            viewHolder.contentTextView.setVisibility(View.INVISIBLE);
//            viewHolder.imageTextView.setVisibility(View.VISIBLE);
            viewHolder.pictureImageView.setVisibility(View.VISIBLE);
            viewHolder.commentTextView.setVisibility(View.VISIBLE);

            viewHolder.frontcoverLayout.setVisibility(View.VISIBLE);
            viewHolder.backbottomLayout.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }



    /*
      클릭되면 setSelect를 소환
     */

    private long mSelectedId = -1;

    public void setSelect(long id) {
        mSelectedId = id;
    }

    private static class ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView imageTextView;
        ImageView pictureImageView;
        TextView commentTextView;

        // 커버, bottom 폴라로이드 이미지
        LinearLayout frontcoverLayout;
        LinearLayout backbottomLayout;
    }


}
