package com.aula.mitap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class MessageViewModel(
    val list: MutableList<Message> = ArrayList(),
    val message: MutableLiveData<Message> = MutableLiveData()
) : ViewModel()

