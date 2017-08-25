package com.dengzi.greendaoorrealm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dengzi.greendaoorrealm.R;
import com.dengzi.greendaoorrealm.gen.GreenUserDao;
import com.dengzi.greendaoorrealm.green.GreenUser;
import com.dengzi.greendaoorrealm.green.GreenUtil;
import com.dengzi.greendaoorrealm.realm.RealmUser;
import com.dengzi.greendaoorrealm.realm.RealmUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends Activity {
    private int count = 1;// 测试条数
    private int type = 0;// 测试类型（测试哪一个数据库框架）

    private RadioGroup countRg;
    private RadioGroup typeRg;
    private TextView greenCountTv;
    private TextView realmCountTv;
    private TextView greenTv;
    private TextView realmTv;

    private GreenUtil greenUtil;
    private RealmUtil realmUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        countRg = (RadioGroup) findViewById(R.id.count_rg);
        typeRg = (RadioGroup) findViewById(R.id.type_rg);
        greenCountTv = (TextView) findViewById(R.id.green_count_tv);
        realmCountTv = (TextView) findViewById(R.id.realm_count_tv);
        greenTv = (TextView) findViewById(R.id.green_tv);
        realmTv = (TextView) findViewById(R.id.realm_tv);
    }

    private void initData() {
        greenUtil = new GreenUtil(greenTv, greenCountTv);
        greenUtil.refreshCountTv();
        realmUtil = new RealmUtil(realmTv, realmCountTv);
        realmUtil.refreshCountTv();
    }

    /*增*/
    public void addClick(View view) {
        if (type == 0) {
            greenUtil.add(count);
        } else {
            realmUtil.add(count);
        }
    }

    /*删*/
    public void deleClick(View view) {
        if (type == 0) {
            greenUtil.dele(count);
        } else {
            realmUtil.dele(count);
        }
    }

    /*改*/
    public void changeClick(View view) {
        if (type == 0) {
            greenUtil.change(count);
        } else {
            realmUtil.change(count);
        }
    }

    /*查*/
    public void searchClick(View view) {
        if (type == 0) {
            greenUtil.search(count);
        } else {
            realmUtil.search(count);
        }
    }

    private void initListener() {
        typeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                type = i == R.id.type_0 ? 0 : 1;
            }
        });
        countRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                count = i == R.id.count_1 ? 1 : i == R.id.count_10 ? 10
                        : i == R.id.count_100 ? 100
                        : i == R.id.count_1000 ? 1000
                        : i == R.id.count_10000 ? 10000 : 1;
            }
        });
    }

}
