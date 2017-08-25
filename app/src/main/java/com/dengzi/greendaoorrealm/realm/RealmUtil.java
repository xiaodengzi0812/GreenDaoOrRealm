package com.dengzi.greendaoorrealm.realm;

import android.os.SystemClock;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * @Title: Realm工具类
 * @Author: djk
 * @Time: 2017/8/25
 * @Version:1.0.0
 */
public class RealmUtil {
    private Realm myOtherRealm;
    private List<RealmUser> realmUserlist = new ArrayList<>();
    private long dbCount = 0;
    private TextView stateTv;
    private TextView countTv;

    public RealmUtil(TextView stateTv, TextView countTv) {
        this.stateTv = stateTv;
        this.countTv = countTv;
        myOtherRealm = Realm.getDefaultInstance();
    }

    /**
     * 添加
     */
    public void add(int count) {
        realmUserlist.clear();
        for (long i = dbCount; i < count + dbCount; i++) {
            RealmUser user = new RealmUser(i, 20, "name");
            realmUserlist.add(user);
        }

        long start = SystemClock.currentThreadTimeMillis();
        myOtherRealm.beginTransaction();
        for (RealmUser realmUser : realmUserlist) {
            myOtherRealm.copyToRealmOrUpdate(realmUser);
        }
        myOtherRealm.commitTransaction();
        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("新增" + count + "条数据耗时" + dis);
        refreshCountTv();
    }

    /**
     * 删除
     *
     * @param count
     */
    public void dele(int count) {
        if (count > dbCount) {
            stateTv.setText("error...");
            return;
        }
        long start = SystemClock.currentThreadTimeMillis();
        for (long i = dbCount - count; i < dbCount; i++) {
            RealmUser user = myOtherRealm.where(RealmUser.class).equalTo("id", i).findFirst();
            myOtherRealm.beginTransaction();
            user.deleteFromRealm();
            myOtherRealm.commitTransaction();
        }
        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("删除" + count + "条数据耗时" + dis);
        refreshCountTv();
    }

    /**
     * 修改
     *
     * @param count
     */
    public void change(int count) {
        if (count > dbCount) {
            stateTv.setText("error...");
            return;
        }
        long start = SystemClock.currentThreadTimeMillis();
        for (long i = 0; i < count; i++) {
            RealmUser user = myOtherRealm.where(RealmUser.class).equalTo("id", i).findFirst();
            myOtherRealm.beginTransaction();
            user.setName("fuck");
            myOtherRealm.commitTransaction();
        }
        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("修改" + count + "条数据耗时" + dis);
    }

    /**
     * 查询
     */
    public void search(int num) {
        long start = SystemClock.currentThreadTimeMillis();
        RealmResults<RealmUser> realmResults = myOtherRealm.where(RealmUser.class).between("id", 0, num).findAll();
        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("查询" + realmResults.size() + "条数据耗时" + dis);
    }

    public void refreshCountTv() {
        RealmResults<RealmUser> realmResults = myOtherRealm.where(RealmUser.class).findAll();
        dbCount = realmResults.size();
        countTv.setText(dbCount + "条");
    }

}
