package com.dengzi.greendaoorrealm.our;

/**
 * @author Djk
 * @Title:
 * @Time: 2017/8/28.
 * @Version:1.0.0
 */
public class MyUser {

    private Long id;
    private int age;
    private String name;

    public MyUser() {
    }

    public MyUser(Long id, int age, String name) {
        this.id = id;
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
