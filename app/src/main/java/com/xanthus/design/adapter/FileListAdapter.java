package com.xanthus.design.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xanthus.design.R;
import com.xanthus.design.bean.FileBean;

import java.util.List;

/**
 * Created by liyiheng on 16/11/9.
 */

public class FileListAdapter
        extends RecyclerView.Adapter<FileListAdapter.FileHolder>
        implements AdapterInterf<FileBean>, View.OnClickListener {
    /**
     * Instantiates a layout XML file into itemView
     */
    private LayoutInflater layoutInflater;
    /**
     * OnItemClickListener
     */
    private ItemClickCallback<FileBean> itemClick;

    public FileListAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setItemClick(ItemClickCallback<FileBean> cb) {
        this.itemClick = cb;
    }

    @Override
    public FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_file_list, parent, false);
        return new FileHolder(view);
    }

    @Override
    public void onBindViewHolder(FileHolder holder, int position) {
        // TODO: 16/11/9 setTag(bean)
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public void updateRes(List<FileBean> date) {

    }

    @Override
    public void addRes(List<FileBean> data) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_file_list_root:
                if (itemClick != null) {
                    itemClick.onItemClick(null); // todo Can't be NULL
                }
                break;
        }
    }


    public class FileHolder extends RecyclerView.ViewHolder {

        public FileHolder(View itemView) {
            super(itemView);
        }
    }
}
