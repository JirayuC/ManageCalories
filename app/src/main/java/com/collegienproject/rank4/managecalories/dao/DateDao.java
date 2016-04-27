package com.collegienproject.rank4.managecalories.dao;

import java.util.Date;

/**
 * Created by JirayuPC on 26 เม.ย. 2559.
 */
public class DateDao{
    private int Date_id;
    private Date datetime;
    private int date_group;

    public DateDao(){

    }

    public int getDate_id() {
        return Date_id;
    }

    public void setDate_id(int date_id) {
        Date_id = date_id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getDate_group() {
        return date_group;
    }

    public void setDate_group(int date_group) {
        this.date_group = date_group;
    }
}
