package com.davidwidjaya.moxvie.data.local.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.davidwidjaya.moxvie.data.local.entity.Movie;
import com.davidwidjaya.moxvie.data.local.entity.Tv;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMovie(Movie movie);

    @Delete()
    void deleteMovie(Movie movie);

    @Query("SELECT * from Movie")
    List<Movie> getAllFavoritedMovie();

    @Query("SELECT * from Movie where id = :id")
    Movie getFavoritedMovieById(int id);


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Tv tv);

    @Delete()
    void delete(Tv tv);

    @Query("SELECT * from Tv")
    List<Tv> getAllFavoritedTV();

    @Query("SELECT * from Tv where id = :id")
    Tv getFavoritedTVById(String id);
}
