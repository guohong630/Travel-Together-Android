package com.example.xinyue.helloworld.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.xinyue.helloworld.Network.NetworkOperation;
import com.example.xinyue.helloworld.R;
import com.example.xinyue.helloworld.util.PlanGenerator;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Edward on 5/23/15.
 */
public class EditPost extends Activity {
    public static final String MY_PREFS_NAME = "tokenInfo";

    private EditText groupSize;
    private EditText departureDate;
    private EditText returnDate;
    private EditText addFriends;
    private ArrayList<String> friendIdList = new ArrayList<String>();
    private ArrayList<String> friendNameList = new ArrayList<String>();
    private Date deptDate = null;
    private Date retDate = null;
    private boolean isFriendIn[] = new boolean[friendNameList.size()];
    private boolean tmpFriendIn[] = new boolean[friendNameList.size()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);

//        if (getIntent().getExtras() != null) {
//            for(String a : getIntent().getExtras().getStringArrayList("friendIdList")) {
//                friendIdList.add(a);
//            }
//            for(String a : getIntent().getExtras().getStringArrayList("friendNameList")) {
//                friendNameList.add(a);
//            }
//
//        }
        addListenerOnGroupSize();
        addListenerOnDepartDate();
        addListenerOnReturnDate();
        addListenerOnAddFriend();

    }

    public void addListenerOnGroupSize(){
        groupSize = (EditText)findViewById(R.id.edit_groupsize);
        groupSize.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    showGroupSize();
            }

        });
        groupSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                showGroupSize();

            }
        });
    }


    public void showGroupSize()
    {

        final Dialog d = new Dialog(EditPost.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.group_size_dialog);
        Button b1 = (Button) d.findViewById(R.id.button1);
        Button b2 = (Button) d.findViewById(R.id.button2);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(20); // max value 100
        np.setMinValue(2);   // min value 0
        np.setWrapSelectorWheel(false);
        //np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                groupSize.setText(String.valueOf(np.getValue())); //set the value to textview
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();
    }

    public void addListenerOnAddFriend(){
        addFriends = (EditText) findViewById(R.id.edit_add_friend);
        addFriends.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showFriendList();

            }

        });
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                showFriendList();

            }
        });
    }

    public void addListenerOnDepartDate(){
        departureDate = (EditText)findViewById(R.id.edit_departure_date);
        departureDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showDepartDate(v);
            }

        });
        departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                showDepartDate(view);

            }
        });
    }

    public void addListenerOnReturnDate(){
        returnDate = (EditText)findViewById(R.id.edit_return_date);
        returnDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    showReturnDate(v);
            }

        });
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                showReturnDate(view);

            }
        });
    }


    public void showDepartDate(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-DD");
                Calendar tmp = Calendar.getInstance();
                tmp.set(year, month, day);
                deptDate = tmp.getTime();
                departureDate.setText(df.format(tmp.getTime()));
            }
        }, mYear, mMonth, mDay);
        dpg.show();
    }

    public void showReturnDate(View view){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-DD");
                Calendar tmp = Calendar.getInstance();
                tmp.set(year, month, day);
                retDate = tmp.getTime();
                returnDate.setText(df.format(tmp.getTime()));
            }
        }, mYear, mMonth, mDay);
        dpg.show();
    }

    public void showFriendList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("@string/friend_list");
        String[] nameList = new String[friendNameList.size()];
        builder.setMultiChoiceItems(friendNameList.toArray(nameList), isFriendIn, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                if (isChecked && (which < friendNameList.size()))
                    tmpFriendIn[which] = true;
                else if (which < friendNameList.size())
                    tmpFriendIn[which] = false;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK, so save the mSelectedItems results somewhere
                // or return them to the component that opened the dialog
                isFriendIn = Arrays.copyOf(tmpFriendIn, tmpFriendIn.length);
                //Set the name in the text view...
                int count = 0;
                int first = -1;
                for (int i = 0; i < tmpFriendIn.length; i++) {
                    if (tmpFriendIn[i] == true) {
                        count++;
                        if (first == -1)
                            first = i;
                    }
                }
                if (count > 0) {
                    StringBuilder string = new StringBuilder();
                    string.append(friendNameList.get(first));
                    if (count > 1) {
                        string.append(" " + "and " + Integer.toString(count - 1) + " more");
                    }
                    addFriends.setText(string.toString());
                } else {
                    addFriends.setText("Click to Add Friends");
                }
            }
        });
    }

    public void moveToList(View view){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    public void sendMessage(View view){
        final Context context = this;
        final EditText destinationField = (EditText) findViewById(R.id.edit_destination);
        String destination = destinationField.getText().toString();
        final String TAG = "MyActivity";

        Log.d(TAG, "index=" + destination);

        String test = "test";
        final EditText departureDateField = (EditText) findViewById(R.id.edit_departure_date);
        String departureDate = departureDateField.getText().toString();

        final EditText sizeField = (EditText) findViewById(R.id.edit_groupsize);
        String size = sizeField.getText().toString();

        final CheckBox responseCheck = (CheckBox) findViewById(R.id.edit_shareFacebook);
        boolean share = responseCheck.isChecked();

        final Spinner spinnerMem = (Spinner) findViewById(R.id.spinner);
        String members = spinnerMem.getSelectedItem().toString();

        final EditText informationField = (EditText) findViewById(R.id.edit_addtional_information);
        String information = informationField.getText().toString();

        Intent intent = new Intent(context, Welcome.class);
        startActivity(intent);

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.edit_radioGroup);
        int selected = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)findViewById(selected);
        int type = 1;
        if (radioButton.getText().toString().equalsIgnoreCase("Friends"))
            type = 2;
        if(radioButton.getText().toString().equalsIgnoreCase("2nd degree"))
            type = 3;


        ArrayList<String> addedFriendsId = new ArrayList<String>();
        for (int i= 0; i<isFriendIn.length; i++){
            if(isFriendIn[i] == true)
                addedFriendsId.add(friendIdList.get(i));
        }

        int days = 0;
        if(retDate != null){
            days = (int)(retDate.getTime() - deptDate.getTime())/(24 * 60 * 60 * 1000);
        }
        String text = PlanGenerator.getPlanString("none", destination, departureDate, Integer.toString(days), information, Integer.toString(type), size, addedFriendsId);
        String token = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).getString("fbAccessToken", "");
        String query = "";
        Log.i("before_add", destination + " " + departureDate + " duration: " + Integer.toString(days) + " info: " + information + " type: " + Integer.toString(type) + " size: " + size);
        Log.i("query", text);
        try {
            query += URLEncoder.encode(text, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        NetworkOperation no = new NetworkOperation();
        JSONObject res = no.addPlan(token, query);
        if(res != null){

        }

        // need to send the message
    }


    public void onRadioButtonClicked(View view) {
        RadioGroup rg = (RadioGroup) findViewById(R.id.edit_radioGroup);
        int selectedID = rg.getCheckedRadioButtonId();

        RadioButton selectedRadioButton = (RadioButton) findViewById(selectedID);
        String privacy = selectedRadioButton.getText().toString();
        String test = "test";
    }

}