package com.example.uspsongs.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uspsongs.R;

public class SongViewHolder extends RecyclerView.ViewHolder {

    TextView tName;
    TextView tAuthor;
    TextView tDuration;
    TextView tYear;
    TextView tGenre;
    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        tName=itemView.findViewById(R.id.textView7);
        tAuthor=itemView.findViewById(R.id.textView8);
        tDuration=itemView.findViewById(R.id.textView9);
        tYear=itemView.findViewById(R.id.textView10);
        tGenre=itemView.findViewById(R.id.textView11);
    }

    public void settName(String name) {//ne e avtomatichno generirano
        this.tName.setText(name);
    }
    public void settAuthor(String author) {//ne e avtomatichno generirano
        this.tAuthor.setText(author);
    }
    public void settDuration(String duration) {//ne e avtomatichno generirano
        this.tDuration.setText(duration);
    }
    public void settYear(String year) {//ne e avtomatichno generirano
        this.tYear.setText(year);
    }
    public void settGenre(String genre) { this.tGenre.setText(genre);}
}
