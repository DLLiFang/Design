package com.xanthus.design.constract;

import com.xanthus.design.BasePresenter;
import com.xanthus.design.BaseView;
import com.xanthus.design.bean.FileBean;

import java.util.List;

/**
 * Created by liyiheng on 16/11/9.
 */

public interface FileConstract {
    interface FileView extends BaseView<FilePresenter> {
        void refreshUI(List<FileBean> data);
        void showToast(String msg);
    }

    interface FilePresenter extends BasePresenter {
        void initData();

        void uploadFile(String path);
    }

}
