package com.collegienproject.rank4.managecalories.dao;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by JirayuPC on 08 เม.ย. 2559.
 */
public class ProgramDao implements Parcelable {
    private int Program_id;
    private String Program_name;
    private Date Start_date;
    private Date End_date;
    private int Week_num;
    private int Goal;

    public ProgramDao(){

    }

    public ProgramDao(int program_id, String program_name, Date start_date, Date end_date, int week_num, int goal) {
        Program_id = program_id;
        Program_name = program_name;
        Start_date = start_date;
        End_date = end_date;
        Week_num = week_num;
        Goal = goal;
    }

    protected ProgramDao(Parcel in) {
        Program_id = in.readInt();
        Program_name = in.readString();
        Week_num = in.readInt();
        Goal = in.readInt();
    }

    public static final Creator<ProgramDao> CREATOR = new Creator<ProgramDao>() {
        @Override
        public ProgramDao createFromParcel(Parcel in) {
            return new ProgramDao(in);
        }

        @Override
        public ProgramDao[] newArray(int size) {
            return new ProgramDao[size];
        }
    };

    public Date getStart_date() {
        return Start_date;
    }

    public void setStart_date(Date start_date) {
        Start_date = start_date;
    }

    public Date getEnd_date() {
        return End_date;
    }

    public void setEnd_date(Date end_date) {
        End_date = end_date;
    }

    public int getWeek_num() {
        return Week_num;
    }

    public void setWeek_num(int week_num) {
        Week_num = week_num;
    }

    public int getGoal() {
        return Goal;
    }

    public void setGoal(int goal) {
        Goal = goal;
    }

    public int getProgram_id() {
        return Program_id;
    }

    public void setProgram_id(int program_id) {
        Program_id = program_id;
    }

    public String getProgram_name() {
        return Program_name;
    }

    public void setProgram_name(String program_name) {
        Program_name = program_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Program_id);
        dest.writeString(Program_name);
        dest.writeInt(Week_num);
        dest.writeInt(Goal);
    }


}


