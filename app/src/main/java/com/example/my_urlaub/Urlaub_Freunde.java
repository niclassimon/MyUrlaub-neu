package com.example.my_urlaub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class Urlaub_Freunde extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button neuerUrlaub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urlaub_freunde);


        addVocation();
    }

    private void addVocation() {
        neuerUrlaub = findViewById(R.id.neuerUrlaub1);
        neuerUrlaub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Urlaub_Freunde.this, NeuerUrlaub.class);
                startActivity(intent);
            }
        });
    }
}