package com.collegienproject.rank4.managecalories.dao;

/**
 * Created by JirayuPC on 06 พ.ค. 2559.
 */
public class ActivityDao {
    private int Activity_id;
    private String Activity_name;
    private float Activity_met;
    private int Image_id;

    public ActivityDao(int imageId) {
        this.Image_id = imageId;
    }

    public float getActivity_met() {
        return Activity_met;
    }

    public void setActivity_met(float activity_met) {
        Activity_met = activity_met;
    }


    public ActivityDao(){

    }

    public int getImage_id() {
        return Image_id;
    }

    public void setImage_id(int image_id) {
        Image_id = image_id;
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
