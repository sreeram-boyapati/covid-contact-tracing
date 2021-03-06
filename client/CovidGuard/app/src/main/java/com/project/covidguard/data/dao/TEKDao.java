package com.project.covidguard.data.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import androidx.room.Update;

import com.project.covidguard.data.entities.RPI;
import com.project.covidguard.data.entities.TEK;

import java.util.List;

@Dao
public interface  TEKDao {

    @Insert
    void insert(TEK tek);

    /**
     * Fetch the TEK corresponding to the ENIntervalNumber
     * @param ENIntervalNumber
     * @return
     */
    @Query("SELECT * FROM teks where en_interval_number = :ENIntervalNumber")
    TEK fetchByENInterval(Long ENIntervalNumber);

    @Query("SELECT * FROM teks where created_at >= :timestamp")
    List<TEK> getTEKFromTimeStamp(Long timestamp);

    @Query("SELECT * FROM teks where created_at >= :from and created_at < :to")
    List<TEK> getTEKFromTimeStampSync(Long from, Long to);

    /**
     * Get the last TEK stored in the database
     * @return TEK - the last tek by created_at
     */
    @Query("SELECT * FROM teks ORDER BY created_at DESC LIMIT 1")
    TEK getLastTEK();

    /**
     * DELETE data from the database based on the timestamp
     */
    @Query("DELETE FROM teks where created_at < :timestamp")
    void deleteBeforeTimeStamp(Long timestamp);
}
