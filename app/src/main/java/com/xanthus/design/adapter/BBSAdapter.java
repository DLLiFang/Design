package com.xanthus.design.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xanthus.design.R;
import com.xanthus.design.bean.Topic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liyiheng on 16/11/10.
 */

public class BBSAdapter extends RecyclerView.Adapter<BBSAdapter.BBSHolder> implements AdapterInterf<Topic>, View.OnClickListener {
    private final SimpleDateFormat simpleDateFormat;
    private LayoutInflater mInflater;
    private ItemClickCallback<Topic> itemClickCallback;

    public void setItemClickCallback(ItemClickCallback<Topic> itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public BBSAdapter(Context context) {
        mData = new ArrayList<>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private List<Topic> mData;

    @Override
    public void updateRes(List<Topic> date) {
        this.mData = date;
        notifyDataSetChanged();
    }

    @Override
    public void addRes(List<Topic> data) {

    }

    @Override
    public BBSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_bbs, parent, false);
        return new BBSHolder(view);
    }

    @Override
    public void onBindViewHolder(BBSHolder holder, int position) {
        Topic topic = mData.get(position);
        holder.itemView.setTag(topic);
        holder.itemView.setOnClickListener(this);
        holder.nickname.setText(topic.getUser().getNickname());
        holder.content.setText(topic.getContent());
        holder.time.setText(simpleDateFormat.format(new Date(topic.getCreatetime()*1000)));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        switch (v.getId()) {
            case R.id.item_bbs_root:
                if (itemClickCallback != null) {
                    itemClickCallback.onItemClick((Topic) tag);// TODO: 16/11/10 -------
                }
                break;
        }
    }


    class BBSHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView time;
        TextView nickname;
        TextView content;


        public BBSHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.item_bbs_avatar);
            time = (TextView) itemView.findViewById(R.id.item_bbs_time);
            nickname = (TextView) itemView.findViewById(R.id.item_bbs_nickname);
            content = (TextView) itemView.findViewById(R.id.item_bbs_content);
        }
    }
}
