package com.wph.goodread;

import android.view.View;

/**
 * Created by wph on 2017/2/10.
 */

public interface BaseView<T> {

    /**
     * set the presenter of mvp
     *
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * init the views of fragment
     *
     * @param view
     */

    void initViews(View view);

}
