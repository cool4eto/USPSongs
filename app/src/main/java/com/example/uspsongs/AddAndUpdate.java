package com.example.uspsongs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uspsongs.model.Song;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_update);
        songDb=new DatabaseHelper(this);
        eName=findViewById(R.id.editText);
        eAuthor=findViewById(R.id.editText3);
        eDuration=findViewById(R.id.editText4);
        eYear=findViewById(R.id.editText5);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//za da imame butonche za wrashrane w glavnata stranica dobavihme i v manifest stranicata :  android:parentActivityName=".MainActivity"


        setFields("It's my life","Bon Jovi","266","2000","pop rock");
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
        boolean isInserted=songDb.insertSong(eName.getText().toString(),eAuthor.getText().toString(),eDuration.getText().toString(),eYear.getText().toString(),eGenre.getText().toString());
        if(isInserted) {
            Toast.makeText(AddAndUpdate.this, "Song Inserted", Toast.LENGTH_LONG).show();
        }
            else {
            Toast.makeText(AddAndUpdate.this, "Song NOT Inserted", Toast.LENGTH_LONG).show();
        }
    }
    public void UpdateSong()
    {
        boolean isUpdated=songDb.updateSong(Integer.toString(songToChange.getId()),eName.getText().toString(),eAuthor.getText().toString(),eDuration.getText().toString(),eYear.getText().toString(),eGenre.getText().toString());
        if(isUpdated)
        {
            Toast.makeText(AddAndUpdate.this, "Song Updated", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(AddAndUpdate.this, "Song NOT Updated", Toast.LENGTH_LONG).show();
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
        eDuration.setText(duration);
        eYear.setText(year);
        eGenre.setText(genre);
    }

}
