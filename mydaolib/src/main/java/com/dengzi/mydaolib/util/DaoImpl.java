package com.dengzi.mydaolib.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/8/28.
 * @Version:1.0.0
 */
public class DaoImpl<T> implements IDao<T> {

    private SQLiteDatabase db;
    private Class<T> mClazz;

    @Override
    public void init(SQLiteDatabase db, Class<T> clazz) {
        this.db = db;
        this.mClazz = clazz;

        // 创建表
        /*
        String creaTTable = "create table user (_id integer PRIMARY KEY AUTOINCREMENT NOT NULL,name varchar,age int)";
        db.execSQL(creaTTable);
        */
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ").append(clazz.getSimpleName()).append(" (").append(getTableSql()).append(")");
        String creatTableStr = sb.toString();
        Log.e("dengzi", creatTableStr);
        db.execSQL(creatTableStr);
    }

    @Override
    public boolean add(T o) {
        /*
        ContentValues cv = new ContentValues();  //存储键值对
        cv.put("name","张三");
        cv.put("sex","男");
        sqWrite.insert("user",null,cv);
        //插入*/
        ContentValues cv = null;
        try {
            cv = getParams(o);
            db.insert(mClazz.getSimpleName(), null, cv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean add(List<T> ts) {
        db.beginTransaction();
        for (T t : ts) {
            add(t);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return true;
    }

    @Override
    public boolean dele(T o) {
//        db.delete("person", "age < ?", new String[]{"35"});

        return false;
    }

    @Override
    public boolean change(T o) {
//        db.update("person", cv, "name = ?", new String[]{"john"});
        return false;
    }

    @Override
    public List<T> search() {
        String sqlStr = "SELECT * FROM " + mClazz.getSimpleName();
        return serachDb(sqlStr, null);
    }

    @Override
    public List<T> search(String sql, String[] selectionArgs) {
        return serachDb(sql, selectionArgs);
    }

    private List<T> serachDb(String sqlStr, String[] selectionArgs) {
        List<T> queryList = new ArrayList<>();
        try {
            Cursor c = db.rawQuery(sqlStr, selectionArgs);
            while (c.moveToNext()) {
//            int _id = c.getInt(c.getColumnIndex("_id"));
//            String name = c.getString(c.getColumnIndex("name"));
//            int age = c.getInt(c.getColumnIndex("age"));
                Class clazz = mClazz;
                Object obj = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    String type = getDbType(field.getType().getSimpleName());
                    if (type.contains("String")) {
                        String value = c.getString(c.getColumnIndex(name));
                        field.set(obj, value);
                    } else if (type.contains("int")) {
                        int value = c.getInt(c.getColumnIndex(name));
                        field.set(obj, value);
                    } else if (type.contains("boolean")) {
                    } else if (type.contains("float")) {
                        Float value = c.getFloat(c.getColumnIndex(name));
                        field.set(obj, value);
                    } else if (type.contains("double")) {
                        Double value = c.getDouble(c.getColumnIndex(name));
                        field.set(obj, value);
                    } else if (type.contains("char")) {
                    } else if (type.contains("Long")) {
                        Long value = c.getLong(c.getColumnIndex(name));
                        field.set(obj, value);
                    }
                }
                queryList.add((T) obj);
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queryList;
    }

    /**
     * 获取建表参数
     *
     * @return
     */
    private String getTableSql() {
        // _id integer PRIMARY KEY AUTOINCREMENT NOT NULL,name varchar,age int
        Class clazz = mClazz;
        Field[] fields = clazz.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        if (fields != null) {
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                String type = getDbType(field.getType().getSimpleName());
                sb.append(", ").append(name).append(" ").append(type);
            }
        }
        return sb.toString().replaceFirst(",", "");
    }


    private String getDbType(String type) {
        String dbTypeStr = "";
        if (type.contains("String")) {
            dbTypeStr = "text";
        } else if (type.contains("int")) {
            dbTypeStr = "integer";
        } else if (type.contains("boolean")) {
            dbTypeStr = "boolean";
        } else if (type.contains("float")) {
            dbTypeStr = "float";
        } else if (type.contains("double")) {
            dbTypeStr = "double";
        } else if (type.contains("char")) {
            dbTypeStr = "double";
        } else if (type.contains("Long")) {
            dbTypeStr = "long";
        }
        return dbTypeStr;
    }


    /*获取操作参数*/
    private ContentValues getParams(Object obj) throws Exception {
        /*
        ContentValues cv = new ContentValues();  //存储键值对
        cv.put("name","张三");
        cv.put("sex","男");
        sqWrite.insert("user",null,cv);
        //插入*/
        ContentValues cv = new ContentValues();  //存储键值对
        Class clazz = mClazz;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object fieldObj = field.get(obj);
            String value = fieldObj.toString();
            cv.put(name, value);
        }
        return cv;
    }
}
