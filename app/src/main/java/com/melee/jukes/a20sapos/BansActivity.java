package com.melee.jukes.a20sapos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WowSuchJukes on 28/07/2017.
 */

public class BansActivity extends AppCompatActivity implements View.OnClickListener {
    static int selected = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bans);
        final SharedPreferences settings = getSharedPreferences("savedGame", 0);
        Gson gson = new Gson();
        String json = settings.getString("GameStatus", "");
        GameStatus status = gson.fromJson(json, GameStatus.class);
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout4);
        Button bb = new Button(this);
        bb.setText("Comenzar");
        bb.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        bb.setOnClickListener(this);
        Button randomizeButton = new Button(this);
        randomizeButton.setText("Bans aleatorios");
        randomizeButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        randomizeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                randomBans(settings);
        }
        });
        layout.addView(bb);
        layout.addView(randomizeButton);
        if (settings.getString("game", "error").equals("PM")) {
            Button b = new Button(this);
            b.setText("Cambiar a Melee");
            b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            b.setOnClickListener(this);
            layout.addView(b);
        } else {
            Button b = new Button(this);
            b.setText("Cambiar a PM");
            b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            b.setOnClickListener(this);
            layout.addView(b);
        }
        for (String s : status.characters) {
            Button b1 = new Button(this);
            b1.setText(s);
            b1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            layout.addView(b1);
            b1.setOnClickListener(this);
        }
    }

    private void randomBans(SharedPreferences settings) {
        List<String> chars;
        Gson gson = new Gson();
        String json = settings.getString("GameStatus", "");
        GameStatus status = gson.fromJson(json, GameStatus.class);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Hay " + status.charactersAlive.size() + " personajes, ¿cuántos quieres banear?");
        chars = status.characters;
        List<String> items = new ArrayList<>();
        for (int i = 0; i < chars.size(); i++)
            items.add(String.valueOf(i));
        selected = 0;
        String[] itemsArray = new String[items.size()];
        items.toArray(itemsArray);
        try {
            alert.setSingleChoiceItems(itemsArray, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selected = which;
                }
            });

        } catch (Exception e){
            e.printStackTrace();
        }
        alert.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final SharedPreferences settings = getSharedPreferences("savedGame", 0);
                Gson gson = new Gson();
                String json = settings.getString("GameStatus", "");
                GameStatus status = gson.fromJson(json, GameStatus.class);
                status.removeRandomCharacters(selected);
                SharedPreferences.Editor prefsEditor = settings.edit();
                json = gson.toJson(status);
                prefsEditor.putString("GameStatus", json);
                prefsEditor.commit();
                startActivity(new Intent(BansActivity.this, BansActivity.class));
            }
        });
        alert.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        final SharedPreferences settings = getSharedPreferences("savedGame", 0);
        Gson gson = new Gson();
        String json = settings.getString("GameStatus", "");
        GameStatus status = gson.fromJson(json, GameStatus.class);
        Button b = (Button) v;
        if (b.getText().toString().equals("Comenzar")) {
            startActivity(new Intent(BansActivity.this, PlayersActivity.class));
        } else if (b.getText().toString().equals("Cambiar a Melee")) {
            status.clearCharacters();
            String[] chars = getResources().getStringArray(R.array.characterArrayMelee);
            status.addCharacters(chars);
            SharedPreferences.Editor prefsEditor = settings.edit();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(status);
            prefsEditor.putString("game", "melee");
            prefsEditor.putString("GameStatus", json1);
            prefsEditor.commit();
            startActivity(new Intent(BansActivity.this, BansActivity.class));
        } else if (b.getText().toString().equals("Cambiar a PM")) {
            status.clearCharacters();
            String[] chars = getResources().getStringArray(R.array.characterArrayPM);
            status.addCharacters(chars);
            SharedPreferences.Editor prefsEditor = settings.edit();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(status);
            prefsEditor.putString("game", "PM");
            prefsEditor.putString("GameStatus", json1);
            prefsEditor.commit();
            startActivity(new Intent(BansActivity.this, BansActivity.class));
        } else {
            status.removeCharacter(b.getText().toString());
            SharedPreferences.Editor prefsEditor = settings.edit();
            json = gson.toJson(status);
            prefsEditor.putString("GameStatus", json);
            prefsEditor.commit();
            startActivity(new Intent(BansActivity.this, BansActivity.class));
        }

    }
}
