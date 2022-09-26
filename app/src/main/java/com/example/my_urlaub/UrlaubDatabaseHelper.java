package com.example.my_urlaub.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import com.example.my_urlaub.Urlaub;
import java.util.ArrayList;
import com.example.my_urlaub.room.UrlaubDatabase;

public class UrlaubDatabaseHelper {

    private static final String DATABASE_NAME = "urlaube-db";
    private final Context context;
    private  UrlaubDatabase db;

    public UrlaubDatabaseHelper(Context context){
        this.context = context;
        initDatabase();
    }

    private void initDatabase(){
        db = Room.databaseBuilder(context, UrlaubDatabase.class, DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public void addUrlaub(Urlaub urlaub) {
        db.urlaubDAO().insertUrlaub(urlaub);
    }

    public void updateUrlaub(Urlaub urlaub) {
        db.urlaubDAO().updateUrlaub(urlaub);
    }

    public ArrayList<Urlaub> getAllUrlaube(){
        return new ArrayList<Urlaub>(db.urlaubDAO().getAllUrlaube());
    }

}

