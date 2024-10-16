package com.ahmety.kotlinmvvm.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.poilabscase.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class NewsAdapter(private var article: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_news, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(article[position])
    }

    override fun getItemCount(): Int {
        return article.size
    }

    fun update(data: List<Article>) {
        article = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvAuthorName: TextView = view.findViewById(R.id.tvAuthorName)
        private val ivThumbnail: ImageView = view.findViewById(R.id.ivThumbnail)
        private val tvArticle: TextView = view.findViewById(R.id.tvArticleTitle)
        private val tvPublishedAt: TextView = view.findViewById(R.id.tvPublishedDate)

        @SuppressLint("SimpleDateFormat")
        fun bind(item: Article) {
            tvAuthorName.text = item.author
            tvArticle.text = item.title

            val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            timeFormat.timeZone = TimeZone.getTimeZone("UTC")
            item.publishedAt?.let { publishedAtTime ->
                val date: Date? = timeFormat.parse(publishedAtTime)
                val desiredFormat = SimpleDateFormat("dd/MM/yyyy")
                date?.let { dateTime ->
                    val formattedDate = desiredFormat.format(dateTime)
                    tvPublishedAt.text = tvPublishedAt.context.getString(R.string.published_time, formattedDate)
                }
            }

            Glide.with(ivThumbnail.context).load(item.urlToImage).placeholder(R.drawable.img_search)
                .error(R.drawable.ic_error_img).into(ivThumbnail)
        }
    }
}