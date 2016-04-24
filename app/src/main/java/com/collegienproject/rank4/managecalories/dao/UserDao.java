package com.collegienproject.rank4.managecalories.dao;

import java.util.Date;

/**
 * Created by JirayuPC on 3/22/2016.
 */
public class UserDao {
    private String User_email;
    private String User_password;
    private String User_name;
    private String User_sex;
    private float User_weight;
    private float User_height;
    private Date User_birthdate;


    public UserDao(Date user_birthdate, String user_email, String user_password, String user_name, String user_sex, float user_weight, float user_height) {
        User_birthdate = user_birthdate;
        User_email = user_email;
        User_password = user_password;
        User_name = user_name;
        User_sex = user_sex;
        User_weight = user_weight;
        User_height = user_height;
    }

    public UserDao() {

    }


    public String getUser_email() {
        return User_email;
    }

    public void setUser_email(String user_email) {
        User_email = user_email;
    }

    public String getUser_password() {
        return User_password;
    }

    public void setUser_password(String user_password) {
        User_password = user_password;
    }

    public String getUser_name() {
        return User_name;
    }

    public void setUser_name(String user_name) {
        User_name = user_name;
    }

    public float getUser_weight() {
        return User_weight;
    }

    public void setUser_weight(float user_weight) {
        User_weight = user_weight;
    }

    public float getUser_height() {
        return User_height;
    }

    public void setUser_height(float user_height) {
        User_height = user_height;
    }

    public Date getUser_birthdate() {
        return User_birthdate;
    }

    public void setUser_birthdate(Date user_birthdate) {
        User_birthdate = user_birthdate;
    }

    public String getUser_sex() {
        return User_sex;
    }

    public void setUser_sex(String user_sex) {
        User_sex = user_sex;
    }
}


