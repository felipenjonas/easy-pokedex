package com.example.easypokedex

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ModelAdapter(context: Context?, dados: List<Person>) : RecyclerView.Adapter<ModelAdapter.ViewHolder>() {
    private val dados: List<Person>
    private val inflater: LayoutInflater
    private var itemClickListener: ItemClickListener? = null
    private var itemLongClickListener: ItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // A única coisa a ser adaptada aqui seria o nome do arquivo de layout da linha
        val view = inflater.inflate(R.layout.linha_recycleview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = dados[position]
        holder.textView.text = person.name
        Picasso.get().load(person.image).error(R.drawable.ic_launcher_background)
            .resize(100, 100).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return dados.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener {

        var textView: TextView
        var imageView: ImageView
        override fun onClick(v: View) {
            if (itemClickListener != null) {
                itemClickListener!!.onItemClick(v, adapterPosition)
            }
        }

        override fun onLongClick(v: View): Boolean {
            if (itemLongClickListener != null) {
                itemLongClickListener!!.onItemLongClick(v, adapterPosition)
            }
            return true
        }

        init {
            textView = itemView.findViewById(R.id.textView)
            imageView = itemView.findViewById(R.id.imageView)
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    fun setLongClickListener(itemLongClickListener: ItemLongClickListener?) {
        this.itemLongClickListener = itemLongClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    interface ItemLongClickListener {
        fun onItemLongClick(view: View?, position: Int)
    }

    init {
        inflater = LayoutInflater.from(context)
        this.dados = dados
    }
}