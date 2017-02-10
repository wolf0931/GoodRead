package com.wph.goodread.homepage;

import android.content.Context;

import com.google.gson.Gson;
import com.wph.goodread.bean.GuokrHandpickNews;

import java.util.ArrayList;

/**
 * Created by wph on 2017/2/10.
 */

public class GuokrPresenter implements GuokrContract.Presenter {

    private GuokrContract.View view;

    private Context context;

    private ArrayList<GuokrHandpickNews.result> list = new ArrayList<GuokrHandpickNews.result>();

    private Gson gson = new Gson();


    public GuokrPresenter(Context context, GuokrContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);

    }

    @Override
    public void loadPosts() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void startReading(int position) {

    }

    @Override
    public void fellLucky() {
        loadPosts();
    }

    @Override
    public void start() {

    }
}
