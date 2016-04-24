package com.collegienproject.rank4.managecalories.sqlite;

/**
 * Created by JirayuPC on 3/21/2016.
 */

/*public class DatabaseHelper extends SQLiteOpenHelper {



    public DatabaseHelper(Context context) {

    }


    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +oldVersion + " to " +newVersion + ", which will destroy all old data");


        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
        // Create a new one.
        onCreate(db);
    }


    public UserDao getUser(String User_email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_USERS,
                        COLUMNS,
                        "User_email =?",
                        new String[] {String.valueOf(User_email)},
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        UserDao user = new UserDao();
        user.setUser_email(cursor.getString(0));
        user.setUser_password(cursor.getString(1));

        Log.d("getUser ("+User_email+")", user.toString());

        return user;
    }

    public List<UserDao> getAllUsers(){
        List<UserDao> users = new LinkedList<UserDao>();

        String query="SELECT * FROM "+TABLE_USERS;

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);

        UserDao user = null;
        if (cursor.moveToFirst()){
            do{
                user = new UserDao();
                user.setFirstName(cursor.getString(0));
                user.setLastName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setUsername(cursor.getString(3));
                user.setPassword(cursor.getString(4));

                users.add(user);
            } while (cursor.moveToNext());
        }

        Log.d("getAllUsers", users.toString());

        return users;
    }

    public int updateUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(FIRST_NAME, user.getFirstName());
        values.put(LAST_NAME, user.getLastName());
        values.put(EMAIL, user.getEmail());
        values.put(USERNAME, user.getUsername());
        values.put(PASSWORD, user.getPassword());

        int i=db.update(TABLE_USERS, values, "Email =?", new String[]{String.valueOf(user.getEmail())});

        db.close();

        return i;
    }

    public void deleteUser(User user){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(TABLE_USERS, "Email =?", new String[]{String.valueOf(user.getEmail())});

        db.close();

        Log.d("deleteUser", user.toString());
    }

}*/
