package com.example.my_urlaub;

import androidx.room.TypeConverter;
import java.util.UUID;

public class UrlaubAttributesConverter {
    @TypeConverter
    public static UUID stringToUUID(String urlaubID_string){
        return urlaubID_string == null ? null : UUID.fromString(urlaubID_string);
    }

    @TypeConverter
    public static String UUID_toString(UUID urlaubID){
        return urlaubID == null ? null : urlaubID.toString();
    }
}

