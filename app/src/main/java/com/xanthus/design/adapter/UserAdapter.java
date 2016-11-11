package com.xanthus.design.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xanthus.design.R;
import com.xanthus.design.bean.User;

import java.util.List;

/**
 * Created by liyiheng on 16/11/10.
 */

public class UserAdapter
        extends RecyclerView.Adapter<UserAdapter.UserHolder>
        implements AdapterInterf<User>, View.OnClickListener {
    private LayoutInflater mInflater;
    private ItemClickCallback<User> itemClickCallback;

    public void setItemClickCallback(ItemClickCallback<User> itemClickCallback) {
        this.itemClickCallback = itemClickCallback;
    }

    public UserAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_user, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        //holder.itemView.setTag(bean);
        holder.itemView.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public void updateRes(List<User> date) {

    }

    @Override
    public void addRes(List<User> data) {

    }

    @Override
    public void onClick(View v) {
        User tag = (User) v.getTag();
        switch (v.getId()){
            case R.id.item_user_root:
                if (itemClickCallback!=null){
                    itemClickCallback.onItemClick(tag);
                }
                break;
        }
    }

    class UserHolder extends RecyclerView.ViewHolder {

        public UserHolder(View itemView) {
            super(itemView);
        }
    }
}
