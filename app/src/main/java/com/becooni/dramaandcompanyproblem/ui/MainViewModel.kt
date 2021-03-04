package com.becooni.dramaandcompanyproblem.ui

import androidx.lifecycle.ViewModel
import com.becooni.dramaandcompanyproblem.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    
}