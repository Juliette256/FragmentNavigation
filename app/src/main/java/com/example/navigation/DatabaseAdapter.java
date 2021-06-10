package com.example.navigation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


public class DatabaseAdapter extends SQLiteOpenHelper {
    Context context;
    Cursor mCursor;

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "users";

    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOC = "location";
    private static final String KEY_DESG = "designation";

    private static final String REGISTRATION = "registration";
    private static final String REGISTRATION_ID ="registration_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email_address";
    private static final String PASSWORD = "password";

    public DatabaseAdapter(Context context) {
        super(context,DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_LOC + " TEXT," + KEY_DESG + " TEXT"+ ")";

        String CREATE_TABLE_REG = "CREATE TABLE " + REGISTRATION + "("
                + REGISTRATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + FIRST_NAME + " TEXT,"
                + LAST_NAME + " TEXT,"  + EMAIL + " TEXT," + PASSWORD + " TEXT"+ ")";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_REG);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        db.execSQL("DROP TABLE IF EXISTS " + REGISTRATION);
        // Create tables again
        onCreate(db);

    }

    //    Register a new member
    public void Register(User user){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(FIRST_NAME, user.getFirstName());
        contentValues.put(LAST_NAME, user.getLastName());
        contentValues.put(EMAIL, user.getEmail());
        contentValues.put(PASSWORD, user.getPass());

        db.insert(REGISTRATION,null,contentValues);
        db.close();
    }

    //        Checking user for login
    boolean CheckUserForLogin(String email, String password){
        SQLiteDatabase db= this.getReadableDatabase();

        String [] column ={EMAIL, PASSWORD};
        String selection = EMAIL + " = ? " + " AND " + PASSWORD + " =?";
        String [] selectionArgs ={email,password};

        Cursor cursor=db.query(REGISTRATION, column ,selection,selectionArgs,null,null,null);
        int cursorCount=cursor.getCount();
        if(cursorCount>0){
            return true;
        }
        return false;
    }

    //        Checking user for signUp

    boolean CheckUserForSignUp(String email){
        SQLiteDatabase db= this.getReadableDatabase();

        String [] column ={EMAIL, PASSWORD};
        String selection = EMAIL + " = ? ";
        String [] selectionArgs ={email};

        Cursor cursor=db.query(REGISTRATION, column ,selection,selectionArgs,null,null,null);
        int cursorCount=cursor.getCount();
        if(cursorCount>0){
            return true;
        }
        return false;
    }

    // Adding new User Details
    public void insertUserDetails(String name, String location, String designation){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cValues = new ContentValues();
        cValues.put(KEY_NAME, name);
        cValues.put(KEY_LOC, location);
        cValues.put(KEY_DESG, designation);
        db.insert(TABLE_Users,null, cValues);

    }

    // Getting User Details
    public SimpleCursorAdapter GetListViewFromDB(){
        SQLiteDatabase db = this.getReadableDatabase();

        String columns[]= {DatabaseAdapter.KEY_ID, DatabaseAdapter.KEY_NAME, DatabaseAdapter.KEY_LOC, DatabaseAdapter.KEY_DESG};

        Cursor cursor = db.query(DatabaseAdapter.TABLE_Users, columns,null,null,null,null,null);

        String[] fromFieldNames= new String[]{DatabaseAdapter.KEY_ID,DatabaseAdapter.KEY_NAME,
                DatabaseAdapter.KEY_LOC, DatabaseAdapter.KEY_DESG};
        int[] toViewIDs = new int[]{R.id._id,R.id.name,R.id.location,R.id.designation};

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(
                context, R.layout.rowlayout,cursor,fromFieldNames,toViewIDs
        );
        simpleCursorAdapter.notifyDataSetChanged();

        return simpleCursorAdapter;
    }

    public int GetRowCount(){
        if(mCursor == null) {
            GetListViewFromDB();
        }return  mCursor.getCount();
    }


    // Load members
    public ArrayList<User> LoadUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
      ArrayList<User>users = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM  userdetails " , null );
         if(cursor.moveToFirst()){

            while(cursor.isAfterLast() ==false) {
                User s = new User();
                String name=cursor.getString(cursor.getColumnIndex(KEY_NAME));
               String location=cursor.getString(cursor.getColumnIndex(KEY_DESG));
                String designation=cursor.getString(cursor.getColumnIndex(KEY_LOC));

             s.setName(name);
             s.setLocation(location);
             s.setAddress(designation);
             users.add(s);
             cursor.moveToNext();
         }

    } 
      return users;}



        // Update User Details
    public int UpdateUserDetails(int id, String name, String location, String designation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseAdapter.KEY_NAME, name);
        contentValues.put(DatabaseAdapter.KEY_LOC, location);
        contentValues.put(DatabaseAdapter.KEY_DESG, designation);

        String whereArgs[] = {"" + id};
        int count = db.update(DatabaseAdapter.TABLE_Users, contentValues, KEY_ID + " =?", whereArgs);
         return count;
    }

    //    deleting a user
    public boolean delete(int rowId){
        SQLiteDatabase db=this.getWritableDatabase();
        String whereArgs[] ={""+rowId};
        db. delete(DatabaseAdapter.TABLE_Users,KEY_ID + "=?", whereArgs);
        return false;
    }

}