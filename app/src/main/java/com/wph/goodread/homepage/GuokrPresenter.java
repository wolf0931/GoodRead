package com.wph.goodread.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.wph.goodread.bean.GuokrHandpickNews;
import com.wph.goodread.bean.StringModelImpl;
import com.wph.goodread.db.DatabaseHelper;
import com.wph.goodread.interfaze.OnStringListener;
import com.wph.goodread.util.Api;
import com.wph.goodread.util.NetworkState;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wph on 2017/2/10.
 */

public class GuokrPresenter implements GuokrContract.Presenter {

    private GuokrContract.View view;

    private Context context;

    private StringModelImpl model;

    private ArrayList<GuokrHandpickNews.result> list = new ArrayList<GuokrHandpickNews.result>();

    private Gson gson = new Gson();

    private DatabaseHelper dbHelper;

    private SQLiteDatabase db;


    public GuokrPresenter(Context context, GuokrContract.View view) {
        this.context = context;
        this.view = view;
        view.setPresenter(this);
        dbHelper = new DatabaseHelper(context, "History.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void loadPosts() {
        view.showLoading();
        if (NetworkState.networkConnected(context)) {
            model.load(Api.GUOKR_ARTICLES, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    list.clear();
                    try {
                        GuokrHandpickNews question = gson.fromJson(result, GuokrHandpickNews.class);
                        for (GuokrHandpickNews.result re : question.getResult()) {
                            list.add(re);

                            try {
                                if (!queryIfIDExists(re.getId())) {
                                    db.beginTransaction();
                                    ContentValues values = new ContentValues();
                                    values.put("guokr_id", re.getId());
                                    values.put("guokr_news", gson.toJson(re));
                                    values.put("guokr_content", "");
                                    values.put("guokr_time", (long) re.getDate_picked());
                                    db.insert("Guokr", null, values);
                                    values.clear();
                                    db.setTransactionSuccessful();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                db.endTransaction();
                            }
                        }

                    } catch (JsonSyntaxException e) {
                        view.showError();
                    }
                    view.showResults(list);
                    view.stopLoading();
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showError();
                }
            });
        } else {
            Cursor cursor = db.query("Guokr", null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    GuokrHandpickNews.result result = gson.fromJson(cursor.getString(cursor.getColumnIndex("guokr_news")), GuokrHandpickNews.result.class);
                    list.add(result);
                } while (cursor.moveToNext());
            }
            cursor.close();
            view.stopLoading();
            view.showResults(list);
            //当第一次安装应用，并且没有打开网络时
            //此时既无法网络加载，也无法本地加载
            if (list.isEmpty()) {
                view.showError();
            }
        }
    }

    @Override
    public void refresh() {
        loadPosts();
    }

    @Override
    public void startReading(int position) {
        GuokrHandpickNews.result item = list.get(position);

    }

    @Override
    public void fellLucky() {
        if (list.isEmpty()) {
            view.showError();
            return;
        }
        startReading(new Random().nextInt(list.size()));
    }

    @Override
    public void start() {
        loadPosts();
    }

    private boolean queryIfIDExists(int id) {
        Cursor cursor = db.query("Guokr", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("guokr_id"))) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }
}
