package com.aula.mitap

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable


class Message : BaseObservable() {

    @Bindable
    var type: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.type)
        }

    @Bindable
    var text: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.text)
        }

    @Bindable
    var msg: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.msg)
        }
}