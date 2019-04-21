//add address page
package csc599.a599addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addAddress extends MainActivity {

    //declare all required variables
    private static final int GALLERY_REQUEST_CODE = 1;
    String lastNameb, firstNameb, nameSpouseb, nameChildrenb, groupb, dateb, addressb, zipb, cityb, stateb, countryb, homePhoneb, cellPhoneb, emailb, commentsb;
    String photo = "";

    DatabaseHelper mDatabaseHelper;

    EditText lastName;
    EditText firstName;
    EditText namespouse;
    EditText namechildren;
    EditText group;
    EditText date;
    EditText address;
    EditText zip;
    EditText city;
    EditText state;
    EditText country;
    EditText homePhone;
    EditText cellPhone;
    EditText email;
    EditText comments;

    /*Retrieve the date and time data from the database */
    Calendar calendar = Calendar.getInstance();
    DecimalFormat df = new DecimalFormat("####0.00");

    //get current date
    SimpleDateFormat ymdformat = new SimpleDateFormat("yyyy / MM / dd ");
    String dateaddedb =  ymdformat.format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddress);

        //set values to all required variables on activity creation
        mDatabaseHelper = new DatabaseHelper(this, null, null, 1);

        lastName = (EditText) findViewById(R.id.lastNameInput);
        firstName = (EditText) findViewById(R.id.firstNameInput);
        namespouse = (EditText) findViewById(R.id.nameSpouseInput);
        namechildren = (EditText) findViewById(R.id.nameChildrenInput);
        group = (EditText) findViewById(R.id.groupInput);
        date = (EditText) findViewById(R.id.dateInput);
        address = (EditText) findViewById(R.id.addressInput);
        zip = (EditText) findViewById(R.id.zipInput);
        city = (EditText) findViewById(R.id.cityInput);
        state = (EditText) findViewById(R.id.stateInput);
        country = (EditText) findViewById(R.id.countryInput);
        homePhone = (EditText) findViewById(R.id.homePhoneInput);
        cellPhone = (EditText) findViewById(R.id.cellPhoneInput);
        email = (EditText) findViewById(R.id.emailInput);
        comments = (EditText) findViewById(R.id.commentsInput);

    }

    //select the photo
    public void browse (View view)
    {
        //Open the galery and only allow image types to be chosen
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    //inserts all data into the database
    public void add(View view)
    {
        //check for the values in the input boxes and input the data to strings
        lastNameb = ((EditText) findViewById(R.id.lastNameInput)).getText().toString();
        if (lastNameb.equals(""))
        {
            lastNameb = "No Name";
        }
        firstNameb = ((EditText) findViewById(R.id.firstNameInput)).getText().toString();
        nameSpouseb = ((EditText) findViewById(R.id.nameSpouseInput)).getText().toString();
        nameChildrenb = ((EditText) findViewById(R.id.nameChildrenInput)).getText().toString();
        groupb = ((EditText) findViewById(R.id.groupInput)).getText().toString();
        if (groupb.equals(""))
        {
            groupb = "No Group";
        }
        dateb = ((EditText) findViewById(R.id.dateInput)).getText().toString();

        addressb = ((EditText) findViewById(R.id.addressInput)).getText().toString();
        if (addressb.equals(""))
        {
            addressb = "No Address";
        }

        zipb = ((EditText) findViewById(R.id.zipInput)).getText().toString();
        cityb = ((EditText) findViewById(R.id.cityInput)).getText().toString();
        stateb = ((EditText) findViewById(R.id.stateInput)).getText().toString();
        countryb = ((EditText) findViewById(R.id.countryInput)).getText().toString();

        homePhoneb = ((EditText) findViewById(R.id.homePhoneInput)).getText().toString();
        cellPhoneb = ((EditText) findViewById(R.id.cellPhoneInput)).getText().toString();
        emailb = ((EditText) findViewById(R.id.emailInput)).getText().toString();

        commentsb = ((EditText) findViewById(R.id.commentsInput)).getText().toString();

        boolean dupe = false;

        //call get all rows to check the database for existing address
        Cursor data2 = mDatabaseHelper.getAllRow();

        while (data2.moveToNext())
        {
            //if there is a dupe (same lastname, firstname, group, and address) set true (no dupes allowed!)
            if(lastNameb.equals(data2.getString(1)) && firstNameb.equals(data2.getString(2)) && groupb.equals(data2.getString(5)) && addressb.equals(data2.getString(7)))
            {
                dupe = true;
            }
            else
            {
                dupe =false;
            }
        }

        //if we have a dupe open up a notification box and don't allow for dupe to be added
        if(dupe == true)
        {
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(this);

            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setTitle("Warning!");
            alertDialogBuilder.setMessage("Cannot enter duplicate address, please change the last name, first name, group, or address.");

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick (DialogInterface dl, int i)
                {
                    dl.cancel();
                }
            });

            alertDialogBuilder.show();
        }
        else
        {
            //call AddData method to put new address into database
            AddData(lastNameb, firstNameb, nameSpouseb, nameChildrenb, groupb, dateb, addressb, zipb, cityb, stateb, countryb, homePhoneb, cellPhoneb, emailb,
                    commentsb, dateaddedb, photo);

            toastMessage("Address Added!");

            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }
    }

    //override the intent action when picking a photo to save the photo
    //Resource: https://stackoverflow.com/questions/10473823/android-get-image-from-gallery-into-imageview
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    photo = cursor.getString(columnIndex);
                    cursor.close();
                    // Confirm attached photo
                    toastMessage("Photo attached! ");
                    break;
            }
    }
}
