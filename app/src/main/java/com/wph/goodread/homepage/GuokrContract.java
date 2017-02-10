package com.wph.goodread.homepage;

import com.wph.goodread.BasePresenter;
import com.wph.goodread.BaseView;
import com.wph.goodread.bean.GuokrHandpickNews;

import java.util.ArrayList;

/**
 * Created by wph on 2017/2/10.
 */

public interface GuokrContract {

    interface View extends BaseView<Presenter> {

        void showError();

        void showResults(ArrayList<GuokrHandpickNews.result> list);

        void showLoading();

        void stopLoading();
    }


    interface Presenter extends BasePresenter {

        void loadPosts();

        void refresh();

        void startReading(int position);

        void fellLucky();

    }

}
