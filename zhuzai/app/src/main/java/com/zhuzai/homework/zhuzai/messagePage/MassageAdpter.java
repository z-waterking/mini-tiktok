package com.zhuzai.homework.zhuzai.messagePage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.widget.CircleImageView;

import java.util.List;

/**
 * Created by admin on 2019/1/19.
 */



public class MassageAdpter extends RecyclerView.Adapter<MassageAdpter.MassageViewHolder>{

    private List<Message> list;

    private final ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    public MassageAdpter(List<Message> Messagelist,ListItemClickListener listener) {
        list = Messagelist;
        Log.d("list.size()",list.size()+"");
        mOnClickListener = listener;
        viewHolderCount = 0;

    }

    @NonNull
    @Override
    public MassageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.massage_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MassageViewHolder viewHolder = new MassageViewHolder(view);
        return viewHolder;
    }

    public int choosePic(String tpye)
    {
        int id;
        if(tpye.equals("TYPE_SYSTEM"))
            id=R.drawable.session_system_notice;
        else if(tpye.equals("TYPE_STRANGER"))
            id=R.drawable.session_stranger;
        else if(tpye.equals("TYPE_USER"))
            id=R.drawable.icon_girl;
        else if(tpye.equals("TYPE_ROBOT"))
            id=R.drawable.session_robot;
        else
            id=R.drawable.icon_micro_game_comment;
        return id;

    }

    @Override
    public void onBindViewHolder(@NonNull MassageViewHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MassageViewHolder extends  RecyclerView.ViewHolder {

        private final CircleImageView circleImageView;
        private final TextView title;
        private final TextView subtitle;
        private final TextView time;
        private final ImageView imageView;


        public MassageViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.massage_pic);
            title=itemView.findViewById(R.id.title);
            subtitle=itemView.findViewById(R.id.subtitle);
            time=itemView.findViewById(R.id.massage_time);
            imageView=itemView.findViewById(R.id.robot_notice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    if (mOnClickListener != null) {
                        mOnClickListener.onListItemClick(clickedPosition);
                    }
                }
            });

        }

        public  void bind(int position){

            title.setText(list.get(position).getTitle());
            subtitle.setText(list.get(position).getDescription());
            time.setText(list.get(position).getTime());
            int id =choosePic(list.get(position).getIcon());
            circleImageView.setImageResource(id);
            if(list.get(position).isOfficial())
                imageView.setVisibility(View.VISIBLE);
            else
                imageView.setVisibility(View.INVISIBLE);


        }

    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
