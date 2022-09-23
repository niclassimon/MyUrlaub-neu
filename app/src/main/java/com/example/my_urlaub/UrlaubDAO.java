package com.example.my_urlaub.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.my_urlaub.Urlaub;

import java.util.List;

@Dao
public interface UrlaubDAO {

    @Insert
    void insertUrlaub (Urlaub urlaub);

    @Update
    void updateUrlaub (Urlaub urlaub);

    @Query("SELECT * FROM urlaub_table")
    List<Urlaub> getAllUrlaube();
}
