/*
 Copyright (c) 2020 Mustafa Ozhan. All rights reserved.
 */
package com.github.mustafaozhan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.mustafaozhan.data.model.Rates

@Dao
interface OfflineRatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOfflineRates(rates: Rates)

    @Query("SELECT * FROM offline_rates WHERE base=:base")
    suspend fun getOfflineRatesByBase(base: String): Rates?
}
