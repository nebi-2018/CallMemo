package com.example.gbese.callmemo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * this is our class For  SQLite DATABASE helper
 * our database name and table name is listed below
 */
public class UserData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME ="NebiiiBese.db";
    private static final int    DATABASE_VERSION  =1;


    /**
     * this is the constructor for our sqlite database helper which has the database name and database version as a parameter
     * @param context - parameter type
     */
    public UserData(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * sql database is created in on create method
     * we are creating  here  the table for the database
     * the table has title and content as column
     * @param db parameter type
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE CALLABLE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "TITLE TEXT, "
                + "CONTENT TEXT);");
    }

    /**
     *  we are not currently using this method but it should be here  whenever we need to update our database we can use it
     * @param db  database
     * @param oldVersion  older version
     * @param newVersion   new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * this is a method we use later in UserNote Class
     * used to add data from the user in to the database
     * itemtitle and itemcontent are data's from the user to the database table
     * @param itemtitle the title
     * @param itemcontent the context
     * @return true
     */
    public boolean addData(String itemtitle,String itemcontent ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TITLE",itemtitle);
        contentValues.put("CONTENT", itemcontent);
        db.insert("CALLABLE", null, contentValues);


        return true;

    }

    /**
     * this is a method which use list Id from  from the simple cursor adapter
     * using the ID of the clicked item we can get both the content and the item the a specific item
     * we use this method in Displayer class
     * @param ListId the id
     * @return cursor which contain a data
     */
    public Cursor getListContent(int ListId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cuursor = db.query("CALLABLE",
                new String[]{"_id","TITLE", "CONTENT"}, "_id = ?", new String[]{Integer.toString(ListId)}
                , null, null, null);
        return cuursor;

    }

    /**
     * this method use two string as a parameter
     * it let users to update this data add to database
     * we use this method later in Dispalyer class
     *
     * @param itemtitle title
     * @param itemcontent content
     * @param id the id
     * @return  true
     */
    public boolean UpdateData(String itemtitle,String itemcontent ,int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("TITLE",itemtitle);
        contentValues.put("CONTENT", itemcontent);
        db.update("CALLABLE",contentValues,
                "_id = ?",
                new String[]{Integer.toString(id)});


        return true;

    }

    /**
     * this method use the id of the clicked item from the list view
     * using this id it let user to delete the data
     * we use this method later in Dispalyer class
     * @param id the id
     * @return true
     */
    public boolean DeleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("CALLABLE",
                "_id = ?",
                new String[]{Integer.toString(id)});


        return true;

    }

    /**
     *this is a delete method for context menu
     * @param id the id
     * @return true
     */
    public boolean contextMenuDeleteData(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("CALLABLE",
                "_id = ?",
                new String[]{Long.toString(id)});


        return true;

    }

    /**
     * cursor for the context menu
     * @param ListId the id of the item
     * @return cursor with the data inside
     */
    public Cursor getListContextMenuContent(long ListId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cuursor = db.query("CALLABLE",
                new String[]{"_id","TITLE", "CONTENT"}, "_id = ?", new String[]{Long.toString(ListId)}
                , null, null, null);
        return cuursor;

    }



}
