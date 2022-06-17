package com.example.scannerapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "itemsdb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "items";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our item name column
    private static final String NAME_COL = "name";

    // below variable for our course description column.
    private static final String PRICE_COL = "price";

    public static final String CODE_COL = "code";
    public int id;
    ResultSet result= null;
    Statement statement = null;
    Connection dbConnection = null;




    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating 
        // an sqlite query and we are 
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PRICE_COL + " TEXT,"
                + CODE_COL + " TEXT)";


        // at last we are calling a exec sql 
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new course to our sqlite database.
    public void addNewItem(String itemName, String itemPrice, String itemCode) {

        // on below line we are creating a variable for 
        // our sqlite database and calling writable method 
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a 
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values 
        // along with its key and value pair.
        values.put(NAME_COL, itemName);
        values.put(PRICE_COL, itemPrice + "₪");
        values.put(CODE_COL, itemCode);

        // after adding all values we are passing
        // content values to table.
        db.insert(TABLE_NAME, null, values);
        //closing db
        db.close();
    }
    //TODO editItem with 3 points button
    public void editItem(String oldItemName, String newItemName, String itemPrice, String itemCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, newItemName);
        values.put(PRICE_COL, itemPrice);
        values.put(CODE_COL, itemCode);

        db.update(TABLE_NAME, values, "name=?", new String[]{oldItemName});
        db.close();
        onCreate(db);

    }

    public boolean isExist(String rcode) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME+ " WHERE " + CODE_COL + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {rcode});

        boolean hasObject = false;
        if(cursor.moveToFirst()){
            hasObject = true;

            //region if you had multiple records to check for, use this region.

            int count = 0;
            while(cursor.moveToNext()){
                count++;
            }
            //here, count is records found
            Log.d(TAG, String.format("%d records found", count));

            //endregion

        }

        cursor.close();          // Dont forget to close your cursor
        db.close();              //AND your Database!
        return hasObject;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<ItemModal> readItems() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a cursor with query to read data from database.
        Cursor cursorItems = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<ItemModal> itemModalArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorItems.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                itemModalArrayList.add(new ItemModal(cursorItems.getString(1),
                        cursorItems.getString(2),
                        cursorItems.getString(3)));
            } while (cursorItems.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our cursor
        // and returning our array list.
        cursorItems.close();
        return itemModalArrayList;
    }
    public ItemModal readItem(int pos) throws CursorIndexOutOfBoundsException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        c.moveToPosition(pos);
        ItemModal itemModal = new ItemModal(c.getString(1),c.getString(2),c.getString(3));

        return itemModal;
    }

    //TODO DEL ITEM BY SWIPING
    public void deleteItem(String ItemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{ItemName});
        db.close();
    }

    public List<ItemModal> getData() throws NullPointerException{
        List<ItemModal> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +";";
        Cursor c = db.rawQuery(query,null);
        c.moveToFirst();

        if (c.equals(null)){
            return null;
        }
        else{
            do {
                list.add(new ItemModal(c.getString(1),c.getString(2),c.getString(3)));

            }while (c.moveToNext());

            }
        db.close();
        return list;
        }

    }







