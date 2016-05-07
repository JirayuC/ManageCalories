package com.collegienproject.rank4.managecalories.dao;

/**
 * Created by benznest on 7/5/2559.
 */
public class DateForActivity {


    public int DFA_id;
    public int Date_id;
    public int Activity_id;
    public String Date_time;
    public String Activity_name;
    public float met;
    public int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public float getMet() {
        return met;
    }

    public void setMet(float met) {
        this.met = met;
    }


    public int getDFA_id() {
        return DFA_id;
    }

    public void setDFA_id(int DFA_id) {
        this.DFA_id = DFA_id;
    }

    public int getDate_id() {
        return Date_id;
    }

    public void setDate_id(int date_id) {
        Date_id = date_id;
    }

    public int getActivity_id() {
        return Activity_id;
    }

    public void setActivity_id(int activity_id) {
        Activity_id = activity_id;
    }

    public String getDate_time() {
        return Date_time;
    }

    public void setDate_time(String date_time) {
        Date_time = date_time;
    }

    public String getActivity_name() {
        return Activity_name;
    }

    public void setActivity_name(String activity_name) {
        Activity_name = activity_name;
    }
}
