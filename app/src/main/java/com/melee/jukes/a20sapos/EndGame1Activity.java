package com.melee.jukes.a20sapos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

/**
 * Created by WowSuchJukes on 28/07/2017.
 */

public class EndGame1Activity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame1);
        final SharedPreferences settings = getSharedPreferences("savedGame", 0);
        settings.edit().putBoolean("InGame",false);
        Gson gson = new Gson();
        String json = settings.getString("GameStatus", "");
        GameStatus status = gson.fromJson(json, GameStatus.class);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout2);
        for (Player p : status.players){
            if(p.alive == 1){
                TextView text = new TextView(this);
                text.setText(p.name+" ("+p.character+")");
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(text);
            }
        }
        Button b1 = new Button(this);
        b1.setText("Nueva partida");

    }

    @Override
    public void onClick(View v) {

    }
}
