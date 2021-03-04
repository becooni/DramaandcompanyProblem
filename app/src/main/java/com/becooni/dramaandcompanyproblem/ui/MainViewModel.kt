package com.becooni.dramaandcompanyproblem.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.becooni.dramaandcompanyproblem.model.User
import com.becooni.dramaandcompanyproblem.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    val inputText = MutableLiveData<String>()

    private fun searchUsers(query: String) {
        githubRepository.getUsers(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _users.value = it
                },
                Throwable::printStackTrace
            )
            .addTo(disposable)
    }

    fun onSearchClick(query: String) {
        searchUsers(query)
    }
}