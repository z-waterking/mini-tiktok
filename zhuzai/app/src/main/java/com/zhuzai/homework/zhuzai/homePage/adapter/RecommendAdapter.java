package com.zhuzai.homework.zhuzai.homePage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.bean.Feed;
import com.zhuzai.homework.zhuzai.bean.Recommend_Feed;

import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder>{

    public List<Recommend_Feed> mData;
    private final ListItemClickListener mOnClickListener;
    public RecommendAdapter(List<Recommend_Feed> data, ListItemClickListener listener){
        mData = data;
        mOnClickListener = listener;
    }
    public void update_Recommend_Feeds(List<Recommend_Feed> feeds){
        mData = feeds;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.activity_recommend_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.video_url = mData.get(position).getVideo_url();
        holder.user_name = mData.get(position).getUser_name();
        holder.image_url = mData.get(position).getImage_url();
        holder.student_id  = mData.get(position).getStudent_id();
//        holder.content = mData.get(position).getContent();
        //设置学生ID，姓名，和视频内容
        holder.user_name_view.setText(holder.user_name);
        holder.student_id_view.setText(holder.student_id);
//        holder.content_view.setText(holder.content);
        //增加封面
        ImageView imageView = holder.image_cover_view;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(imageView.getContext()).load(holder.image_url).into(holder.image_cover_view);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //绑定播放器
        private LinearLayout linearLayout;
        private TextView student_id_view;
        private TextView user_name_view;
        private TextView content_view;
        private ImageView image_cover_view;
        private String student_id;
        private String image_url;
        private String user_name;
        private String video_url;
        private String content;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayout);
            student_id_view = (TextView) itemView.findViewById(R.id.student_id);
            user_name_view = (TextView) itemView.findViewById(R.id.user_name);
            content_view = (TextView) itemView.findViewById(R.id.content);
            image_cover_view = (ImageView) itemView.findViewById(R.id.image_cover);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(video_url, user_name, image_url, student_id);
            }
        }
    }

    public interface ListItemClickListener {
        //跳转到详情页
        void onListItemClick(String video_url, String user_name, String image_url, String student_id);
    }
}
