package com.melee.jukes.a20sapos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.google.gson.Gson;

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
                String[] chars = getResources().getStringArray(R.array.characterArrayPM);
                status.addCharacters(chars);
                for(int i = 0;i<playerNum;i++){
                    Player p = new Player(i);
                    status.addPlayer(p);
                }
                SharedPreferences.Editor prefsEditor = settings.edit();
                Gson gson = new Gson();
                String json = gson.toJson(status);
                prefsEditor.putString("GameStatus", json);
                prefsEditor.putInt("playerNum",0);
                prefsEditor.putInt("playerMax",playerNum);
                prefsEditor.putString("game", "PM");
                prefsEditor.commit();
                startActivity(new Intent(MainActivity.this, BansActivity.class));
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
