package com.dengzi.greendaoorrealm.green;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author Djk
 * @Title: GreenDao 类型的业务bean
 * @Time: 2017/8/24.
 * @Version:1.0.0
 */
@Entity
public class GreenUser {

    @Id(autoincrement = true)
    private Long id;
    private int age;
    private String name;

    @Generated(hash = 1678257977)
    public GreenUser() {
    }

    @Generated(hash = 1544400092)
    public GreenUser(Long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
