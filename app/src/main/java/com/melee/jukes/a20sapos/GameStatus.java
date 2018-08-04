package com.melee.jukes.a20sapos;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by WowSuchJukes on 27/07/2017.
 */

public class GameStatus {
    public List<Player> players;
    public List<String> characters;
    public List<String> charactersAlive;
    public List<String> history;
    public List<Player> playersDead;
    public List<Player> playersAlive;
    Context mContext;
    public GameStatus(){
        this.players = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.charactersAlive = new ArrayList<>();
        this.history = new ArrayList<>();
        this.playersDead = new ArrayList<>();
        this.playersAlive = new ArrayList<>();
    }
    public Boolean inProgress(){
        Boolean res = false;
        String cha = null;
        for(Player p : this.players){
            if (p.alive == 1) {
                if (cha == null) {
                    cha = p.character;
                } else {
                    if (!cha.equals(p.character)) {
                        res = true;
                        break;
                    }
                }
            }
        }
        return res;
    }
    public void addPlayer(Player player){
        this.playersAlive.add(player);
        this.players.add(player);
    }
    public void addCharacters(String[] c){
        List<String> temp = Arrays.asList(c);
        this.characters.addAll(temp);
        this.charactersAlive.addAll(temp);
    }

    public void removeCharacter(String c) {
        this.characters.remove(c);
        this.charactersAlive.remove(c);
    }

    public void removeRandomCharacters(Integer i) {
        for (int j = 0; j < i; j++) {
            Random rand = new Random();
            int remove = rand.nextInt(this.characters.size());
            this.characters.remove(remove);
            this.charactersAlive.remove(remove);
        }
    }

    public void clearCharacters() {
        this.characters = new ArrayList<>();
        this.charactersAlive = new ArrayList<>();
    }
    public void killCharacter(String c){
        charactersAlive.remove(c);
        history.add(c+" ganó.");
        for (Player p : players){
            if (p.character.equals(c)){
                p.rip();
                playersDead.add(p);
                history.add("¡"+p.name+" fue eliminado!");
            }
        }
    }

    /*    public GameStatus(Parcel p){
        this.players = new ArrayList<>();
        this.characters = new ArrayList<>();
        this.charactersAlive = new ArrayList<>();
        readFromParcel(p);
    }

    public static final Creator<GameStatus> CREATOR = new Creator<GameStatus>() {
        @Override
        public GameStatus createFromParcel(Parcel in) {
            return new GameStatus(in);
        }

        @Override
        public GameStatus[] newArray(int size) {
            return new GameStatus[size];
        }
    };*/

/*    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(players);
        dest.writeTypedList(characters);
        dest.writeTypedList(charactersAlive);
    }

    private void readFromParcel(Parcel in) {
        this.players = in.readTypedList(players,Player.CREATOR);
    }*/
}
