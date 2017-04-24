package com.hj.diaryproject.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hj.diaryproject.R;
import com.hj.diaryproject.models.Page;
import com.hj.diaryproject.utils.MyUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.hj.diaryproject.TypefaceManager.mKopubBatangLightTypeface;
import static com.hj.diaryproject.TypefaceManager.mKopubBatangMediumTypeface;

/**
 * Created by USER on 2017-04-05.
 */

public class LandAdapter extends RecyclerView.Adapter<LandAdapter.ViewHolder> {

    private Context mContext;
    private List<Page> mData;
    private ViewGroup mParent;


    public LandAdapter(Context mContext, List<Page> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mParent = parent;
        // 뷰를 새로 만들 때
        View convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.land_item_page, parent, false);
        return new ViewHolder(convertView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // 데이터
        Page page = mData.get(position);

        // font 지정
        holder.titleTextView.setTypeface(mKopubBatangMediumTypeface);
        holder.contentTextView.setTypeface(mKopubBatangLightTypeface);
        holder.commentTextView.setTypeface(mKopubBatangMediumTypeface);

        // 화면에 뿌리기
        holder.titleTextView.setText(page.getTitle());
        holder.contentTextView.setText(page.getContent());

//        holder.imageTextView.setText(page.getImage());
        holder.commentTextView.setText(page.getComment());


        // 선택된 사진이 없다면
        // page.getImage(String) = null 이라면
        if (page.getImage().equals("nothing")) {
            holder.pictureImageView.setImageResource(R.drawable.background3);
        } else {
            // 1. Glide로 이미지
            Glide.with(mParent.getContext()).load(page.getImage()).into(holder.pictureImageView);

            // 2. Glide로 썸네일 주기 -> 오류
//            Glide.with(parent.getContext())
//                    .loadFromMediaStore(Uri.parse(page.getImage()))
//                    .thumbnail(0.5f)
//                    .into(holder.pictureImageView);

            // 3. 비트맵 형식으로 썸네일 주기
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(parent.getContext().getContentResolver(), Uri.parse(page.getImage()));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 100, 100);
//            holder.pictureImageView.setImageBitmap(thumbnail);
        }

        if (mData.get(position).getState() == 0) {
            holder.titleTextView.setVisibility(View.VISIBLE);
            holder.contentTextView.setVisibility(View.VISIBLE);
//            holder.imageTextView.setVisibility(View.INVISIBLE);
            holder.pictureImageView.setVisibility(View.INVISIBLE);
            holder.commentTextView.setVisibility(View.INVISIBLE);

            holder.frontcoverLayout.setVisibility(View.INVISIBLE);
            holder.backbottomLayout.setVisibility(View.VISIBLE);
        } else {
            holder.titleTextView.setVisibility(View.INVISIBLE);
            holder.contentTextView.setVisibility(View.INVISIBLE);
//            viewHolder.imageTextView.setVisibility(View.VISIBLE);
            holder.pictureImageView.setVisibility(View.VISIBLE);
            holder.commentTextView.setVisibility(View.VISIBLE);

            holder.frontcoverLayout.setVisibility(View.VISIBLE);
            holder.backbottomLayout.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 아이템(페이지) 클릭시 -> 뒤집기
                 * {@link com.hj.diaryproject.ScrollingActivity#itemClick(com.hj.diaryproject.utils.MyUtils.ItemClickEvent)}
                 */
                MyUtils.ItemClickEvent event = new MyUtils.ItemClickEvent(position);
                EventBus.getDefault().post(event);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /**
                 * 아이템(페이지) 롱 클릭시 -> 편집
                 * {@link com.hj.diaryproject.ScrollingActivity#itemLongClick(com.hj.diaryproject.utils.MyUtils.ItemLongClickEvent)}
                 */
                MyUtils.ItemLongClickEvent event = new MyUtils.ItemLongClickEvent(position);
                EventBus.getDefault().post(event);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
        TextView imageTextView;
        ImageView pictureImageView;
        TextView commentTextView;

        // 커버, bottom 폴라로이드 이미지
        LinearLayout frontcoverLayout;
        LinearLayout backbottomLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            // 레이아웃 들고 오기
            TextView titleTextView = (TextView) itemView.findViewById(R.id.title_textiview);
            TextView contentTextView = (TextView) itemView.findViewById(R.id.content_textview);
            TextView imageTextView = (TextView) itemView.findViewById(R.id.image_textview);
            ImageView pictureImageView = (ImageView) itemView.findViewById(R.id.picture_imageview);
            TextView commentTextView = (TextView) itemView.findViewById(R.id.comment_textview);

            LinearLayout frontcoverLayout = (LinearLayout) itemView.findViewById(R.id.frontcover_layout);
            LinearLayout backbottomLayout = (LinearLayout) itemView.findViewById(R.id.backcbottom_layout);


            // 뷰 홀더에 넣는다
            this.titleTextView = titleTextView;
            this.contentTextView = contentTextView;
            this.imageTextView = imageTextView;
            this.pictureImageView = pictureImageView;
            this.commentTextView = commentTextView;

            this.frontcoverLayout = frontcoverLayout;
            this.backbottomLayout = backbottomLayout;


        }
    }

    public void swap(List<Page> data) {
        mData = data;
    }
}
