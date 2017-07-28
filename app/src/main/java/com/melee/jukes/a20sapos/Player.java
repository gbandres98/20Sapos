package com.melee.jukes.a20sapos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by WowSuchJukes on 27/07/2017.
 */

public class Player {
    public int id;
    public String name;
    public String character;
    public int alive;

    public Player(int id){
        this.id = id;
        this.name = null;
        this.character = null;
        this.alive = 1;
    }
    public void setName (String name){
        this.name = name;
    }
    public void setCharacter (String c){
        this.character = c;
    }
    public void rip(){
        this.alive = 0;
    }
/*
    protected Player(Parcel in) {
        id = in.readInt();
        name = in.readString();
        character = in.readParcelable(SmashCharacter.class.getClassLoader());
        alive = in.readInt();
    }*/

/*    public static final Creator<Player> CREATOR = new Creator<Player>() {
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        public Player[] newArray(int size) {
            return new Player[size];
        }
    };*/
/*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeParcelable(character,flags);
        dest.writeInt(alive);
    }*/
}
