package com.xanthus.design.adapter;

import java.util.List;

/**
 * Created by liyiheng on 16/11/9.
 */

public interface AdapterInterf<T> {
    void updateRes(List<T> date);

    void addRes(List<T> data);
}
