package com.codegeniuses.estetikin.ui.article

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codegeniuses.estetikin.R
import com.codegeniuses.estetikin.model.response.article.ArticleItem

class ArticleLocalAdapter(private var listArticle: ArrayList<ArticleItem>) : RecyclerView.Adapter<ArticleLocalAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallBack


    interface OnItemClickCallBack {
        fun onItemClicked(data: ArticleItem)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvArticleTitle: TextView = itemView.findViewById(R.id.tv_article_title)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_article_category)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val title = listArticle[position].title
        val author = listArticle[position].author
        holder.tvArticleTitle.text = title
        holder.tvAuthor.text = author

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listArticle[holder.adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount() = listArticle.size

    @SuppressLint("NotifyDataSetChanged")
    fun setArticleData(articles: List<ArticleItem>) {
        listArticle = articles as ArrayList<ArticleItem>
        notifyDataSetChanged()
    }


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }
}
