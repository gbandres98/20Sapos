package com.melee.jukes.a20sapos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by WowSuchJukes on 27/07/2017.
 */

public class PlayersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        final SharedPreferences settings = getSharedPreferences("savedGame", 0);
        int playerMax = settings.getInt("playerMax",4);
        final int playerNum = settings.getInt("playerNum",0);
        if (playerNum == playerMax){
            settings.edit().putBoolean("InGame",true);
            startActivity(new Intent(PlayersActivity.this,InGameActivity.class));
        }
        Integer texto = playerNum + 1;
        TextView text = (TextView) findViewById(R.id.TextView2);
        text.setText("Jugador "+texto.toString()+":");
        final EditText nombre = (EditText) findViewById(R.id.editName);
        final Spinner spinner = (Spinner) findViewById(R.id.playerCharSpinner);
        final Button button = (Button) findViewById(R.id.playerCharButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Gson gson = new Gson();
                String json = settings.getString("GameStatus", "");
                GameStatus status = gson.fromJson(json, GameStatus.class);
                status.players.get(playerNum).setName(nombre.getText().toString());
                status.players.get(playerNum).setCharacter(spinner.getSelectedItem().toString());
                status.playersAlive.get(playerNum).setName(nombre.getText().toString());
                status.playersAlive.get(playerNum).setCharacter(spinner.getSelectedItem().toString());
                SharedPreferences.Editor prefsEditor = settings.edit();
                Gson gson1 = new Gson();
                String json1 = gson1.toJson(status);
                prefsEditor.putString("GameStatus", json1);
                prefsEditor.putInt("playerNum",playerNum+1);
                prefsEditor.commit();
                startActivity(new Intent(PlayersActivity.this,PlayersActivity.class));
            }
        });
    }
}
