package com.example.my_urlaub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OnClickUrlaub extends AppCompatActivity {

    TextView Location;
    TextView Description;
    TextView StartDate;
    TextView EndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_urlaub);
        setUpUI();
    }

    //Hier werden alle benötigten Views (um einen Urlaub vollständig anzeigen zu können) inizialisiert und mit den dazugehörigen Werten besetzt
    public void setUpUI(){
        Intent intent = getIntent();

        String location = intent.getStringExtra("location");
        String desciption = intent.getStringExtra("description");
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");

        Location  = findViewById(R.id.location);
        Description = findViewById(R.id.description);
        StartDate = findViewById(R.id.startDate);
        EndDate = findViewById(R.id.endDate);

        StartDate.setText(startDate);
        EndDate.setText(endDate);
        Location.setText(location);
        Description.setText(desciption);
    }
}