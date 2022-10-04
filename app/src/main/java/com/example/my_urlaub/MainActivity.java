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
import android.widget.Toast;

import com.example.my_urlaub.UrlaubDatabaseHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements Recycler_view_Interface{
    DrawerLayout drawerLayout;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Button neuerUrlaub;
    ArrayList<Urlaub> UrlaubModelList = new ArrayList<Urlaub>();
    private UrlaubDatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        drawerLayout = findViewById(R.id.drawer_layout);

        addVocation();
        addVocationToArrayList();
    }

    //Hier wird der neue Urlaub in die Array-List für alle Urlaube hinzugefügt
    public void addVocationToArrayList(){
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
                Urlaub urlaub = new Urlaub(location,startDate,endDate,description,imgSrc);
                UrlaubModelList.add(urlaub);

                recyclerView();
            }
        });
    }

    //Hier wird der RecyclerView inizialisisert und mit dem Adapter verbunden
    public void recyclerView(){
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setHasFixedSize(false);
        Urlaub_recycler_View_Adapter adapter = new Urlaub_recycler_View_Adapter(this, UrlaubModelList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();
    }

    //Die Funktion ermöglicht es mit einem Button die NeuerUrlaub-Activity zu öffnen
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

    // Um den kompletten Urlaub sehen zu können, wird eine neue Activity gestarten. Dabei werden die Daten aus der NeuerUrlaub-Activity benötigt.
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, OnClickUrlaub.class );
        Urlaub urlaub = UrlaubModelList.get(position);
        String description = urlaub.getDescription();
        String location = urlaub.getLocation();
        String startDate = urlaub.getStartDate();
        String endDate = urlaub.getEndDate();
        intent.putExtra("startDate",startDate);
        intent.putExtra("endDate", endDate);
        intent.putExtra("location", location);
        intent.putExtra("description", description);

        startActivity(intent);
    }

}
