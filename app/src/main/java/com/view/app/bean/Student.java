package com.view.app.bean;

import android.text.TextUtils;

/**
 * AutoCompleteTextViewDemo
 *
 * @author xugang
 * @date 2017/12/11
 * @describe
 */

public class Student {
    private String id;
    private String name;

    public Student(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Student)) {
            return false;
        }
        if (TextUtils.isEmpty(((Student) obj).getId())) {
            return false;
        }
        return this.id.equals(((Student) obj).getId());
    }
}
