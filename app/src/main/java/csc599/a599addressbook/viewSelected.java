//view selected address page

package csc599.a599addressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class viewSelected extends viewAllAddress {

    //declare all required variables

    private static final int GALLERY_REQUEST_CODE = 1;
    String lastNameb, firstNameb, nameSpouseb, nameChildrenb, groupb, dateb, addressb, zipb, cityb, stateb, countryb, homePhoneb, cellPhoneb, emailb, commentsb;
    String photo = "";

    DatabaseHelper mDatabaseHelper;
    SharedPreferences sharedpreference;

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

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewselected);

        //set values to all required variables on activity creation

        mDatabaseHelper = new DatabaseHelper(this, null, null, 1);
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

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

        imageView = (ImageView)findViewById(R.id.imageView);

        //retrieve the address info from the database and put into proper fields
        String rowCurrent = Integer.toString(sharedpreference.getInt("rowid", 0));
        Cursor data2 = mDatabaseHelper.getAllRow();
        while (data2.moveToNext())
        {
            if (data2.getString(0).equals(rowCurrent))
            {
                lastName.setText(data2.getString(1));
                firstName.setText(data2.getString(2));
                namespouse.setText(data2.getString(3));
                namechildren.setText(data2.getString(4));
                group.setText(data2.getString(5));
                date.setText(data2.getString(6));

                address.setText(data2.getString(7));
                zip.setText(data2.getString(8));
                city.setText(data2.getString(9));
                state.setText(data2.getString(10));
                country.setText(data2.getString(11));
                homePhone.setText(data2.getString(12));
                homePhoneb = data2.getString(12);
                cellPhone.setText(data2.getString(13));
                cellPhoneb = data2.getString(13);
                email.setText(data2.getString(14));
                emailb = data2.getString(14);
                comments.setText(data2.getString(15));

                if(data2.getString(17).equals(""))
                {
                    imageView.setImageResource(R.drawable.photoplaceholder);
                }
                else
                {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(data2.getString(17)));
                    photo = data2.getString(17);
                }
            }
        }
    }


    //call phone number 1
    public void callOne (View view)
    {
        //declare boolean for duplicate possibility
        boolean empty = false;

        //call get all rows to check the database for existing address
        Cursor data2 = mDatabaseHelper.getAllRow();

        //checks all the rows to match last name, first name, group, and address
        while (data2.moveToNext())
        {
            if(homePhoneb.equals(""))
            {
                empty = true;
            }
            else
            {
                empty =false;
            }
        }

        //if we get a dupe entry
        if(empty == true)
        {
            //open an error box informating of the dupe and instructing user to change entry
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(this);

            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setTitle("Alert!");
            alertDialogBuilder.setMessage("Cannot call phone 1, please enter a phone number in the phone 1 field.");

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
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + homePhoneb));
            startActivity(intent);
        }

    }

    //call phone number 2
    public void callTwo (View view)
    {

        //declare boolean for duplicate possibility
        boolean empty = false;

        //call get all rows to check the database for existing address
        Cursor data2 = mDatabaseHelper.getAllRow();

        //checks all the rows to match last name, first name, group, and address
        while (data2.moveToNext())
        {
            if(cellPhoneb.equals(""))
            {
                empty = true;
            }
            else
            {
                empty =false;
            }
        }

        //if we get a dupe entry
        if(empty == true)
        {
            //open an error box informating of the dupe and instructing user to change entry
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(this);

            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setTitle("Alert!");
            alertDialogBuilder.setMessage("Cannot call phone 2, please enter a phone number in the phone 2 field.");


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
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + homePhoneb));
            startActivity(intent);
        }

    }

    //email button
    public void email (View view)
    {
        //declare boolean for duplicate possibility
        boolean empty = false;

        //call get all rows to check the database for existing address
        Cursor data2 = mDatabaseHelper.getAllRow();

        //checks all the rows to match last name, first name, group, and address
        while (data2.moveToNext())
        {
            if(emailb.equals(""))
            {
                empty = true;
            }
            else
            {
                empty =false;
            }
        }

        //if we get a dupe entry
        if(empty == true)
        {
            //open an error box informating of the dupe and instructing user to change entry
            AlertDialog.Builder alertDialogBuilder  = new AlertDialog.Builder(this);

            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setTitle("Alert!");
            alertDialogBuilder.setMessage("Cannot send email, please enter an email in the email field.");


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
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",emailb, null));
            startActivity(Intent.createChooser(intent, "How would you like to send your email?"));
        }
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

    //override the intent action when picking a photo to save the photo
    //Resource: https://stackoverflow.com/questions/10473823/android-get-image-from-gallery-into-imageview
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        //Result = RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    //Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    //Move to row 1
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    photo = cursor.getString(columnIndex);
                    cursor.close();
                    //Set the Image in ImageView after decoding the String
                    toastMessage("New photo attached, tap update to update your address! ");
                    //imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    break;

            }
    }


    //inserts all data into the database
    public void update(View view)
    {
        //retrieve the current row id
        String rowCurrent = Integer.toString(sharedpreference.getInt("rowid", 0));

        //call delete row
        mDatabaseHelper.deleteRow(rowCurrent);

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
            addressb = "No Name";
        }

        zipb = ((EditText) findViewById(R.id.zipInput)).getText().toString();
        cityb = ((EditText) findViewById(R.id.cityInput)).getText().toString();
        stateb = ((EditText) findViewById(R.id.stateInput)).getText().toString();
        countryb = ((EditText) findViewById(R.id.countryInput)).getText().toString();

        homePhoneb = ((EditText) findViewById(R.id.homePhoneInput)).getText().toString();
        cellPhoneb = ((EditText) findViewById(R.id.cellPhoneInput)).getText().toString();
        emailb = ((EditText) findViewById(R.id.emailInput)).getText().toString();

        commentsb = ((EditText) findViewById(R.id.commentsInput)).getText().toString();

        /*Create a calandar to retrieve and store current date*/
        Calendar calendar = Calendar.getInstance();
        DecimalFormat df = new DecimalFormat("####0.00");

        //get current date
        SimpleDateFormat ymdformat = new SimpleDateFormat("yyyy / MM / dd ");
        String dateaddedb =  ymdformat.format(calendar.getTime());

        //declare boolean for duplicate possibility
        boolean dupe = false;

        //call get all rows to check the database for existing address
        Cursor data2 = mDatabaseHelper.getAllRow();

        //checks all the rows to match last name, first name, group, and address
        while (data2.moveToNext())
        {
            if(lastNameb.equals(data2.getString(1)) && firstNameb.equals(data2.getString(2)) && groupb.equals(data2.getString(5)) && addressb.equals(data2.getString(7)))
            {
                dupe = true;
            }
            else
            {
                dupe =false;
            }
        }

        //if we get a dupe entry
        if(dupe == true)
        {
            //open an error box informating of the dupe and instructing user to change entry
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

            //pop up notification of success
            toastMessage("Address Updated! ");

            //take us back the the main activity
            Intent intent = new Intent (this, MainActivity.class);
            startActivity(intent);
        }
    }

    //calls the database helper method deleteRow on the current rowid for the selected address
    public void deleteCurrent(View view)
    {
        //get rowid
        String rowCurrent = Integer.toString(sharedpreference.getInt("rowid", 0));
        //call delete row
        mDatabaseHelper.deleteRow(rowCurrent);

        //pop up notification of success
        toastMessage("Address Deleted! ");

        //take us back to the amain activity
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}
