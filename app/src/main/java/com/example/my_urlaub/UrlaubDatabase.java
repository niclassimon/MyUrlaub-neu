package com.example.my_urlaub.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.my_urlaub.Urlaub;

@Database(entities = {Urlaub.class}, version = 1)
@TypeConverters({UrlaubAttributesConverter.class})

public abstract class UrlaubDatabase extends RoomDatabase{
    public abstract UrlaubDAO urlaubDAO();
}
