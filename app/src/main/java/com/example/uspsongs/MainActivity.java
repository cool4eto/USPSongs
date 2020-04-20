package com.example.uspsongs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.example.uspsongs.Adapter.SongAdapter;
import com.example.uspsongs.data.SongSource;
import com.example.uspsongs.model.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SongAdapter adapter;
    DatabaseHelper songDb;

    SongSource songs = new SongSource();

    private FloatingActionButton bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new SongAdapter(songs.getSongs(),MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songDb=new DatabaseHelper(this);
        bt=findViewById(R.id.floatingActionButton);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddAndUpdate.class);
                startActivity(i);

            }
        });
        fillRecyclerView();
    }
    public void fillRecyclerView()
    {
        Cursor res = songDb.getAllSongs();
        if(res.getCount()==0)
        {
            return;
        }
        else
        {
            while (res.moveToNext())
            {
                Song song1=new Song();
                song1.setId(res.getInt(0));
                song1.setName(res.getString(1));
                song1.setAuthor(res.getString(2));
                song1.setDuration(res.getInt(3));
                song1.setYear(res.getInt(4));
                song1.setGenre(res.getString(5));
                adapter.addSong(song1);
            }
        }
    }
}
