package info.androidhive.retrofit.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={FavoriteList.class},version = 1 , exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase{

    public abstract FavoriteDao favoriteDao();


}
