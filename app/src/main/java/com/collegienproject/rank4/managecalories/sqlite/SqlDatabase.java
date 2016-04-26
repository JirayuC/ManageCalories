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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by JirayuPC on 06 เม.ย. 2559.
 */
public class SqlDatabase {

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


    private static  final String TABLE_DATESET = "DateSet";
    public static final String COLUMN_DATEID = "Date_id";
    public static final String COLUMN_DATETIME = "Date_time";
    public static final String COLUMN_DATEGROUP = "Date_GROUP";

    private static  final String TABLE_DATEFORACTIVITY = "DateForActivity";
    public static final String COLUMN_DATEIDGROUP = "Date_id";
    public static final String COLUMN_ACTIVITYIDGROUP = "Activity_id";
    public static final String COLUMN_CALORIESREAL = "CaloriesReal";

    private static  final String TABLE_ACTIVITY = "Activity";
    public static final String COLUMN_ACTIVITYID = "Activity_id";
    public static final String COLUMN_ACTIVITYNAME = "Activity_name";

    public static final String COLUMNS[] = {COLUMN_USEREMAIL, COLUMN_USERPASSWORD};

    public static final int NAME_COLUMN = 1;

    private DbHelper mHelper;

    private final Context mContext;

    private SQLiteDatabase mDatabase;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
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
                    "Goal INTEGER," +
                    "Set_id INTEGER, " +
                    "FOREIGN KEY(Set_id)REFERENCES DateSet(Date_id) );";

    private static final  String CREATE_DATESET_TABLE =
            "CREATE TABLE DateSet(" +
                    "Date_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "Date_time DATE," +
                    "Date_GROUP INTEGER);";

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


    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        //Set up database here
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_USER_TABLE);
                db.execSQL(CREATE_PROGRAM_TABLE);
                db.execSQL(CREATE_DATESET_TABLE);
                db.execSQL(CREATE_DATEFORACTIVITY_TABLE);
                db.execSQL(CREATE_ACTIVITY_TABLE);
                Log.d("Database operations","Table created...");
            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROGRAM);
            onCreate(db);
}

    }


    public SqlDatabase(Context c) {
        mContext = c;
    }

    public SqlDatabase open() throws SQLException {
        //Set up the helper with the context
        mHelper = new DbHelper(mContext);
        //Open the database with our helper
        try
        {
            mDatabase = mHelper.getWritableDatabase();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return this;
    }

    public SqlDatabase close() {
        try
        {
            mHelper.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

       return this;
    }


    public void addUser(UserDao user){

        Log.d("addUser", user.toString());

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

            mDatabase.insert(TABLE_USERS, null, values);
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    public void addProgram(ProgramDao prg){

        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PROGRAMNAME,prg.getProgram_name());
            cv.put(COLUMN_STARTDATE, df.format(prg.getStart_date()));
            cv.put(COLUMN_GOAL,prg.getGoal());
            cv.put(COLUMN_WEEKNUM,prg.getWeek_num());

            mDatabase.insert(TABLE_PROGRAM, COLUMN_PROGRAMID ,cv);

            Log.d("Database operation", "One Row Inserted...");

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public Cursor getAllProgram()
    {
        String[] columns={COLUMN_PROGRAMID,COLUMN_PROGRAMNAME};

        return mDatabase.query(TABLE_PROGRAM,columns,null,null,null,null,null);
    }

    public void deleteProgram(ProgramDao pg){

        try {
            mDatabase.delete(TABLE_PROGRAM, COLUMN_PROGRAMID+" =?", new String[]{String.valueOf(pg.getProgram_id())});

            mDatabase.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        Log.d("deleteUser", pg.toString());
    }

    public ArrayList<ProgramDao>  getProgramList() {
        //Open connection to read only
        ArrayList<ProgramDao> listPrg = new ArrayList<ProgramDao>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                COLUMN_PROGRAMID + "," +
                COLUMN_PROGRAMNAME +
                " FROM " + TABLE_PROGRAM ;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor!=null && cursor.moveToFirst()) {
            do {
                ProgramDao prg = new ProgramDao();
                prg.setProgram_id(cursor.getInt(cursor.getColumnIndex(COLUMN_PROGRAMID)));
                prg.setProgram_name(cursor.getString(cursor.getColumnIndex(COLUMN_PROGRAMNAME)));
                listPrg.add(prg);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listPrg;

    }

    public void addDate(DateDao date){

        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.US);
        try {
            ContentValues dt = new ContentValues();
            dt.put(COLUMN_DATETIME, df.format(date.getDatetime()));
            dt.put(COLUMN_DATEGROUP, date.getDate_group());

            mDatabase.insert(TABLE_DATESET, COLUMN_DATEID ,dt);

            Log.d("Database operation", "One Row Inserted...");

        }catch (SQLException e){
            e.printStackTrace();
        }

    }



}
