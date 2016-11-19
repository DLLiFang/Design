package com.xanthus.design.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.xanthus.design.R;
import com.xanthus.design.adapter.BBSAdapter;
import com.xanthus.design.adapter.ItemClickCallback;
import com.xanthus.design.api.LApi;
import com.xanthus.design.api.LSubscriber;
import com.xanthus.design.bean.Topic;
import com.xanthus.design.bean.Wrapper2;
import com.xanthus.design.utils.LToast;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class BBSFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ItemClickCallback<Topic>, View.OnClickListener {
    public static final String TAG = "BBSFragment";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private BBSAdapter mAdapter;

    public static BBSFragment newInstance() {
        BBSFragment fragment = new BBSFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
        //mParam1 = getArguments().getString(ARG_PARAM1);
        //mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    private CompositeSubscription compositeSubscription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        compositeSubscription = new CompositeSubscription();
        layout = inflater.inflate(R.layout.fragment_bb, container, false);
        initView();
        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    private void initView() {
        swipeRefresh = ((SwipeRefreshLayout) layout.findViewById(R.id.bbs_swiperefresh));
        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView = ((RecyclerView) layout.findViewById(R.id.bbs_recycler));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BBSAdapter(getContext());
        mAdapter.setItemClickCallback(this);
        mRecyclerView.setAdapter(mAdapter);
        layout.findViewById(R.id.fab_bbs).setOnClickListener(this);
        initData();
    }


    @Override
    public void onRefresh() {
        initData();
    }

    private void initData() {
        Subscription subscribe = LApi.INSTANCE.getTopics(0, 20).subscribe(new LSubscriber<Wrapper2<Topic>>() {
            @Override
            public void onNext(Wrapper2<Topic> topicWrapper2) {
                swipeRefresh.setRefreshing(false);
                mAdapter.updateRes(topicWrapper2.getResult());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                swipeRefresh.setRefreshing(false);
            }
        });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void onItemClick(Topic bean) {
        LToast.show(getContext(), "点击了条目,需跳转至评论列表页");// TODO: 16/11/10 跳转,附带id
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_bbs:
                startActivity(new Intent(getContext(), AddTopicActivity.class));
                break;
        }
    }
}
