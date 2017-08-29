package com.dengzi.mydaolib.util;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/8/28.
 * @Version:1.0.0
 */
public interface IDao<T> {

    void init(SQLiteDatabase db, Class<T> clazz);

    /**
     * 添加
     */
    boolean add(T t);

    /**
     * 添加
     */
    boolean add(List<T> tList);

    /**
     * 删除
     */
    boolean dele(T t);

    /**
     * 修改
     */
    boolean change(T t);

    /**
     * 查询全部
     */
    List<T> search();

    /**
     * 条件查询
     */
    List<T> search(String sql, String[] selectionArgs);
}
