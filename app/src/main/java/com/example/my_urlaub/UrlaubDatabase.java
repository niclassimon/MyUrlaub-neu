package com.example.my_urlaub.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.my_urlaub.Urlaub;

@Database(entities = {Urlaub.class}, version = 1)
@TypeConverters({com.example.my_urlaub.room.UrlaubAttributesConverter.class})

public abstract class UrlaubDatabase extends RoomDatabase{
    public abstract com.example.my_urlaub.room.UrlaubDAO urlaubDAO();
}
