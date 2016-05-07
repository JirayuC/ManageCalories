package com.collegienproject.rank4.managecalories.dao;

/**
 * Created by JirayuPC on 06 พ.ค. 2559.
 */
public class ActivityDao {
    private int Activity_id;
    private String Activity_name;

    public ActivityDao(){

    }

    public int getActivity_id() {
        return Activity_id;
    }

    public void setActivity_id(int activity_id) {
        Activity_id = activity_id;
    }

    public String getActivity_name() {
        return Activity_name;
    }

    public void setActivity_name(String activity_name) {
        Activity_name = activity_name;
    }
}
