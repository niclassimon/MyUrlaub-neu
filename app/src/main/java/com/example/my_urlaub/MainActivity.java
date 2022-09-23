package com.example.my_urlaub;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Button neuerUrlaub;
    ArrayList<Urlaub> UrlaubModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        addVocation();

        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        setUpUrlaubModels();
        Urlaub_recycler_View_Adapter adapter = new Urlaub_recycler_View_Adapter(this, UrlaubModelList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpUrlaubModels(){
        String [] urlaubDescription = getResources().getStringArray(R.array.description);
        String [] urlaubLocation = getResources().getStringArray(R.array.location);
        String [] urlaubStartDate = getResources().getStringArray(R.array.startDate);
        String [] urlaubEndDate = getResources().getStringArray(R.array.endDate);

        for (int i = 0; i < urlaubDescription.length; i++) {
            UrlaubModelList.add(new Urlaub(urlaubLocation[i], urlaubDescription[i], urlaubStartDate[i], urlaubEndDate[i]));
        }
    }

    private void addVocation() {
        neuerUrlaub = findViewById(R.id.neuerUrlaub);
        neuerUrlaub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NeuerUrlaub.class);
                startActivity(intent);
            }
        });
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickNewVocation(View view){
        redirectActivity(this, NeuerUrlaub.class);
    }

    public void ClickFriends(View view){
        //redirectActivity(this, Urlaub_Freunde.class);
        Intent intent = new Intent(MainActivity.this, Urlaub_Freunde.class);
        startActivity(intent);
    }

    public void ClickCalender(View view){
        redirectActivity(this, Kalender.class);
    }

    public void ClickLogout(View view){
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder((activity));

        builder.setTitle("Logout");

        builder.setMessage("Are you sure you want to logout ?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        //Set flag
        intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
        //Start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}
