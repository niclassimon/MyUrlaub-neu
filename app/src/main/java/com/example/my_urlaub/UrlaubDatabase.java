package com.example.my_urlaub;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Urlaub.class}, version = 1)
@TypeConverters({com.example.my_urlaub.UrlaubAttributesConverter.class})

public abstract class UrlaubDatabase extends RoomDatabase{
    public abstract com.example.my_urlaub.UrlaubDAO urlaubDAO();
}
