package com.phz.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author haizhuo
 * @introduction 用户
 */
public class User implements Parcelable {
    public User(String name, String phone, String pwd) {
        this.name = name;
        this.phone = phone;
        this.pwd = pwd;
    }

    private String name;
    private String phone;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.pwd);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.pwd = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
