package com.dengzi.greendaoorrealm.our;

import android.database.Cursor;
import android.os.SystemClock;
import android.widget.TextView;

import com.dengzi.greendaoorrealm.gen.GreenUserDao;
import com.dengzi.greendaoorrealm.green.GreenUser;
import com.dengzi.mydaolib.util.DBFactory;
import com.dengzi.mydaolib.util.IDao;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/8/28.
 * @Version:1.0.0
 */
public class MyUtil {
    private IDao<MyUser> iDao;
    private List<MyUser> userlist = new ArrayList<>();

    private long dbCount = 0;
    private TextView stateTv;
    private TextView countTv;

    public MyUtil(TextView stateTv, TextView countTv) {
        this.stateTv = stateTv;
        this.countTv = countTv;
        iDao = DBFactory.getInstence().getDao(MyUser.class);
    }

    /**
     * 添加
     */
    public void add(int count) {
        userlist.clear();
        for (long i = dbCount; i < count + dbCount; i++) {
            MyUser user = new MyUser(i, 20, "name");
            userlist.add(user);
        }
        long start = SystemClock.currentThreadTimeMillis();
        iDao.add(userlist);
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

        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("修改" + count + "条数据耗时" + dis);
    }

    /**
     * 查询
     */
    public void search(int num) {
        long start = SystemClock.currentThreadTimeMillis();
        String sqlStr = "SELECT * FROM " + MyUser.class.getSimpleName() + " where id <= ?";
        String[] selectionArgs = {"" + num};
        List<MyUser> userList = iDao.search(sqlStr, selectionArgs);
        long end = SystemClock.currentThreadTimeMillis();
        long dis = end - start;
        stateTv.setText("查询" + userList.size() + "条数据耗时" + dis);
    }

    public void refreshCountTv() {
        userlist = iDao.search();
        dbCount = userlist.size();
        countTv.setText(dbCount + "条");
    }
}
