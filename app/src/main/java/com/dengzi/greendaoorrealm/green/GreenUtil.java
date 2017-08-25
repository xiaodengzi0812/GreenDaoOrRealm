package com.dengzi.greendaoorrealm.green;


import android.os.SystemClock;
import android.widget.TextView;

import com.dengzi.greendaoorrealm.MyApp;
import com.dengzi.greendaoorrealm.gen.GreenUserDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Djk
 * @Title: GreenDao工具类
 * @Time: 2017/8/24.
 * @Version:1.0.0
 */
public class GreenUtil {
    private GreenUserDao userDao;
    private List<GreenUser> greenUserList = new ArrayList<>();

    private long dbCount = 0;
    private TextView stateTv;
    private TextView countTv;

    public GreenUtil(TextView stateTv, TextView countTv) {
        this.stateTv = stateTv;
        this.countTv = countTv;
        userDao = MyApp.getInstence().getDaoSession().getGreenUserDao();
    }

    /**
     * 添加
     */
    public void add(int count) {
        greenUserList.clear();
        for (long i = dbCount; i < count + dbCount; i++) {
            GreenUser user = new GreenUser(i, 20, "name");
            greenUserList.add(user);
        }

        long start = SystemClock.currentThreadTimeMillis();
        for (GreenUser greenUser : greenUserList) {
            userDao.insert(greenUser);
        }
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
            userDao.deleteByKey(i);
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
        List<GreenUser> userList = userDao.queryBuilder().where(GreenUserDao.Properties.Id.le(count)).build().list();
        for (GreenUser greenUser : userList) {
            greenUser.setName("fuck");
            userDao.update(greenUser);
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
        List<GreenUser> userList = userDao.queryBuilder().where(GreenUserDao.Properties.Id.le(num)).build().list();
        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("查询" + userList.size() + "条数据耗时" + dis);
    }

    public void refreshCountTv() {
        List<GreenUser> userList = userDao.loadAll();
        dbCount = userList.size();
        countTv.setText(dbCount + "条");
    }
}
