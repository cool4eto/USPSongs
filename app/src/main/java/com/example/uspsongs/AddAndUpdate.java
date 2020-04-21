package com.example.uspsongs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uspsongs.model.Song;

import java.util.Calendar;

public class AddAndUpdate extends AppCompatActivity {
    DatabaseHelper songDb;
    private EditText eName;
    private EditText eAuthor;
    private EditText eDuration;
    private EditText eYear;
    private EditText eGenre;
    private Button button;
    private Button deleteButton;
    private Song songToChange;
    TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    DatePickerDialog.OnDateSetListener mOnYearListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_update);
        songDb=new DatabaseHelper(this);
        eName=findViewById(R.id.editText);
        eAuthor=findViewById(R.id.editText3);
        eDuration=findViewById(R.id.editText4);

        eDuration.setFocusable(false);
        eDuration.setClickable(true);


        eYear=findViewById(R.id.editText5);

        eYear.setFocusable(false);
        eYear.setClickable(true);

        eGenre=findViewById(R.id.editText6);
        button=findViewById(R.id.button);
        deleteButton=findViewById(R.id.button2);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteSong();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button.getText().toString().equals("Update"))
                UpdateSong();
                else
                AddSong();
            }
        });
        eDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int minute=0;
                int second=0;
                if(button.getText().toString().equals("Update")) {
                    String[] units = eDuration.getText().toString().split(":");
                    minute=Integer.parseInt(units[0]);
                    second = Integer.parseInt(units[1]);
                }
                TimePickerDialog mTimePickerDialog = new TimePickerDialog(
                        AddAndUpdate.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnTimeSetListener,
                        minute,second,true);
                mTimePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mTimePickerDialog.show();
            }
        });
        mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int minute, int second) {
                String mTime = minute+":"+second;
                eDuration.setText(mTime);
            }
        };
        eYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year=2020;
                if(button.getText().toString().equals("Update")){
                year=Integer.parseInt(eYear.getText().toString());}
                DatePickerDialog mYearPickerDialog=new DatePickerDialog(AddAndUpdate.this,AlertDialog.THEME_HOLO_LIGHT,mOnYearListener,year,2,2);
                mYearPickerDialog.getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);// premahva den ot skrolera
                mYearPickerDialog.getDatePicker().findViewById(getResources().getIdentifier("month","id","android")).setVisibility(View.GONE);//premahva mesec ot skrolera



                mYearPickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mYearPickerDialog.show();
            }
        });
        mOnYearListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                eYear.setText(Integer.toString(year));
            }
        };



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//za da imame butonche za wrashrane w glavnata stranica dobavihme i v manifest stranicata :  android:parentActivityName=".MainActivity"


        /*setFields("It's my life","Bon Jovi","266","2000","pop rock");*/
        Intent intent = getIntent();
        try {
            Bundle data=intent.getExtras();//tozi i dolniq red sa za parcelable
            songToChange= (Song) data.getParcelable("songToChange");
            //Log.d("SongToChange",songToChange.toString());
            setFields(songToChange.getName().toString(),songToChange.getAuthor().toString(),Integer.toString(songToChange.getDuration()),Integer.toString(songToChange.getYear()),songToChange.getGenre().toString());
            button.setText("Update");
            deleteButton.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void AddSong()//funkicq koqto dobawq pesen w bazata ot danni
    {
        try {
            boolean isInserted=false;
            if(!eName.getText().toString().equals("")&&!eAuthor.getText().toString().equals("")&&!eDuration.getText().toString().equals("")&&!eYear.getText().toString().equals("")&&!eGenre.getText().toString().equals(""))
             isInserted=songDb.insertSong(eName.getText().toString(),eAuthor.getText().toString(),minsToSecs(eDuration.getText().toString()),eYear.getText().toString(),eGenre.getText().toString());
            if(isInserted) {
                Toast.makeText(AddAndUpdate.this, "Song Inserted", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(AddAndUpdate.this, "Song NOT Inserted", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(AddAndUpdate.this, "Song NOT Inserted Check the fields", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }
    public void UpdateSong()
    {
        try {
            boolean isUpdated=false;
            if(!eName.getText().toString().equals("")&&!eAuthor.getText().toString().equals("")&&!eDuration.getText().toString().equals("")&&!eYear.getText().toString().equals("")&&!eGenre.getText().toString().equals(""))
                 isUpdated=songDb.updateSong(Integer.toString(songToChange.getId()),eName.getText().toString(),eAuthor.getText().toString(),minsToSecs(eDuration.getText().toString()),eYear.getText().toString(),eGenre.getText().toString());
            if(isUpdated&&!eName.getText().toString().equals("")&&!eAuthor.getText().toString().equals("")&&!eDuration.getText().toString().equals("")&&!eYear.getText().toString().equals("")&&!eGenre.getText().toString().equals(""))
            {
                Toast.makeText(AddAndUpdate.this, "Song Updated", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(AddAndUpdate.this, "Song NOT Updated", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(AddAndUpdate.this, "Song NOT Updated Check the fields", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    public void DeleteSong()
    {
        boolean isDeleted=songDb.deleteSong(Integer.toString(songToChange.getId()));
        if(isDeleted)
        {
            Toast.makeText(AddAndUpdate.this, "Song Deleted", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        else
        {
            Toast.makeText(AddAndUpdate.this, "Song NOT Deleted", Toast.LENGTH_LONG).show();
        }

    }
    public void onBackPressed() {//prezapiswame go za da moje kogato se wurnem kum aktivitito sus pesnite da moje to da se refreshne
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }
    public void setFields(String name,String author,String duration,String year,String genre)
    {
        eName.setText(name);
        eAuthor.setText(author);
        eDuration.setText(secsToMin(duration));
        eYear.setText(year);
        eGenre.setText(genre);
    }
    public String secsToMin(String secs)//funkciq koqto obryshta produjitelnostta ot format samo sus sekundi na format s minuti i sekundi
    {
        int mins=Integer.parseInt(secs)/60;
        int sec=Integer.parseInt(secs)%60;
        return ""+mins+":"+sec;
    }
    public String minsToSecs(String mins)//funkciq koqto obrashta ot format minuti i sekundi w format samo sekundi(za zapis w bazata)
    {
        String[] units = mins.split(":");
        return Integer.toString(Integer.parseInt(units[0])*60+Integer.parseInt(units[1]));
    }

}
