package com.ahmety.kotlinmvvm.view.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmety.kotlinmvvm.extension.loadImagesAsThumbnail
import com.ahmety.kotlinmvvm.model.Article
import com.ahmety.poilabscase.R
import com.ahmety.poilabscase.databinding.RowNewsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class NewsAdapter(
    private val onItemClick: (Article) -> Unit
) : ListAdapter<Article, NewsAdapter.MViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val binding = RowNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MViewHolder(
        private val binding: RowNewsBinding,
        private val onItemClick: (Article) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SimpleDateFormat")
        fun bind(item: Article) {
            binding.tvAuthorName.text = item.author
            binding.tvArticleTitle.text = item.title

            val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            timeFormat.timeZone = TimeZone.getTimeZone("UTC")
            item.publishedAt?.let { publishedAtTime ->
                val date: Date? = timeFormat.parse(publishedAtTime)
                val desiredFormat = SimpleDateFormat("dd/MM/yyyy")
                date?.let { dateTime ->
                    val formattedDate = desiredFormat.format(dateTime)
                    binding.tvPublishedDate.text =
                        binding.root.context.getString(R.string.published_time, formattedDate)
                }
            }

            item.urlToImage?.let { imgUrl ->
                binding.ivThumbnail.loadImagesAsThumbnail(imgUrl)
            }

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}