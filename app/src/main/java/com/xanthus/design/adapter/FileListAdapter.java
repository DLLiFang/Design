package com.xanthus.design.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xanthus.design.R;
import com.xanthus.design.bean.FileBean;
import com.xanthus.design.bean.User;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by liyiheng on 16/11/9.
 */

public class FileListAdapter
        extends RecyclerView.Adapter<FileListAdapter.FileHolder>
        implements AdapterInterf<FileBean>, View.OnClickListener {
    private final SimpleDateFormat simpleDateFormat;
    /**
     * Instantiates a layout XML file into itemView
     */
    private LayoutInflater layoutInflater;
    /**
     * OnItemClickListener
     */
    private ItemClickCallback<FileBean> itemClick;

    public FileListAdapter(Context context) {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        FileBean fileBean = mData.get(position);
        holder.itemView.setTag(fileBean);
        holder.itemView.setOnClickListener(this);

        holder.fileName.setText(fileBean.getName());
        String date = simpleDateFormat.format(new Date(fileBean.getCreatetime()*1000 ));
        User user = fileBean.getUser();
        String nick = "";
        if (user != null) {
            if (!TextUtils.isEmpty(user.getNickname())) {
                nick = user.getNickname();
            } else {
                nick = user.getUsername();
            }
        }
        holder.timeAndAuthor.setText(TextUtils.concat(nick, "上传于", date));

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private List<FileBean> mData;

    @Override
    public void updateRes(List<FileBean> date) {
        if (date != null) {
            mData = date;
            notifyDataSetChanged();
        }
    }

    @Override
    public void addRes(List<FileBean> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
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
        TextView fileName;
        TextView timeAndAuthor;

        public FileHolder(View itemView) {
            super(itemView);
            fileName = (TextView) itemView.findViewById(R.id.item_file_name);
            timeAndAuthor = (TextView) itemView.findViewById(R.id.item_file_timeAndUser);
        }
    }
}
