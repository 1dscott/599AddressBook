//view all address page

package csc599.a599addressbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Arrays;



public class viewAllAddress extends MainActivity {

    //declare all required variables
    DatabaseHelper mDatabaseHelper;
    SharedPreferences sharedpreference;
    private ListView mListView;
    EditText search;
    ArrayAdapter adapter;
    String [] tempSplit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewalladdress);
        mDatabaseHelper = new DatabaseHelper(this, null, null, 1);
        sharedpreference = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());

        //search bar
        search = (EditText) findViewById(R.id.search);

        //get all the rows in the database
        Cursor data2 = mDatabaseHelper.getAllRow();

        //get all the string values we want and save them formated as string arraylist
        ArrayList<String> contact = new ArrayList<>();
        while (data2.moveToNext())
        {
            contact.add(data2.getString(1) +  " " + data2.getString(2) + " \n" + data2.getString(5) + " \n" + data2.getString(7) + "^" + data2.getString(0));
        }

        //put array list into an array and sort alphabetically
        String [] contactb = new String[contact.size()];
        for(int i = 0; i < contactb.length; i++)
        {
            contactb[i] =  contact.get(i);
        }
        Arrays.sort(contactb);

        //Split the row number off the end of each sorted string and save it into its own arrays
        //put the rowids into int array
        String [] rowid2 = new String[contactb.length];

        for(int n = 0; n < contactb.length; n++)
        {
            tempSplit = contactb[n].split("(?=\\^)|(?<=\\^)");
            contactb[n] = tempSplit[0];
            rowid2[n] = tempSplit[2];
        }

        final String [][] contact1 = new String[contact.size()][2];
        for(int i = 0; i < contact1.length; i++)
        {
            contact1[i][0] = rowid2[i];
            contact1[i][1] =  contactb[i];
        }

        //set values in our 2-d array as our listview vales
        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for(int i = 0; i < contact1.length; i++) {
            adapter.add(contact1[i][1]);
        }
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //check the listview value with the array vale to get the row id
                String text = mListView.getItemAtPosition(i).toString();
                String rowid = "0";
                for(int k = 0; k < contact1.length; k++)
                {
                    if(text.equals(contact1[k][1]))
                    {
                        rowid = contact1[k][0];
                    }
                }

                //popup add with success, save the row id into shared preferences, navigate to viewSelected
                Intent intent = new Intent(getApplicationContext(), viewSelected.class);
                sharedpreference.edit().putInt("rowid", Integer.parseInt(rowid)).apply();
                startActivity(intent);
            }
        });

        //resource
        //https://www.youtube.com/watch?v=rdQN2U1JJvY
        //SEARCH BAR WORKING BUY LINKS TO WRONG ROW
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (viewAllAddress.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //sort all listview entries alphabetically
    public void abcSort (View view) {
        Cursor data2 = mDatabaseHelper.getAllRow();

        ArrayList<String> contact = new ArrayList<>();
        while (data2.moveToNext())
        {
            contact.add(data2.getString(1) +  " " + data2.getString(2) + " \n" + data2.getString(5) + " \n" + data2.getString(7) + "^" + data2.getString(0));
        }

        String [] contactb = new String[contact.size()];
        for(int i = 0; i < contactb.length; i++)
        {
            contactb[i] =  contact.get(i);
        }
        Arrays.sort(contactb);

        //Split the row number off the end of each sorted string and save it into its own arrays
        //put the rowids into int array
        String [] rowid2 = new String[contactb.length];

        for(int n = 0; n < contactb.length; n++)
        {
            tempSplit = contactb[n].split("(?=\\^)|(?<=\\^)");
            contactb[n] = tempSplit[0];
            rowid2[n] = tempSplit[2];
        }

        final String [][] contact1 = new String[contact.size()][2];
        for(int i = 0; i < contact1.length; i++)
        {
            contact1[i][0] = rowid2[i];
            contact1[i][1] =  contactb[i];
        }


        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for(int i = 0; i < contact1.length; i++) {
            adapter.add(contact1[i][1]);
        }
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String text = mListView.getItemAtPosition(i).toString();
                String rowid = "0";
                for(int k = 0; k < contact1.length; k++)
                {
                    if(text.equals(contact1[k][1]))
                    {
                        rowid = contact1[k][0];
                    }
                }

                Intent intent = new Intent(getApplicationContext(), viewSelected.class);
                sharedpreference.edit().putInt("rowid", Integer.parseInt(rowid)).apply();
                startActivity(intent);
            }
        });
    }

    //Sort all list view enteries alphabeticall by Group
    public void groupSort (View view) {

        Cursor data2 = mDatabaseHelper.getAllRow();

        ArrayList<String> contact = new ArrayList<>();
        while (data2.moveToNext())
        {
            contact.add(data2.getString(5) + " \n" + data2.getString(1) +  " " + data2.getString(2) + " \n" + data2.getString(7) + "^" + data2.getString(0));
        }

        String [] contactb = new String[contact.size()];
        for(int i = 0; i < contactb.length; i++)
        {
            contactb[i] =  contact.get(i);
        }
        Arrays.sort(contactb);

        //Split the row number off the end of each sorted string and save it into its own arrays
        //put the rowids into int array
        String [] rowid2 = new String[contactb.length];

        for(int n = 0; n < contactb.length; n++)
        {
            tempSplit = contactb[n].split("(?=\\^)|(?<=\\^)");
            contactb[n] = tempSplit[0];
            rowid2[n] = tempSplit[2];
        }

        final String [][] contact1 = new String[contact.size()][2];
        for(int i = 0; i < contact1.length; i++)
        {
            contact1[i][0] = rowid2[i];
            contact1[i][1] =  contactb[i];
        }

        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        for(int i = 0; i < contact1.length; i++) {
            adapter.add(contact1[i][1]);
        }
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String text = mListView.getItemAtPosition(i).toString();
                String rowid = "0";
                for(int k = 0; k < contact1.length; k++)
                {
                    if(text.equals(contact1[k][1]))
                    {
                        rowid = contact1[k][0];
                    }
                }

                Intent intent = new Intent(getApplicationContext(), viewSelected.class);
                sharedpreference.edit().putInt("rowid", Integer.parseInt(rowid)).apply();
                startActivity(intent);
            }
        });
    }
}


