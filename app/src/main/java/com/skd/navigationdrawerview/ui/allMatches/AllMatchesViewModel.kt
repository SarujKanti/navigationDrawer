package com.skd.navigationdrawerview.ui.allMatches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.skd.navigationdrawerview.data.remote.Venue
import com.skd.navigationdrawerview.data.repository.MatchRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AllMatchesViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repo = MatchRepository(application)

    private val _loading = MediatorLiveData<Boolean>()
    val loading = _loading

    val allMatches = liveData {
        _loading.postValue(true)
        val apiMatches = repo.fetchMatches()
        emit(apiMatches)
        _loading.postValue(false)
    }

    val savedIds = repo.getSavedMatches()
        .map { list -> list.map { it.id }.toSet() }
        .asLiveData()

    val visibleMatches = MediatorLiveData<List<Venue>>()

    init {
        var apiList: List<Venue> = emptyList()
        var savedSet: Set<String> = emptySet()

        fun update() {
            visibleMatches.value =
                apiList.filterNot { savedSet.contains(it.id) }
        }

        visibleMatches.addSource(allMatches) {
            apiList = it
            update()
        }

        visibleMatches.addSource(savedIds) {
            savedSet = it
            update()
        }
    }

    fun saveMatch(venue: Venue) = viewModelScope.launch {
        repo.saveMatch(venue)
    }
}




