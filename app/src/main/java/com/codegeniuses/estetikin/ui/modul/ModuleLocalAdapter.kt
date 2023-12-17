package com.codegeniuses.estetikin.ui.modul

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codegeniuses.estetikin.R
import com.codegeniuses.estetikin.data.local.Module
import com.codegeniuses.estetikin.model.response.module.DataItem

class ModuleLocalAdapter(private val listModule: ArrayList<Module>): RecyclerView.Adapter<ModuleLocalAdapter.ListViewHolder>() {
    private var listModules: List<Module> = emptyList()

    private lateinit var onItemClickCallback: ModuleLocalAdapter.OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data: Module)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconModule: ImageView = itemView.findViewById(R.id.iv_icon_item_module)
        val titleModule: TextView = itemView.findViewById(R.id.tv_item_module_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_module, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listModule.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (icon, title) = listModule[position]

        holder.iconModule.setImageResource(icon)
        holder.titleModule.text = title
//        holder.itemView.setOnClickListener {
//            onItemClickCallback.onItemClicked(listModules[holder.adapterPosition])
//        }
    }
}