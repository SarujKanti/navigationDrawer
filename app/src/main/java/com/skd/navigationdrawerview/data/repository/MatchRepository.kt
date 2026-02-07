package com.skd.navigationdrawerview.data.repository

import android.content.Context
import com.skd.navigationdrawerview.data.local.AppDatabase
import com.skd.navigationdrawerview.data.local.MatchEntity
import com.skd.navigationdrawerview.data.remote.Venue
import com.skd.navigationdrawerview.utils.RetrofitBuilder
import kotlinx.coroutines.flow.Flow

class MatchRepository(context: Context) {

    private val dao = AppDatabase.get(context).matchDao()
    private val api = RetrofitBuilder.apiService

    suspend fun fetchMatches(): List<Venue> =
        api.getMatches().response.venues

    fun getSavedMatches(): Flow<List<MatchEntity>> =
        dao.getSavedMatches()

    suspend fun saveMatch(venue: Venue) {
        dao.insert(MatchEntity(venue.id, venue.name, venue.location.country))
    }

    suspend fun deleteMatch(id: String) {
        dao.deleteById(id)
    }

    suspend fun isSaved(id: String): Boolean =
        dao.isSaved(id)
}
