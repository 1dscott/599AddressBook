//Database SQLite helper class

package csc599.a599addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    //declare all database information
    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "address.db";
    private static final String TABLE_NAME = "address_info";
    //0
    private static final String COL_ID = "id";
    //1
    private static final String COL_NAMELAST = "name_last";
    //2
    private static final String COL_NAMEFIRST = "name_first";
    //3
    private static final String COL_NAMESPOUSE = "name_spouse";
    //4
    private static final String COL_NAMECHILDREN = "name_children";
    //5
    private static final String COL_GROUPS = "groups";
    //6
    private static final String COL_DATE = "date";
    //7
    private static final String COL_ADDRESS = "address";
    //8
    private static final String COL_ZIP = "zip";
    //9
    private static final String COL_CITY = "city";
    //10
    private static final String COL_STATE = "state";
    //11
    private static final String COL_COUNTRY = "country";
    //12
    private static final String COL_PHONEHOME = "phone_home";
    //13
    private static final String COL_PHONECELL = "phone_cell";
    //14
    private static final String COL_EMAIL = "email";
    //15
    private static final String COL_COMMENTS = "comments";
    //16
    private static final String COL_DATEADDED = "date_added";
    //17
    private static final String COL_PHOTO = "photo";


    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //creates a database for the first time when you open the app if one is not made
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //create the table in the database
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAMELAST + " TEXT," +
                COL_NAMEFIRST + " TEXT," + COL_NAMESPOUSE + " TEXT," +
                COL_NAMECHILDREN + " TEXT," + COL_GROUPS + " TEXT," +
                COL_DATE + " TEXT," + COL_ADDRESS + " TEXT," +
                COL_ZIP + " TEXT," + COL_CITY + " TEXT," +
                COL_STATE + " TEXT," + COL_COUNTRY + " TEXT," +
                COL_PHONEHOME + " TEXT," + COL_PHONECELL + " TEXT,"
                + COL_EMAIL + " TEXT," + COL_COMMENTS + " TEXT," +
                COL_DATEADDED + " TEXT," + COL_PHOTO + " TEXT"+ ");";
        db.execSQL(createTable);
    }

    //if you update the database in a later release this function replaces the old database with the new one
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if (oldVersion != newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    //Insert a row of data
    public boolean addData(String item1, String item2, String item3,
                           String item4, String item5, String item6,
                           String item7, String item8, String item9,
                           String item10, String item11, String item12,
                            String item13, String item14,String item15,
                           String item16, String item17) {
        //open the database
        SQLiteDatabase db = this.getWritableDatabase();

        //add the values recieved
        ContentValues values = new ContentValues();
        values.put(COL_NAMELAST, item1);
        values.put(COL_NAMEFIRST, item2);
        values.put(COL_NAMESPOUSE, item3);
        values.put(COL_NAMECHILDREN, item4);
        values.put(COL_GROUPS, item5);
        values.put(COL_DATE, item6);

        values.put(COL_ADDRESS, item7);
        values.put(COL_ZIP, item8);
        values.put(COL_CITY, item9);
        values.put(COL_STATE, item10);
        values.put(COL_COUNTRY, item11);

        values.put(COL_PHONEHOME, item12);
        values.put(COL_PHONECELL, item13);
        values.put(COL_EMAIL, item14);

        values.put(COL_COMMENTS, item15);
        values.put(COL_DATEADDED, item16);
        values.put(COL_PHOTO, item17);

        Log.d(TAG, "addData: Adding " + item1 + ", " + item2 + ", " + item3 + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //delete row of data
    public void deleteRow(String id)
    {
        //open database
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COL_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    //This area is for deleting the entire table
    public void deleteAll()
    {
        //open database
        SQLiteDatabase db = this.getWritableDatabase();

        //delete entire table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //close the database
        db.close();
    }

    //This creates a new database and table
    public void rebuildDatabase()
    {
        //open database
        SQLiteDatabase db = this.getWritableDatabase();

        //create the table in the database
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAMELAST + " TEXT," +
                COL_NAMEFIRST + " TEXT," + COL_NAMESPOUSE + " TEXT," +
                COL_NAMECHILDREN + " TEXT," + COL_GROUPS + " TEXT," +
                COL_DATE + " TEXT," + COL_ADDRESS + " TEXT," +
                COL_ZIP + " TEXT," + COL_CITY + " TEXT," +
                COL_STATE + " TEXT," + COL_COUNTRY + " TEXT," +
                COL_PHONEHOME + " TEXT," + COL_PHONECELL + " TEXT,"
                + COL_EMAIL + " TEXT," + COL_COMMENTS + " TEXT," +
                COL_DATEADDED + " TEXT," + COL_PHOTO + " TEXT"+ ");";
        db.execSQL(createTable);
    }

    //returns all rows data
    public Cursor getAllRow()
    {
        //open database
        SQLiteDatabase db = this.getWritableDatabase();

        //query database for the latest row added
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}