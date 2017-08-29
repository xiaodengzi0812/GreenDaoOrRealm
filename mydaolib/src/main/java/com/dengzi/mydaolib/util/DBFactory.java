package com.dengzi.mydaolib.util;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/8/28.
 * @Version:1.0.0
 */
public class DBFactory {
    private static DBFactory dbFactory;
    private SQLiteDatabase db;

    private DBFactory() {
        String dbPath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File dbPathFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dzdb" + File.separator);
            if (!dbPathFile.exists()) {
                dbPathFile.mkdir();
            }

            File dbFile = new File(dbPathFile.getAbsolutePath(), "test.db");
            if (dbFile.exists()) {
                dbFile.mkdir();
            }
            dbPath = dbFile.getAbsolutePath();
        }
        if (TextUtils.isEmpty(dbPath))
            return;
        db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    /*单例*/
    public static DBFactory getInstence() {
        if (dbFactory == null) {
            synchronized (DBFactory.class) {
                if (dbFactory == null) {
                    dbFactory = new DBFactory();
                }
            }
        }
        return dbFactory;
    }

    public <T> IDao<T> getDao(Class<T> clazz) {
        IDao<T> idao = new DaoImpl<>();
        idao.init(db, clazz);
        return idao;
    }
}
