package com.xanthus.design.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xanthus.design.R;
import com.xanthus.design.bean.Topic;

import java.util.List;

/**
 * Created by liyiheng on 16/11/10.
 */

public class BBSAdapter extends RecyclerView.Adapter<BBSAdapter.BBSHolder> implements AdapterInterf<Topic>, View.OnClickListener {
    private LayoutInflater mInflater;
    private ItemClickCallback<Topic> itemClickCallback;

    public void setItemClickCallback(ItemClickCallback<Topic> itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public BBSAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void updateRes(List<Topic> date) {

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
        //holder.itemView.setTag(bean); // TODO: 16/11/10 ------
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_bbs_root:
                if (itemClickCallback != null) {
                    itemClickCallback.onItemClick(null);// TODO: 16/11/10 -------
                }
                break;
        }
    }


    class BBSHolder extends RecyclerView.ViewHolder {

        public BBSHolder(View itemView) {
            super(itemView);
        }
    }
}
