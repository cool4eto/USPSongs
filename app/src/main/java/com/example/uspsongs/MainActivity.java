package com.example.uspsongs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.uspsongs.Adapter.SongAdapter;
import com.example.uspsongs.data.SongSource;
import com.example.uspsongs.model.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;

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
        //adapter.songSort(1);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serch_menu,menu);
        MenuItem item=menu.findItem(R.id.song_search);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.yearAscending:adapter.songSort(1);break;
            case R.id.yearDescending:adapter.songSort(2);break;
            case R.id.durationAscending:adapter.songSort(3);break;
            case R.id.durationDescending:adapter.songSort(4);break;
        }
        return true;
    }
}
