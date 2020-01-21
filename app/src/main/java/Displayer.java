package com.example.gbese.callmemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import static android.nfc.NfcAdapter.EXTRA_ID;

/**
 * this is a class for the activity that user can open to see or edit their call note or memo
 * in this cla we used an id of the selected item to get data from the database
 * the id is from the intent which is from the main  activity
 * we give this id to the cursor and and the cursor browse the database and deliver the data
 * we receive the data from the cursor and display it the edit text box
 */
public class Displayer extends AppCompatActivity {
    private EditText displaytitle1;
    private EditText displaycontent1;
    private UserData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayer);
        displaytitle1 = findViewById(R.id.displaytitle);
        displaycontent1 = findViewById(R.id.displaycontent);
        // below; getting the id of thr list item from the intent and save it to an int variable
        int ListId = (Integer) getIntent().getExtras().get(EXTRA_ID);
        // calling the UserData class
        data = new UserData(this);
        // below ; this cursor get the data
        Cursor displayerCursor =  data.getListContent(ListId);
        // browsing through our database class
        if (displayerCursor.moveToFirst()) {
            String ttitle = displayerCursor.getString(1);
            String ccontent = displayerCursor.getString(2);
            displaytitle1.setText(ttitle);
            displaycontent1.setText(ccontent);
       }
    }

    /**
     * inflating the icons
     * @param menu take menu as a parameter
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save2,menu);

        return true;
    }

    /**
     * setting the function for the icons
     * here we have 4 icons to delete, share and save memo and a calendar icon
     * so we set onclick action for each of them
     * @param item take item as a parameter
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // we used if method in this case to identify the selected icon
        //below;  this icon save the data if the user update or edit his memo

        if(item.getItemId()==R.id.noteitem2){
            // getting the content and title from the edit text widget
            String newcontent = displaycontent1.getText().toString();
            String newtitle = displaytitle1.getText().toString();
           // use the id to update the data
            int ListId = (Integer) getIntent().getExtras().get(EXTRA_ID);
            SQLiteOpenHelper data = new UserData(this);
          // we use the UpdateData method from the UserData class to update th memo
            ((UserData) data).UpdateData(newtitle,newcontent,ListId);
      // using if method  and boolean  we notify the user whether his data is update in the database otr note
            boolean mm = ((UserData) data).UpdateData(newtitle,newcontent,ListId);
            if(mm){
                Toast.makeText(getApplicationContext(), "Memo is updated :)", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Data is not updated :(", Toast.LENGTH_LONG).show();
            }
        }
        // this icon delete the selected memo data
        // again we user the id from the intent and we call the delete data method from the UserData class
        //this method use the id from the intent and delete the data
        else if(item.getItemId()==R.id.noteitem22){

            int ListId2 = (Integer) getIntent().getExtras().get(EXTRA_ID);
            UserData daata = new UserData(this);
            ((UserData) daata).DeleteData(ListId2);
            boolean deleteddata = ((UserData) daata).DeleteData(ListId2);
            if(deleteddata){
                Toast.makeText(getApplicationContext(), "Memo is Deleted :)", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Memo is not deleted  :)", Toast.LENGTH_LONG).show();

            }

            }

            // thi icon is an intent to the calendar
        else if(item.getItemId() == R.id.set_calander){

            Intent intent1 = new Intent(Intent.ACTION_INSERT);
            intent1.setData(CalendarContract.Events.CONTENT_URI);
            startActivity(intent1);
        }
        // this icon is to share content of the call note using an implicit intent
        else if(item.getItemId() == R.id.share_content){

            EditText shrecontent1 = (EditText) findViewById(R.id.displaycontent);
            String sharecontent = shrecontent1.getText().toString();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sharecontent);
            startActivity(Intent.createChooser(intent,"Share via"));
            return true;
        }

        finish();
        return true;

    }
}