package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahdi on 3/5/2016.
 */
public class DBAdapter {
    public static final String KEY_ID="id";
    public static final String KEY_TITLE="title";
    public static final String KEY_DESCRIPTION="description";
    public static final String KEY_PICTURE="picture";
    public static final String KEY_BODY_PICTURE="bodyPicture";
    public static final String KEY_LIKE="like";
    public static final String KEY_READ="read";
    public static final String KEY_TYPE="type";
    public static final String DATABASE_NAME="foods";
    public static final String DATABASE_TABLE="foods";
    public static final String TAG="zzzzzzzzzzzzzz";

    static final String CREATE_TABLE = "CREATE  TABLE "
            + DATABASE_TABLE +" ("+ KEY_ID
            +" INTEGER PRIMARY KEY  NOT NULL , "+ KEY_TITLE +" TEXT, "+KEY_DESCRIPTION+" TEXT, "+KEY_PICTURE+" TEXT, "
            +KEY_BODY_PICTURE+" TEXT, "+KEY_LIKE+" INTEGER NOT NULL  DEFAULT 0, "+KEY_READ+" INTEGER NOT NULL  DEFAULT 0, "
            +KEY_TYPE+" TEXT NOT NULL  DEFAULT foods)";

    String[] yek_name = new String[]{KEY_ID,KEY_TITLE,KEY_DESCRIPTION,KEY_PICTURE,KEY_BODY_PICTURE,KEY_LIKE,KEY_READ,KEY_TYPE};










    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            //DATABASE_VERSION = 1
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(CREATE_TABLE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertContact(String id, String nam,String mavadavaliye,String dasturpokht,String note,String ezafi,String fav)
    {
        ContentValues initialValues = new ContentValues();
        //*

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---retrieves all the contacts---
    public List<Food> getAllContacts()
    {

        Cursor cursor = db.query(DATABASE_TABLE, yek_name, null, null, null, null, null);
        List<Food> nams = cursorToList(cursor);
        return nams;
    }

    private List<Food> cursorToList(Cursor cursor) {
        List<Food> nams = new ArrayList<Food>();
        if (cursor.getCount() > 0)
        {
            while (cursor.moveToNext()) {
                Food nam = new Food();
                //**
                nam.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                nam.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                nam.setDescription(cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION)));
                nam.setPicture(cursor.getString(cursor.getColumnIndex(KEY_PICTURE)));
                nam.setBodyPicture(cursor.getString(cursor.getColumnIndex(KEY_BODY_PICTURE)));
                nam.setLike(cursor.getInt(cursor.getColumnIndex(KEY_LIKE)));
                nam.setRead(cursor.getInt(cursor.getColumnIndex(KEY_READ)));
                nam.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));

                nams.add(nam);
            } ;
        }
        return nams;
    }
    //---retrieves a particular contact---
    public Food getContact(int new_id) throws SQLException
    {
        List<Food> nams = new ArrayList<Food>();
        Cursor cursor =db.query(true, DATABASE_TABLE,  yek_name, KEY_ID + " == '" + new_id + "'", null,
                null, null, null, null);
        Food nam = new Food();
        if (cursor != null) {
            cursor.moveToFirst();
            //***
            nams = cursorToList(cursor);


        }
        Log.i(TAG, nam.getTitle() + ",database");
        return nams.get(0);
    }

    ///search
    public List<Food> findContacts(String nam,String row) throws SQLException
    {
        Cursor cursor =
                db.query(true, DATABASE_TABLE, yek_name, row + " LIKE '%" + nam + "%'", null,
                        null, null, null, null);
        List<Food> nams = cursorToList(cursor);
        return nams;
    }
    ///fav?
    public List<Food> findFAVContacts() throws SQLException
    {
        Cursor cursor =db.query(true, DATABASE_TABLE, yek_name, KEY_LIKE + " == " + 1 + "", null,
                null, null, null, null);

        List<Food> nams = cursorToList(cursor);
        return nams;
    }

    public List<Food> findKhandeContacts() throws SQLException
    {
        Cursor cursor =db.query(true, DATABASE_TABLE, yek_name, KEY_READ + " == " + 1 + "", null,
                null, null, null, null);

        List<Food> nams = cursorToList(cursor);
        return nams;
    }

    //---updates a contact---
    public boolean updateContact(Food up_nam)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ID,up_nam.getId());
        args.put(KEY_TITLE, up_nam.getTitle());
        args.put(KEY_DESCRIPTION, up_nam.getDescription());
        args.put(KEY_PICTURE, up_nam.getPicture());
        args.put(KEY_BODY_PICTURE, up_nam.getBodyPicture());
        args.put(KEY_LIKE, up_nam.getLike());
        args.put(KEY_READ, up_nam.getRead());
        args.put(KEY_TYPE, up_nam.getType());
        
        
        return db.update(DATABASE_TABLE, args, KEY_ID + "=" + up_nam.getId(), null) > 0;
    }


}

