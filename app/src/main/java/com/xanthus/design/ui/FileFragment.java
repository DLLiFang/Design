package com.xanthus.design.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xanthus.design.MainActivity;
import com.xanthus.design.R;
import com.xanthus.design.adapter.FileListAdapter;
import com.xanthus.design.adapter.ItemClickCallback;
import com.xanthus.design.bean.FileBean;
import com.xanthus.design.constract.FileConstract;
import com.xanthus.design.presenter.FilePresenter;
import com.xanthus.design.utils.LToast;

import java.util.List;

import cn.qqtheme.framework.picker.FilePicker;
import cn.qqtheme.framework.util.StorageUtils;

public class FileFragment
        extends
        BaseFragment
        implements
        FileConstract.FileView,
        SwipeRefreshLayout.OnRefreshListener,
        ItemClickCallback<FileBean> {
    public static final String TAG = "FileFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private FileConstract.FilePresenter mPresenter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private FileListAdapter mAdapter;


    public FileFragment() {
    }

    public static FileFragment newInstance(String param1, String param2) {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FilePresenter(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
        mPresenter = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_file, container, false);
        initView();
        return layout;
    }

    private void initView() {
        mRecyclerView = ((RecyclerView) layout.findViewById(R.id.file_list_recycler));
        swipeRefresh = ((SwipeRefreshLayout) layout.findViewById(R.id.file_list_swipe));
        swipeRefresh.setOnRefreshListener(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new FileListAdapter(getContext());
        mAdapter.setItemClick(this);
        mRecyclerView.setAdapter(mAdapter);
        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab_file);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                FilePicker picker = new FilePicker(getActivity(), FilePicker.FILE);
                picker.setShowHideDir(false);
                picker.setRootPath(StorageUtils.getRootPath(getContext()) + "Download/");
                //picker.setAllowExtensions(new String[]{".apk"});
                picker.setOnFilePickListener(new FilePicker.OnFilePickListener() {
                    @Override
                    public void onFilePicked(String currentPath) {
                        mPresenter.uploadFile(currentPath);
                    }
                });
                picker.show();
            }
        });
    }


    @Override
    public void onRefresh() {
        mPresenter.initData();
    }

    @Override
    public void refreshUI(List<FileBean> data) {
        mAdapter.updateRes(data);
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showToast(String msg) {
        LToast.show(getContext(), msg);
    }

    @Override
    public void onItemClick(FileBean bean) {
        new MaterialDialog.Builder(getContext())
                .items("下载", "查看作者", "取消")
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position) {
                            case 0:
                                LToast.show(getContext(), "点击了下载");//TODO
                                break;
                            case 1:
                                LToast.show(getContext(), "点击了查看作者"); // TODO
                                break;
                        }
                    }
                }).build().show();
    }
}
