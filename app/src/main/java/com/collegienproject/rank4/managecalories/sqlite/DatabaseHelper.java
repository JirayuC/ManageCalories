package com.collegienproject.rank4.managecalories.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.collegienproject.rank4.managecalories.dao.DateDao;
import com.collegienproject.rank4.managecalories.dao.ProgramDao;
import com.collegienproject.rank4.managecalories.dao.UserDao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    private static  final String TABLE_PROGRAM = "UserProgram";
    public static final String COLUMN_PROGRAMID = "Program_id";
    public static final String COLUMN_PROGRAMNAME = "Program_name";
    public static final String COLUMN_STARTDATE = "Start_date";
    public static final String COLUMN_ENDDATE = "End_date";
    public static final String COLUMN_WEEKNUM = "Week_num";
    public static final String COLUMN_GOAL = "Goal";


    private static  final String TABLE_DATEFORPROGRAM = "DateForProgram";

    private static  final String TABLE_DATESET = "DateSet";
    public static final String COLUMN_DATEID = "Date_id";
    public static final String COLUMN_DATETIME = "Date_time";

    public static final String COLUMN_DATEDFPID = "DateDFP_id";
    public static final String COLUMN_PROGRAMDFPID = "ProgramDFP_id";


    private static  final String TABLE_DATEFORACTIVITY = "DateForActivity";
    public static final String COLUMN_DATEIDGROUP = "Date_id";
    public static final String COLUMN_ACTIVITYIDGROUP = "Activity_id";
    public static final String COLUMN_CALORIESREAL = "CaloriesReal";

    private static  final String TABLE_ACTIVITY = "Activity";
    public static final String COLUMN_ACTIVITYID = "Activity_id";
    public static final String COLUMN_ACTIVITYNAME = "Activity_name";


    private static final String CREATE_USER_TABLE =
            "CREATE TABLE UserProfile(" +
                    "User_email TEXT PRIMARY KEY NOT NULL," +
                    "User_password TEXT NOT NULL," +
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
            "CREATE TABLE DateSet(" +
                    "Date_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Date_time DATE);";

    private static final  String CREATE_DATEFORPROGRAM_TABLE =
            "CREATE TABLE DateForProgram(" +
                    "DFPid INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "DateDFP_id INTEGER, " +
                    // "FOREIGN KEY(DateDFP_id)REFERENCES DateSet(Date_id)," +
                    "ProgramDFP_id INTEGER);";
    // "FOREIGN KEY(ProgramDFP_id)REFERENCES UserProgram(Program_id));";

    private static final  String CREATE_DATEFORACTIVITY_TABLE =
            "CREATE TABLE DateForActivity(" +
                    "DFA_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "DateDFA_id INTEGER, " +
                    "FOREIGN KEY(DateDFA_id)REFERENCES DateSet(Date_id)," +
                    "ActivityDFA_id INTEGER, " +
                    "FOREIGN KEY(ActivityDFA_id)REFERENCES DateSet(Activity_id));";

    private static final  String CREATE_ACTIVITY_TABLE =
            "CREATE TABLE Activity(" +
                    "Activity_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Activity_name TEXT);";


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
            Log.d("Database operations","Table created...");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATESET);
        onCreate(db);
    }


    public void addUser(UserDao user){

        Log.d("addUser", user.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = new SimpleDateFormat("dd MMMM yyyy");
        try{
            ContentValues values = new ContentValues();
            values.put(COLUMN_USERNAME, user.getUser_name());
            values.put(COLUMN_USEREMAIL, user.getUser_email());
            values.put(COLUMN_USERPASSWORD, user.getUser_password());
            values.put(COLUMN_BIRTHDATE, df.format(user.getUser_birthdate()));
            values.put(COLUMN_USERWEIGHT, user.getUser_weight());
            values.put(COLUMN_USERHEIGHT, user.getUser_height());
            values.put(COLUMN_SEX, user.getUser_sex());

            db.insert(TABLE_USERS, null, values);
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public int addProgram(ProgramDao prg , int[] date_pri){
        int program_pri = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PROGRAMNAME,prg.getProgram_name());
            cv.put(COLUMN_STARTDATE, df.format(prg.getStart_date()));
            cv.put(COLUMN_GOAL,prg.getGoal());
            cv.put(COLUMN_WEEKNUM,prg.getWeek_num());

            program_pri = (int) db.insert(TABLE_PROGRAM, COLUMN_PROGRAMID,cv);
            for(int i : date_pri){
                createDatePrg(program_pri,i);
            }

            Log.d("Database operation", "One Row Inserted...");


        }catch (SQLException e){
            e.printStackTrace();
        }
        return program_pri;
    }



    public List<ProgramDao> getProgramList() {

        //Open connection to read only
        List<ProgramDao> listPrg = new ArrayList<ProgramDao>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                COLUMN_PROGRAMNAME + "," +
                COLUMN_STARTDATE +
                " FROM " + TABLE_PROGRAM ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        if (cursor!=null && cursor.moveToFirst()) {
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

    public int addDate(DateDao date){

        int date_pri = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        try {

            ContentValues dt = new ContentValues();
            dt.put(COLUMN_DATETIME, df.format(date.getDatetime()));

            date_pri =  (int) db.insert(TABLE_DATESET, COLUMN_DATEID , dt);
            //Log.d("ADebugTag", "Vzxczxcxzczxczxczxczxczxczxczxczxczxczxczxczxczxczxczxczxczxcalue: " + Float.toString(x));

            Log.d("Database operation", "One Row Inserted...");


        }catch (SQLException e){
            e.printStackTrace();
        }
        return date_pri ;

    }

    public int createDatePrg(long Program_id, long date_id ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATEDFPID,date_id);
        values.put(COLUMN_PROGRAMDFPID,Program_id);
        int id = (int)db.insert(TABLE_DATEFORPROGRAM,null,values);

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

        String selectQuery =  "SELECT  " +
                COLUMN_DATEID + "," +
                COLUMN_DATETIME +
                " FROM " + TABLE_DATESET ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        if (cursor!=null && cursor.moveToFirst()) {
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
}
