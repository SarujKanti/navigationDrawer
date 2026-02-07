package com.skd.navigationdrawerview.ui.savedMatches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.skd.navigationdrawerview.data.repository.MatchRepository
import kotlinx.coroutines.launch

class SavedMatchesViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repo = MatchRepository(application)

    val savedMatches = repo.getSavedMatches().asLiveData()

    fun removeMatch(id: String) = viewModelScope.launch {
        repo.deleteMatch(id)
    }
}


