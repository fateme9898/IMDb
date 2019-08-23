package info.androidhive.retrofit.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities={FavoriteList.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase{

    public abstract FavoriteDao favoriteDao();


}
