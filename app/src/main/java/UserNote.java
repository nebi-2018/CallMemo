package com.example.gbese.callmemo;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * this is the java class for the activity users add their call note
 */
public class UserNote extends AppCompatActivity {
    private EditText title1;
    private   EditText content1;
    private UserData data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_note);
        title1 = findViewById(R.id.displaytitle);
        content1 = findViewById(R.id.displaycontent);
        data = new UserData(this);

    }

    /**
     * this is the method from the UserData class
     * it add data to the database
     */
    public void addData() {
        String title;
        String content;
        title = title1.getText().toString();
        content = content1.getText().toString();

 // this boolean is for giving the user a toast message
       boolean dataIsInserted = data.addData(title, content);
        if (dataIsInserted == true) {
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(getApplicationContext(), "Data not saved", Toast.LENGTH_LONG).show();
        }

        finish();
    }

    /**
     * inflating the menu  icons in this  activity
     * @param menu parameter type
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * click listener for the icons
     * @param item parameter type
     * @return parameter type
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
// we are using id to identify the selected icon
// this icon show a toast if user have an empty title or content box
// if the user put all then it will call addData() to add the data to the database
            case R.id.savemanu:
                String title;
                String content;
                title = title1.getText().toString();
                content = content1.getText().toString();
                if (title.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Please Enter a Title", Toast.LENGTH_LONG).show();
                } else if (content.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter a content", Toast.LENGTH_LONG).show();
                } else {
                    addData();
                }
                return true;
//this icon is for sharing contents in the content box through different messaging app
//we are using implicit intent with action send
            case R.id.share_content:
                EditText shrecontent1 = findViewById(R.id.displaycontent);
                String sharecontent = shrecontent1.getText().toString();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, sharecontent);
                startActivity(Intent.createChooser(intent,"Share via"));
                return true;
// this icon will take the user to his calendar

            case R.id.set_calander:
                Intent intent1 = new Intent(Intent.ACTION_INSERT);
                intent1.setData(CalendarContract.Events.CONTENT_URI);
                startActivity(intent1);
        }
        return true;
    }


}