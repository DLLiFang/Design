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
import com.xanthus.design.adapter.ItemClickCallback;
import com.xanthus.design.adapter.UserAdapter;
import com.xanthus.design.bean.User;
import com.xanthus.design.utils.LToast;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class FollowedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ItemClickCallback<User> {

    public static final String TAG = "FollowedFragment";
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private CompositeSubscription compositeSubscription;
    private UserAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_followed, container, false);
        compositeSubscription = new CompositeSubscription();
        initView();
        return layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.unsubscribe();
    }

    private void initView() {
        mAdapter = new UserAdapter(getContext());
        mAdapter.setItemClickCallback(this);
        mRecyclerView = ((RecyclerView) layout.findViewById(R.id.follows_recycler));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        swipeRefresh = ((SwipeRefreshLayout) layout.findViewById(R.id.follows_swipe));
        swipeRefresh.setOnRefreshListener(this);

    }

    private void initData() {
        Subscription subscribe = Observable.timer(1000, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        swipeRefresh.setRefreshing(false);
                        mAdapter.updateRes(null);// TODO: 16/11/10 =================================
                    }
                });
        compositeSubscription.add(subscribe);
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onItemClick(User bean) {
        //LToast.show(getContext(), "itemClicked");
        startActivity(new Intent(getContext(), ProfileActivity.class));
    }
}
