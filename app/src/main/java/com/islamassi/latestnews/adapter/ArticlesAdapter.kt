package com.islamassi.latestnews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.StringUtil
import com.islamassi.latestnews.databinding.ArticleViewBinding
import com.islamassi.latestnews.model.Article
import com.islamassi.latestnews.viewholder.ArticleViewHolder

class ArticlesAdapter constructor(private val articles : MutableList<Article>):
    RecyclerView.Adapter<ArticleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(ArticleViewBinding.inflate(LayoutInflater.from(parent.context),parent, false ))

    override fun getItemCount(): Int = articles.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) =
        holder.onBind(articles[position])

    fun notifyChange(newArticles: List<Article>){
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }
}