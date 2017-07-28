package com.melee.jukes.a20sapos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Integer playerNum = 4;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        final SharedPreferences settings = getSharedPreferences("savedGame", 0);
        boolean inGame = settings.getBoolean("inGame",false);
        if(inGame){
            startActivity(new Intent(MainActivity.this,InGameActivity.class));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner playerNumSpinner = (Spinner) findViewById(R.id.playerNumSpinner);
        final Button button = (Button) findViewById(R.id.playerNumButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                playerNum = playerNumSpinner.getSelectedItemPosition() + 2;
                GameStatus status = new GameStatus();
                for(int i = 0;i<playerNum;i++){
                    Player p = new Player(i);
                    status.addPlayer(p);
                }
                String[] chars = getResources().getStringArray(R.array.characterArray);
                status.addCharacters(chars);
                SharedPreferences.Editor prefsEditor = settings.edit();
                Gson gson = new Gson();
                String json = gson.toJson(status);
                prefsEditor.putString("GameStatus", json);
                prefsEditor.putInt("playerNum",0);
                prefsEditor.putInt("playerMax",playerNum);
                prefsEditor.commit();
                startActivity(new Intent(MainActivity.this,PlayersActivity.class));
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        playerNum = position + 2;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
