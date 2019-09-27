package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

/**
 * @Author : YFL  is Creating a porject in My Application
 * @Package com.example.myapplication
 * @Email : yufeilong92@163.com
 * @Time :2019/9/27 10:11
 * @Purpose :
 */
class RlvAdapter(mContext: Context, var data: MutableList<String>) :
    RecyclerView.Adapter<RlvAdapter.ViewHoler>() {
    private var inflater: LayoutInflater? = null

    init {
        inflater = LayoutInflater.from(mContext)

    }

    interface RecyclerItemListener {
        fun itemClickListener(btn:Button)
    }

    private var listener: RecyclerItemListener? = null;

    fun setRecyclerListener(listener: RecyclerItemListener) {
        this.listener = listener
    }

    class ViewHoler(item: View) : RecyclerView.ViewHolder(item) {
       val btn=item.findViewById<Button>(R.id.btn_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RlvAdapter.ViewHoler {
        return ViewHoler(inflater!!.inflate(R.layout.item, null))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RlvAdapter.ViewHoler, position: Int) {
        holder.btn.setOnClickListener {
            listener?.let {
                it.itemClickListener(holder.btn)
            }
        }
    }
}