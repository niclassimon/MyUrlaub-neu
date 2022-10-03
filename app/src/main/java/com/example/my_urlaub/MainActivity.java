package com.example.my_urlaub;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.my_urlaub.UrlaubDatabaseHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Recycler_view_Interface, DrawerLayout.DrawerListener, NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Button neuerUrlaub;
    ArrayList<Urlaub> UrlaubModelList = new ArrayList<Urlaub>();
    private UrlaubDatabaseHelper db;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);

        addVocation();
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                addVocation();
                Intent i = result.getData();
                String startDate = i.getStringExtra("startDate");
                String endDate = i.getStringExtra("endDate");
                String location = i.getStringExtra("location");
                String description = i.getStringExtra("description");
                String imgSrc = i.getStringExtra("imgSrc");
                //Toast.makeText(MainActivity.this,i.getStringExtra("location"),Toast.LENGTH_LONG).show();
                Urlaub urlaub = new Urlaub(location,startDate,endDate,description,imgSrc);
                UrlaubModelList.add(urlaub);

                recyclerView();
            }
        });
    }

    public void recyclerView(){
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        Urlaub_recycler_View_Adapter adapter = new Urlaub_recycler_View_Adapter(this, UrlaubModelList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    private void setUpUrlaubModels(){
        addVocation();

        Intent intent = getIntent();
        String startDate = intent.getStringExtra("startDate");
        String endDate = intent.getStringExtra("endDate");
        String location = intent.getStringExtra("location");
        String description = intent.getStringExtra("description");
        String imgSrc = intent.getStringExtra("imgSrc");

        Urlaub urlaub = new Urlaub(location,startDate,endDate,description,imgSrc);
        UrlaubModelList.add(new Urlaub("USA", "12.04.67", "13.05.67", "war cool!", ""));
        UrlaubModelList.add(urlaub);

        //loadUrlaubList(urlaub);
        //for (int i = 0; i < urlaubDescription.length; i++) {
        //    UrlaubModelList.add(new Urlaub(urlaubLocation[i], urlaubDescription[i], urlaubStartDate[i], urlaubEndDate[i]));
        //}
    }

    private void addVocation() {
        neuerUrlaub = findViewById(R.id.neuerUrlaub);
        neuerUrlaub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NeuerUrlaub.class);
                activityResultLauncher.launch(intent);
            }
        });
    }


    private void loadUrlaubList(Urlaub urlaub) {
        db = new UrlaubDatabaseHelper(MainActivity.this);
        db.addUrlaub(urlaub);
        db.getAllUrlaube();
    }

    public void ClickNewVocation(View view){
        redirectActivity(this, NeuerUrlaub.class);

    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickFriends(View view){
        //redirectActivity(this, Urlaub_Freunde.class);
        Intent intent = new Intent(MainActivity.this, Urlaub_Freunde.class);
        startActivity(intent);
    }

    public void ClickCalender(View view){
        redirectActivity(MainActivity.this, Kalender.class);
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
        //intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));
        //Start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = getIntent();

        //String endDate = intent.getStringExtra("endDate");

        Intent intent1 = new Intent(MainActivity.this, OnClickUrlaub.class );
        Urlaub urlaub = UrlaubModelList.get(position);
        String description = urlaub.getDescription();
        String location = urlaub.getLocation();
        String startDate = urlaub.getStartDate();
        String endDate = urlaub.getEndDate();
        intent1.putExtra("startDate",startDate);
        intent1.putExtra("endDate", endDate);
        intent1.putExtra("location", location);
        intent1.putExtra("description", description);

        startActivity(intent1);
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {
        invalidateOptionsMenu();
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
