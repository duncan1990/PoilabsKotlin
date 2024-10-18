package com.ahmety.kotlinmvvm.view.articledetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ahmety.kotlinmvvm.extension.doUnderline
import com.ahmety.kotlinmvvm.extension.loadImages
import com.ahmety.kotlinmvvm.utilities.formatDate
import com.ahmety.poilabscase.R
import com.ahmety.poilabscase.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : Fragment() {
    private var _binding: FragmentArticleDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ArticleDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.article.urlToImage?.let { img ->
            binding.ivArticle.loadImages(img)
        }
        binding.tvAuthor.text = args.article.author
        binding.tvPublishedDate.text = args.article.publishedAt?.formatDate()
        args.article.urlToImage?.let { img ->
            binding.ivArticle.loadImages(img)
        }
        binding.tvUrl.text = getString(R.string.u_url_to_read_full_article_u).doUnderline()
        binding.tvMainTopic.text = args.article.title
        binding.tvUrl.setOnClickListener {
            startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse(args.article.url)
            ))
        }
        binding.tvArticle.text = args.article.content
        binding.tvArticle.setOnClickListener {
            startActivity(Intent(
                Intent.ACTION_VIEW,
                Uri.parse(args.article.url)
            ))
        }
    }

}