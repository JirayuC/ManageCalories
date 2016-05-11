package com.collegienproject.rank4.managecalories.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.collegienproject.rank4.managecalories.R;
import com.collegienproject.rank4.managecalories.dao.ActivityDao;
import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.dao.DateForActivity;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.dao.UserDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by JirayuPC on 30 เม.ย. 2559.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Calories.db";


    private static final String TABLE_USERS = "UserProfile";
    public static final String COLUMN_USEREMAIL = "User_email";
    public static final String COLUMN_USERPASSWORD = "User_password";
    public static final String COLUMN_USERNAME = "User_name";
    public static final String COLUMN_USERWEIGHT = "User_weight";
    public static final String COLUMN_USERHEIGHT = "User_height";
    public static final String COLUMN_BIRTHDATE = "User_birthdate";
    public static final String COLUMN_SEX = "User_sex";

    private static final String TABLE_PROGRAM = "UserProgram";
    public static final String COLUMN_PROGRAMID = "Program_id";
    public static final String COLUMN_PROGRAMNAME = "Program_name";
    public static final String COLUMN_STARTDATE = "Start_date";
    public static final String COLUMN_ENDDATE = "End_date";
    public static final String COLUMN_WEEKNUM = "Week_num";
    public static final String COLUMN_GOAL = "Goal";


    private static final String TABLE_DATEFORPROGRAM = "DateForProgram";

    private static final String TABLE_DATESET = "DateSet";
    public static final String COLUMN_DATEID = "Date_id";
    public static final String COLUMN_DATETIME = "Date_time";

    public static final String COLUMN_DATEDFPID = "DateDFP_id";
    public static final String COLUMN_PROGRAMDFPID = "ProgramDFP_id";


    private static final String TABLE_DATEFORACTIVITY = "DateForActivity";
    public static final String COLUMN_DATEIDGROUP = "Date_id";
    public static final String COLUMN_ACTIVITYIDGROUP = "Activity_id";
    public static final String COLUMN_CALORIESREAL = "CaloriesReal";

    private static final String TABLE_ACTIVITY = "Activity";
    public static final String COLUMN_ACTIVITYID = "Activity_id";
    public static final String COLUMN_ACTIVITYNAME = "Activity_name";
    public static final String COLUMN_ACTIVITYMET = "Activity_met";
    public static final String COLUMN_ACTIVITYIMAGE = "Activity_image";


    private static final String CREATE_USER_TABLE =
            "CREATE TABLE UserProfile(" +
                    "User_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "User_email TEXT," +
                    "User_sex TEXT NOT NULL, " +
                    "User_name TEXT NOT NULL, " +
                    "User_weight REAL NOT NULL, " +
                    "User_height REAL NOT NULL, " +
                    "User_birthdate DATE NOT NULL);";

    private static final String CREATE_PROGRAM_TABLE =
            "CREATE TABLE UserProgram(" +
                    "Program_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Program_name TEXT NOT NULL, " +
                    "Start_date DATE NOT NULL, " +
                    "Week_num INTEGER, " +
                    "Goal INTEGER);";

    private static final String CREATE_DATESET_TABLE =
            "CREATE TABLE " + TABLE_DATESET + " (" +
                    "Date_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Date_time DATE," +
                    "Program_id INTEGER);";

    private static final String CREATE_DATEFORPROGRAM_TABLE =
            "CREATE TABLE DateForProgram(" +
                    "DFPid INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "DateDFP_id INTEGER, " +
                    // "FOREIGN KEY(DateDFP_id)REFERENCES DateSet(Date_id)," +
                    "ProgramDFP_id INTEGER);";
    // "FOREIGN KEY(ProgramDFP_id)REFERENCES UserProgram(Program_id));";

    private static final String CREATE_DATEFORACTIVITY_TABLE =
            "CREATE TABLE " + TABLE_DATEFORACTIVITY + " (" +
                    "DFA_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Date_id INTEGER, " +
                    "Activity_id INTEGER," +
                    "CaloriesReal FLOAT," +
                    "Status INTEGER);";

    private static final String CREATE_ACTIVITY_TABLE =
            "CREATE TABLE " + TABLE_ACTIVITY + " (" +
                    "Activity_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Activity_name TEXT," +
                    "Activity_met FLOAT);";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_USER_TABLE);
            db.execSQL(CREATE_PROGRAM_TABLE);
            db.execSQL(CREATE_DATESET_TABLE);
            db.execSQL(CREATE_DATEFORPROGRAM_TABLE);
            db.execSQL(CREATE_ACTIVITY_TABLE);
            db.execSQL(CREATE_DATEFORACTIVITY_TABLE);

            initActivity(db);

            Log.d("Database operations", "Table created...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    int[] imageId = {
            R.drawable.ic_aerobic, R.drawable.ic_badminton2, R.drawable.ic_badminton,
            R.drawable.ic_baseball, R.drawable.ic_basketball, R.drawable.ic_biker,
            R.drawable.ic_bowling, R.drawable.ic_boxer, R.drawable.ic_canoe,
            R.drawable.ic_cross_country_skiing, R.drawable.ic_man_in_hike, R.drawable.ic_fencing,
            R.drawable.ic_ice_skating, R.drawable.ic_american_football, R.drawable.ic_golf,
            R.drawable.ic_gymnast, R.drawable.ic_handball, R.drawable.ic_home_calisthenics,
            R.drawable.ic_rugby, R.drawable.ic_football, R.drawable.ic_softball,
            R.drawable.ic_swimming, R.drawable.ic_pingpong, R.drawable.ic_tennis_singer,
            R.drawable.ic_tennis, R.drawable.ic_marathon, R.drawable.high_jump,
            R.drawable.long_jumping, R.drawable.ic_volleyball, R.drawable.ic_walking,
            R.drawable.ic_weight_tainning, R.drawable.ic_yoga, R.drawable.ic_running
    };

    float[] met = {
            3.9f, 3.5f, 4.5f,
            5.5f, 6f, 5.2f,
            3f, 6f, 7f,
            5f, 7f, 8f,
            4f, 6.5f, 5.1f,
            7f, 4f, 4f,
            6f, 5f, 4.5f,
            4.5f, 4.7f, 6.5f,
            4.5f, 4.5f, 4.1f,
            2f, 6f, 3.4f,
            3f, 3.2f, 2.5f

    };

    String[] array_actiivty_name = {
            "Aerobic dancing", "Badminton Doubles", "Badminton Singles",
            "Baseball", "Basketball", "Bicycling",
            "Bowling", "Boxing", "Canoeing",
            "Cross-country skiing", "Equestrianism", "Fencing",
            "Figure skating", "American football", "Golf",
            "Gymnastics", "Handball", "Home calisthenics",
            "Rugby", "football", "Softball",
            "Swimming", "Table tennis", "Tennis Singles",
            "Tennis Doubles", "Marathon", "High jump",
            "Long jump", "Volleyball", "Walking for exercise",
            "Weight training", "Yoga", "Running"
    };

    private void initActivity(SQLiteDatabase db) {

        for (int i = 0 ; i < array_actiivty_name.length; i++) {
            ActivityDao activityDao = new ActivityDao();
            activityDao.setActivity_id(i + 1);
            //activityDao.setImage_id(imageId[i]);
            activityDao.setActivity_name(array_actiivty_name[i]);
            activityDao.setActivity_met(met[i]);
            addActivity(db, activityDao);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATESET);
        onCreate(db);
    }


    public void addUser(UserDao user) {

        Log.d("addUser", user.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, user.getUser_name());
            values.put(COLUMN_USEREMAIL, user.getUser_email());
            values.put(COLUMN_BIRTHDATE, df.format(user.getUser_birthdate()));
            values.put(COLUMN_USERWEIGHT, user.getUser_weight());
            values.put(COLUMN_USERHEIGHT, user.getUser_height());
            values.put(COLUMN_SEX, user.getUser_sex());

            db.insert(TABLE_USERS, null, values);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public int updateUser(UserDao user) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUser_name());
        values.put(COLUMN_USEREMAIL, user.getUser_email());
        values.put(COLUMN_BIRTHDATE, df.format(user.getUser_birthdate()));
        values.put(COLUMN_USERWEIGHT, user.getUser_weight());
        values.put(COLUMN_USERHEIGHT, user.getUser_height());
        values.put(COLUMN_SEX, user.getUser_sex());

        return db.update(TABLE_USERS, values, "User_id" + " = ?",
                new String[]{String.valueOf("1")});
}

    public int addProgram(ProgramDao prg, int[] date_pri) {
        int program_pri = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PROGRAMNAME, prg.getProgram_name());
            cv.put(COLUMN_STARTDATE, df.format(prg.getStart_date()));
            cv.put(COLUMN_GOAL, prg.getGoal());
            cv.put(COLUMN_WEEKNUM, prg.getWeek_num());

            program_pri = (int) db.insert(TABLE_PROGRAM, COLUMN_PROGRAMID, cv);
            for (int i : date_pri) {
                createDatePrg(program_pri, i);
            }

            Log.d("Database operation", "One Row Inserted...");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return program_pri;
    }


    public List<ActivityDao> getActPickerList() {

        //Open connection to read only
        List<ActivityDao> listAct = new ArrayList<ActivityDao>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  " +
                /*COLUMN_ACTIVITYIMAGE + "," +*/
                COLUMN_ACTIVITYNAME  +
                " FROM " + TABLE_ACTIVITY;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ActivityDao prg = new ActivityDao();
                prg.setActivity_name(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVITYNAME)));
                //prg.setImage_id(cursor.getInt(cursor.getColumnIndex(COLUMN_ACTIVITYIMAGE)));
                listAct.add(prg);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listAct;

    }


    public List<ProgramDao> getProgramList() {

        //Open connection to read only
        List<ProgramDao> listPrg = new ArrayList<ProgramDao>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  " +
                COLUMN_PROGRAMNAME + "," +
                COLUMN_STARTDATE +
                " FROM " + TABLE_PROGRAM;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ProgramDao prg = new ProgramDao();
                prg.setProgram_name(cursor.getString(cursor.getColumnIndex(COLUMN_PROGRAMNAME)));
                Date date = null;
                try {
                    date = df.parse(cursor.getString(cursor.getColumnIndex(COLUMN_STARTDATE)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                prg.setStart_date(date);
                listPrg.add(prg);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listPrg;

    }

    public List<DateDao> getListOfProgramDAO(int index) {
        List<DateDao> listDate = new ArrayList<DateDao>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_DATEFORPROGRAM +
                " left join " + TABLE_DATESET +
                " on " + COLUMN_DATEDFPID + "=" + COLUMN_DATEID +
                " WHERE " + COLUMN_PROGRAMDFPID +
                "=?";
        String[] arg = {Float.toString(index)};
        Log.e("-*----------*------->", selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, arg);
        // looping through all rows and adding to list
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DateDao dat = new DateDao();
                dat.setDate_id(cursor.getInt(cursor.getColumnIndex(COLUMN_DATEID)));
                Date date = null;
                try {
                    date = df.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dat.setDatetime(date);
                listDate.add(dat);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listDate;
    }

    public ProgramDao ModelProgram(int position) {

        position = position + 1;

        //Open connection to read only
        // List<ProgramDao> listPrg = new ArrayList<ProgramDao>();
        ProgramDao model = new ProgramDao();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * " +
                " FROM " + TABLE_PROGRAM +
                " where " + COLUMN_PROGRAMID + "=?";

        String[] args = {Float.toString(position)};
        Cursor cursor = db.rawQuery(selectQuery, args);
        // looping through all rows and adding to list
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                model.setProgram_name(cursor.getString(cursor.getColumnIndex(COLUMN_PROGRAMNAME)));
                model.setProgram_id(cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRAMID)));


                Date date = null;
                try {
                    date = df.parse(cursor.getString(cursor.getColumnIndex(COLUMN_STARTDATE)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                model.setStart_date(date);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return model;

    }


    public int addDate(DateDao date, int Program_id) {

        int date_pri = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {

            ContentValues dt = new ContentValues();
            dt.put(COLUMN_DATETIME, df.format(date.getDatetime()));
            dt.put(COLUMN_PROGRAMID, Program_id);

            date_pri = (int) db.insert(TABLE_DATESET, COLUMN_DATEID, dt);
            //Log.d("ADebugTag", "Vzxczxcxzczxczxczxczxczxczxczxczxczxczxczxczxczxczxczxczxczxcalue: " + Float.toString(x));

            Log.d("Database operation", "One Row Inserted...");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date_pri;
    }

    public int createDatePrg(long Program_id, long date_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATEDFPID, date_id);
        values.put(COLUMN_PROGRAMDFPID, Program_id);
        int id = (int) db.insert(TABLE_DATEFORPROGRAM, null, values);

        return id;

    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public List<DateDao> getDateList() {

        //Open connection to read only
        List<DateDao> listDate = new ArrayList<DateDao>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  " +
                COLUMN_DATEID + "," +
                COLUMN_DATETIME +
                " FROM " + TABLE_DATESET;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DateDao dat = new DateDao();
                dat.setDate_id(cursor.getInt(cursor.getColumnIndex(COLUMN_DATEID)));
                Date date = null;
                try {
                    date = df.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dat.setDatetime(date);
                listDate.add(dat);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listDate;

    }


    public int addActivity(SQLiteDatabase db, ActivityDao act) {
        int activity_pri = -1;

        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ACTIVITYNAME, act.getActivity_name());
            cv.put(COLUMN_ACTIVITYMET, act.getActivity_met());
            //cv.put(COLUMN_ACTIVITYIMAGE, act.getImage_id());
            activity_pri = (int) db.insert(TABLE_ACTIVITY, COLUMN_ACTIVITYID, cv);

            Log.d("Database operation", "One Row Inserted...");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity_pri;
    }

    private int createDateAct(long Activity_id, long date_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATEIDGROUP, date_id);
        values.put(COLUMN_ACTIVITYIDGROUP, Activity_id);
        int id = (int) db.insert(TABLE_DATEFORACTIVITY, null, values);

        return id;

    }

    public void addDateForActivity(int date_id, int activity_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("Date_id", date_id);
            cv.put("Activity_id", activity_id);

            cv.put("CaloriesReal", 0.0f);
            cv.put("Status", 0);

            db.insert("DateForActivity", "DFA_id", cv);

            Log.d("Database operation", "One Row Inserted...");


        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public List<DateForActivity> getDateForActivity(int Date_id) {

        //Open connection to read only
        List<DateForActivity> listPrg = new ArrayList<DateForActivity>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT DFA.DFA_id , DFA.Status , A.* , D.*" +
                " FROM " + TABLE_DATEFORACTIVITY + " DFA , " + TABLE_ACTIVITY + " A , " + TABLE_DATESET + " D " +
                " WHERE DFA.Date_id = D.Date_id AND DFA.Activity_id = A.Activity_id AND DFA.Date_id = " + Date_id +
                " ";

        Log.d("Biw", "selectQuery = " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to lis
        if (cursor != null && cursor.moveToFirst()) {
            do {
                DateForActivity prg = new DateForActivity();
                prg.setDFA_id(cursor.getInt(cursor.getColumnIndex("DFA_id")));
                prg.setActivity_id(cursor.getInt(cursor.getColumnIndex("Activity_id")));
                prg.setDate_id(cursor.getInt(cursor.getColumnIndex("Date_id")));
                prg.setActivity_name(cursor.getString(cursor.getColumnIndex("Activity_name")));
                prg.setDate_time(cursor.getString(cursor.getColumnIndex("Date_time")));
                prg.setMet(cursor.getFloat(cursor.getColumnIndex("Activity_met")));
                prg.setStatus(cursor.getInt(cursor.getColumnIndex("Status")));

                Log.d("Biw", "list Added. ");
                listPrg.add(prg);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listPrg;
    }

    public UserDao getUser() {

        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        Log.d("Biw", "selectQuery = " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        UserDao userDao = new UserDao();

        if (cursor != null && cursor.moveToFirst()) {
            do {

                // set birth date.
                String birth = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDATE));
                Log.d("Biw", "Birth = " + birth);
                try {
                    Date date = df.parse(birth);
                    userDao.setUser_birthdate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                userDao.setUser_height(cursor.getFloat(cursor.getColumnIndex(COLUMN_USERHEIGHT)));
                userDao.setUser_weight(cursor.getFloat(cursor.getColumnIndex(COLUMN_USERWEIGHT)));
                userDao.setUser_sex(cursor.getString(cursor.getColumnIndex(COLUMN_SEX)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return userDao;
    }

    public int activitySuccess(int DFA_id, float cal) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CaloriesReal", cal);
        values.put("Status", 1);

        return db.update(TABLE_DATEFORACTIVITY, values, "DFA_id=" + DFA_id, null);
    }

    public int getDateForActivityID(Date date, int Program_id) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String my_date = df.format(date);

        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT Date_id " +
                " FROM " + TABLE_DATESET +
                " WHERE Date_time = '" + my_date + "' AND Program_id = " + Program_id +
                " ";

        Log.d("Biw", "selectQuery = " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        int date_id = 0;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                date_id = cursor.getInt(cursor.getColumnIndex("Date_id"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return date_id;
    }

    public int getMaxProgramId() {


        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT MAX(" + COLUMN_PROGRAMID + ") AS max" +
                " FROM " + TABLE_PROGRAM;

        Log.d("Biw", "selectQuery = " + selectQuery);

        Cursor cursor = db.rawQuery(selectQuery, null);

        int max = 1;

        if (cursor != null && cursor.moveToFirst()) {
            do {
                max = cursor.getInt(cursor.getColumnIndex("max"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return max;
    }

    public float getStatisticCal() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT Goal - (SELECT SUM(CaloriesReal) FROM " + TABLE_DATEFORACTIVITY + ") AS cal  FROM " + TABLE_PROGRAM + "";

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        float cal = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cal = cursor.getFloat(cursor.getColumnIndex("cal"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cal;
    }

    public float getAVGGoal(SQLiteDatabase db, int Program_id) {
        //Open connection to read only

        String selectQuery = "SELECT Goal / (SELECT COUNT(*) FROM  " + TABLE_DATESET + " D WHERE D.Program_id = P.Program_id ) AS avg_goal FROM " + TABLE_PROGRAM +
                " P WHERE Program_id = " + Program_id;

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        float cal = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cal = cursor.getFloat(cursor.getColumnIndex("avg_goal"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return cal;
    }

    public int getCountSuccessDate(int Program_id) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        float avg_goal = getAVGGoal(db, Program_id);
        Log.d("Biw", "AVG GOAL = " + avg_goal);
        int count_success = 0;
        String selectQuery = "SELECT SUM(CaloriesReal) AS cal FROM " + TABLE_DATEFORACTIVITY + " GROUP BY Date_id";

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        float cal = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cal = cursor.getFloat(cursor.getColumnIndex("cal"));
                if (cal >= avg_goal) {
                    count_success++;
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count_success;
    }

    public int getCountDate(int Program_id) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        int count = 0;
        String selectQuery = "SELECT COUNT(*) as count FROM " + TABLE_DATESET + " WHERE Program_id = " + Program_id;

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                count = cursor.getInt(cursor.getColumnIndex("count"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count;
    }

    public String getDateCurrent() {
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        return format1.format(cal.getTime());
    }


    public int getCountMissDate(int Program_id) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String date_today = getDateCurrent();

        int count_miss = 0;

        // find date_id for today.
        String selectQuery = "SELECT Date_id FROM " + TABLE_DATESET + " WHERE Program_id = " + Program_id +" AND Date_time = '"+date_today+"'";

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        int date_id = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                date_id = cursor.getInt(cursor.getColumnIndex("Date_id"));
            } while (cursor.moveToNext());
        }


        Log.d("Biw", "today date_id = " + date_id);
        // find date less than date_id in dataset.
        selectQuery = "SELECT Date_id FROM " + TABLE_DATESET + " WHERE Program_id = " + Program_id +" AND Date_id <= "+date_id;

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor2 = db.rawQuery(selectQuery, null);


        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
               int date_id_2 = cursor2.getInt(cursor2.getColumnIndex("Date_id"));

                //
                selectQuery = "SELECT IFNULL(SUM(CaloriesReal),0) As cal FROM " + TABLE_DATEFORACTIVITY + " WHERE Date_id = " + date_id_2;

                Log.d("Biw", "selectQuery = " + selectQuery);
                Cursor cursor3 = db.rawQuery(selectQuery, null);

                if (cursor3 != null && cursor3.moveToFirst()) {
                    do {
                        float cal = cursor3.getInt(cursor3.getColumnIndex("cal"));
                        if(cal <= 0){
                            count_miss++;
                        }
                    } while (cursor3.moveToNext());
                }

            } while (cursor2.moveToNext());
        }

        // cal


        cursor.close();
        db.close();
        return count_miss;
    }

    public float getGoalCal(int Program_id) {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT Goal FROM " + TABLE_PROGRAM + " WHERE Program_id = "+Program_id;

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        float cal = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cal = cursor.getFloat(cursor.getColumnIndex("Goal"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cal;
    }

    public boolean isAlreadyUser() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT COUNT(*) as count FROM "+TABLE_USERS;

        Log.d("Biw", "selectQuery = " + selectQuery);
        Cursor cursor = db.rawQuery(selectQuery, null);

        float cal = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                cal = cursor.getFloat(cursor.getColumnIndex("count"));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cal > 0;
    }

}
