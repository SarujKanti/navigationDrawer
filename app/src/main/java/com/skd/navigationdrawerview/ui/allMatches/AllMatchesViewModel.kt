package com.skd.navigationdrawerview.ui.allMatches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.skd.navigationdrawerview.data.remote.Venue
import com.skd.navigationdrawerview.data.repository.MatchRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class AllMatchesViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repo = MatchRepository(application)
    private var currentList: List<Venue> = emptyList()
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    val allMatches = liveData {
        _loading.postValue(true)
        emit(repo.fetchMatches())
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
            val updated = currentList.map { venue ->
                venue.copy(isSaved = savedSet.contains(venue.id))
            }
            visibleMatches.value = updated
        }

        visibleMatches.addSource(allMatches) {
            apiList = it
            currentList = it
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

    fun removeMatch(id: String) = viewModelScope.launch {
        repo.deleteMatch(id)
    }

}







