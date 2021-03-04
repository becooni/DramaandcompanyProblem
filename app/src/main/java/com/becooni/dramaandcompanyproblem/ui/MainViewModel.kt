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

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val disposable = CompositeDisposable()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    val inputText = MutableLiveData<String>()

    private fun searchUsers(query: String) {
        githubRepository.getUsers(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loading.value = true }
            .doFinally { _loading.value = false }
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

    fun onBookmarkClick(item: User) {
        githubRepository.insertBookmarkUser(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                },
                Throwable::printStackTrace
            )
            .addTo(disposable)
    }
}