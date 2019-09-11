package com.aula.mitap

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.github.nkzawa.socketio.client.IO
import com.google.gson.Gson
import com.github.nkzawa.socketio.client.Socket

class Client : LifecycleObserver{

    private val socket: Socket

    init {
        socket = IO.socket("http://34.217.47.91")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {
        socket.connect()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener() {
        socket.disconnect()
    }

    fun setup(
        owner: LifecycleOwner,
        callback: (note: Message) -> Unit
    ) {
        var message: Message? = null

        socket.on(Socket.EVENT_CONNECT) {
            if (it.size > 0 && owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                message = Gson().fromJson(it.get(0) as String, Message::class.java)
                message?.let {
                    callback.invoke(it)
                }
            }
        }.on("chat message") {
            if (it.size > 0 && owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                message = Gson().fromJson(it.get(0) as String, Message::class.java)
                message?.let {
                    callback.invoke(it)
                }
            }
        }.on(Socket.EVENT_DISCONNECT) {
            if (it.size > 0 && owner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                message = Gson().fromJson(it.get(0) as String, Message::class.java)
                message?.let {
                    callback.invoke(it)
                }
            }
        }
    }

    fun emit(message: Message) {
        socket.emit("chat message", Gson().toJson(message))
    }
}