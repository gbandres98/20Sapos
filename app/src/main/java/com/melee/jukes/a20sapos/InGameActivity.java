package com.melee.jukes.a20sapos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;

/**
 * Created by WowSuchJukes on 27/07/2017.
 */

public class InGameActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingame);
        final SharedPreferences settings = getSharedPreferences("savedGame", 0);
        Gson gson = new Gson();
        String json = settings.getString("GameStatus", "");
        GameStatus status = gson.fromJson(json, GameStatus.class);
        if (status.playersDead.size() == status.players.size()-1){
            startActivity(new Intent(InGameActivity.this, EndGame1Activity.class));
            /*settings.edit().putBoolean("InGame",false);*/
        }
        if (status.playersDead.size() == status.players.size()){
            //TODO
        }
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);
        for (String s : status.charactersAlive) {
            Button b1 = new Button(this);
            b1.setText(s);
            b1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(b1);
            b1.setOnClickListener(this);
        }
        Button end = new Button(this);
        end.setText("Terminar partida");
        end.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        end.setOnClickListener(this);
    }
    public void onClick(View v) {
        Button b = (Button) v;
        if (b.getText().toString() == "Terminar partida"){

        }else {
            final SharedPreferences settings1 = getSharedPreferences("savedGame", 0);
            Gson gson = new Gson();
            String json = settings1.getString("GameStatus", "");
            GameStatus status = gson.fromJson(json, GameStatus.class);
            status.killCharacter(b.getText().toString());
            SharedPreferences.Editor prefsEditor = settings1.edit();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(status);
            prefsEditor.putString("GameStatus", json1);
            prefsEditor.commit();
            startActivity(new Intent(InGameActivity.this, InGameActivity.class));
        }
    }
}
