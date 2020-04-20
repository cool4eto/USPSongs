package com.example.uspsongs.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private int Id;
    private String name;
    private String author;
    private int duration;
    private int year;
    private String genre;

    public Song(int id, String name, String author, int duration, int year, String genre) {
        Id = id;
        this.name = name;
        this.author = author;
        this.duration = duration;
        this.year = year;
        this.genre = genre;
    }

    protected Song(Parcel in) {
        Id = in.readInt();
        name = in.readString();
        author = in.readString();
        duration = in.readInt();
        year = in.readInt();
        genre = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Song() {
    }

    @Override
    public String toString() {
        return "Song{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", duration=" + duration +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                '}';
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(name);
        dest.writeString(author);
        dest.writeInt(duration);
        dest.writeInt(year);
        dest.writeString(genre);
    }


}
