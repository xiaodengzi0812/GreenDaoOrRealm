package com.dengzi.greendaoorrealm;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.dengzi.greendaoorrealm.gen.DaoMaster;
import com.dengzi.greendaoorrealm.gen.DaoSession;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/8/24.
 * @Version:1.0.0
 */

public class MyApp extends Application {

    private static MyApp myApp;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static MyApp getInstence() {
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;

        /*初始化GreenDao*/
        initGreenDao();

        /*初始化Realm*/
        initRealm();
    }


    private void initGreenDao() {
        mHelper = new DaoMaster.DevOpenHelper(MyApp.getInstence(), "user.db");
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myRealm.realm")
                .deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }


}
