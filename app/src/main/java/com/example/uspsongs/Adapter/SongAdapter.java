package com.example.uspsongs.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uspsongs.AddAndUpdate;
import com.example.uspsongs.MainActivity;
import com.example.uspsongs.R;
import com.example.uspsongs.holder.SongViewHolder;
import com.example.uspsongs.model.Song;

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> {
    private ArrayList<Song> songs;
    private Activity activity;//dobaweno za da moje da se preprashta kum activityto za update i delete
    public SongAdapter(ArrayList<Song> songs,Activity activity) {
        this.songs = songs;
        this.activity=activity;
    }

    public SongAdapter() {
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View songView = inflater.inflate(R.layout.song_item, parent, false);

        return new SongViewHolder(songView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
            final Song song=songs.get(position);
            holder.settName(song.getName());
            holder.settAuthor(song.getAuthor());
            holder.settDuration(Integer.toString(song.getDuration()));
            holder.settYear(Integer.toString(song.getYear()));
            holder.settGenre(song.getGenre());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(activity,AddAndUpdate.class);
                i.putExtra("songToChange",song);
                activity.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }
    public void addSong(Song song)
    {
        songs.add(0,song);
        notifyItemInserted(0);
    }
}
