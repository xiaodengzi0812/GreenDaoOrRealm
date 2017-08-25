package com.dengzi.greendaoorrealm.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @Title: Realm类型的业务bean
 * @Author: djk
 * @Time: 2017/8/25
 * @Version:1.0.0
 */
public class RealmUser extends RealmObject {

    @PrimaryKey
    private Long id;
    private int age;
    private String name;

    public RealmUser() {

    }

    public RealmUser(Long id, int age, String name) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
