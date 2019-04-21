//Android Studio App - Address Book


package csc599.a599addressbook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 3;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new DatabaseHelper(this, null, null, 1);
    }

    //go to the view all addresses page
    public void viewall(View view)
    {
        Intent intent = new Intent (this, viewAllAddress.class);
        startActivity(intent);

    }

    //go to add address page
    public void addaddress(View view)
    {
        Intent intent = new Intent (this, addAddress.class);
        startActivity(intent);

    }

    //delete all contacts
    public void delete(View view)
    {
        //open warning box
        AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(this);

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("Warning!");
        alertDialogBuilder.setMessage("Are you sure you want to delete all your addresses?");

        //add cancel button
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick (DialogInterface dl, int i)
            {
                dl.cancel();
            }
        });

        //add ok button
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick (DialogInterface dl, int i)
            {
                //if ok is tapped delete the database and rebuid it
                mDatabaseHelper.close();
                mDatabaseHelper.deleteAll();
                mDatabaseHelper.rebuildDatabase();
                toastMessage("All of your Addresses Have Been Deleted!");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        alertDialogBuilder.show();
    }

    //main add data method
    //used to put all the new data into the database
    public void AddData(String item1, String item2, String item3,
                        String item4, String item5, String item6,
                        String item7, String item8, String item9,
                        String item10, String item11, String item12,
                        String item13, String item14,String item15,
                        String item16, String item17)
    {
        boolean insertData = mDatabaseHelper.addData(item1, item2, item3, item4, item5, item6, item7,
                item8, item9, item10, item11, item12, item13, item14, item15, item16, item17);

    }

    //pop up message method
    public void toastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
