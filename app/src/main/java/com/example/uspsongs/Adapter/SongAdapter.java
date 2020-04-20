package com.example.uspsongs.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uspsongs.AddAndUpdate;
import com.example.uspsongs.MainActivity;
import com.example.uspsongs.R;
import com.example.uspsongs.holder.SongViewHolder;
import com.example.uspsongs.model.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongViewHolder> implements Filterable {
    private ArrayList<Song> songs;
    private ArrayList<Song> songsFull;//towa e za da zapazim wsichki pesni kogato filtrirame
    private Activity activity;//dobaweno za da moje da se preprashta kum activityto za update i delete

    public SongAdapter(ArrayList<Song> songs,Activity activity) {
        this.songs = songs;
        songsFull=new ArrayList<>(songs);
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
            holder.settDuration(Integer.toString(song.getDuration()/60)+":"+Integer.toString(song.getDuration()%60));
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
        songsFull.add(0,song);
        notifyItemInserted(0);
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Song> filteredList=new ArrayList<>();
            if(constraint.toString().isEmpty())
            {
                filteredList.addAll(songsFull);
            }
            else
            {
                String filterPatern=constraint.toString().toLowerCase().trim();//wzema poleto i mu maha wsichko koeto bi ni prechilo
                for(Song song1: songsFull)
                {   //proverka ako nqkoe ot poletata ime,avtor,ili janr sudurjat constraint
                    if(song1.getName().toLowerCase().contains(filterPatern)||song1.getAuthor().toLowerCase().contains(filterPatern)||song1.getGenre().toLowerCase().contains(filterPatern))
                    {
                        filteredList.add(song1);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            songs.clear();
            //songs.addAll((Collection<? extends Song>) results);
            songs.addAll((ArrayList<Song>) results.values);
            notifyDataSetChanged();
        }
    };
    public void songSort(final int orderBy)
    {
        //orderBy stoinosti : year ascending=1; year descending=2; duration ascending=3; duration descending=4
        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song o1, Song o2) {
                switch (orderBy){
                    case 1:return Integer.toString(o1.getYear()).compareTo(Integer.toString(o2.getYear()));
                    case 2:return Integer.toString(o2.getYear()).compareTo(Integer.toString(o1.getYear()));
                    case 3:return Integer.toString(o1.getDuration()).compareTo(Integer.toString(o2.getDuration()));
                    case 4:return Integer.toString(o2.getDuration()).compareTo(Integer.toString(o1.getDuration()));


                }
                return 0;
            }
        });
        notifyDataSetChanged();
    }
}
