package com.becooni.dramaandcompanyproblem.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.becooni.dramaandcompanyproblem.model.ItemType
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

    private val disposables = CompositeDisposable()

    private val _users = MutableLiveData<List<ItemType>>()
    val users: LiveData<List<ItemType>> = _users

    private val _bookmarks = MutableLiveData<List<ItemType>>()
    val bookmarks: LiveData<List<ItemType>> = _bookmarks

    val inputText = MutableLiveData<String>()

    private lateinit var currentTab: TabType

    init {
        githubRepository.getBookmarkUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _bookmarks.value = it
                },
                Throwable::printStackTrace
            )
            .addTo(disposables)
    }

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
            .addTo(disposables)
    }

    private fun searchBookmarks(query: String) {
        githubRepository.getBookmarkUsers(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _loading.value = true }
            .doFinally { _loading.value = false }
            .subscribe(
                {
                    _bookmarks.value = it
                },
                Throwable::printStackTrace
            )
            .addTo(disposables)
    }

    internal fun onSearchClick(query: String) {
        when (currentTab) {
            TabType.SEARCH -> searchUsers(query)
            TabType.BOOKMARK -> searchBookmarks(query)
        }
    }

    internal fun onBookmarkClick(item: User) {
        if (item.bookmarked) {
            unsetUserBookmark(item)
            removeBookmark(item)
            removeBookmarkDb(item)
        } else {
            setUserBookmark(item)
            addBookmark(item)
            addBookmarkDb(item)
        }
    }

    private fun setUserBookmark(item: User) {
        val list = _users.value?.toMutableList() ?: mutableListOf()
        val position = list.indexOfFirst {
            when (it) {
                is ItemType.Item -> it.item.id == item.id
                else -> false
            }
        }
        if (position > -1) {
            list[position] = ItemType.Item(item.copy(bookmarked = true))
            _users.value = list
        }
    }

    private fun unsetUserBookmark(item: User) {
        val list = _users.value?.toMutableList() ?: mutableListOf()
        val position = list.indexOfFirst {
            when (it) {
                is ItemType.Item -> it.item.id == item.id
                else -> false
            }
        }
        if (position > -1) {
            list[position] = ItemType.Item(item.copy(bookmarked = false))
            _users.value = list
        }
    }

    private fun addBookmark(item: User) {
        val list = _bookmarks.value?.toMutableList() ?: mutableListOf()

        val initial = item.initial

        var position = -1

        for (i in list.indices) {
            val itemType = list[i]
            if (itemType is ItemType.Item && itemType.item.initial == initial) {
                position = i
                if (itemType.item.name > item.name) {
                    position = i - 1
                    break
                }
            }
        }

        val copiedItem = ItemType.Item(item.copy(bookmarked = true))

        if (position > -1) {
            list.add(position + 1, copiedItem)
        } else {
            val prePosition = list.indexOfFirst {
                it is ItemType.Header && it.initial > initial
            }

            if (prePosition > -1) {
                list.add(prePosition, ItemType.Header(initial))
                list.add(prePosition + 1, copiedItem)
            } else {
                list.add(ItemType.Header(initial))
                list.add(copiedItem)
            }
        }

        _bookmarks.value = list
    }

    private fun removeBookmark(item: User) {
        val list = _bookmarks.value?.toMutableList() ?: mutableListOf()

        val position = list.indexOfFirst {
            when (it) {
                is ItemType.Item -> it.item.id == item.id
                else -> false
            }
        }

        if (position > -1) {
            list.removeAt(position)

            val remaining = list.count { it is ItemType.Item && it.item.initial == item.initial }

            if (remaining == 0) {
                list.removeAll { it is ItemType.Header && it.initial == item.initial }
            }

            _bookmarks.value = list
        }
    }

    private fun addBookmarkDb(item: User) {
        githubRepository.insertBookmarkUser(item.copy(bookmarked = true))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, Throwable::printStackTrace)
            .addTo(disposables)
    }

    private fun removeBookmarkDb(item: User) {
        githubRepository.deleteBookmarkUser(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, Throwable::printStackTrace)
            .addTo(disposables)
    }

    internal fun setCurrentTab(tabType: TabType) {
        currentTab = tabType
    }

    private fun LiveData<List<ItemType>>.toMutableList() = value?.toMutableList() ?: mutableListOf()
}