package com.aula.mitap

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aula.mitap.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment(), View.OnClickListener {


    private lateinit var binding: FragmentChatBinding
    private lateinit var messageViewModel: MessageViewModel
    private lateinit var adapter: ListChat
    private val client: Client = Client()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messageViewModel =
            this.activity?.run { ViewModelProvider(this).get(MessageViewModel::class.java) } ?: MessageViewModel()

        if (messageViewModel.message.value == null)
            messageViewModel.message.value = Message()


        binding.message = messageViewModel.message.value
        binding.fragment = this

        adapter = ListChat(messageViewModel.list)

        list_chat?.apply {
            adapter = this@ChatFragment.adapter
            layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
        }

        messageViewModel.message.observe(this.viewLifecycleOwner, androidx.lifecycle.Observer {
            if (TextUtils.equals("msg", it.type)) {
                messageViewModel.list.add(it)
                adapter.notifyItemInserted(messageViewModel.list.size)
            }

            binding.message?.text = it.msg
        })

        lifecycle.addObserver(client)

        client.setup(this.viewLifecycleOwner, callback = {
            messageViewModel.message.postValue(it)
        })
    }

    fun setValue(message: String) {
        binding.message?.type = "digitando"
        binding.message?.msg = message
        client.emit(binding.message ?: Message())
    }

    override fun onClick(p0: View?) {
        binding.message?.type = "msg"
        client.emit(binding.message ?: Message())
        binding.message?.msg = ""
    }
}