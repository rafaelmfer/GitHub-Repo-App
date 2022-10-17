package com.rafaelmfer.githubrepo.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.rafaelmfer.githubrepo.R
import com.rafaelmfer.githubrepo.databinding.ItemRepoInfoBinding
import com.rafaelmfer.githubrepo.domain.model.ItemModel

class ReposAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val repoList: MutableList<ItemModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReposViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ReposViewHolder)?.bind(repoList[position])
    }

    override fun getItemCount(): Int = repoList.size

    class ReposViewHolder(private val binding: ItemRepoInfoBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ReposViewHolder {
                val view = ItemRepoInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return ReposViewHolder(view)
            }
        }

        fun bind(item: ItemModel) {
            binding.apply {
                tvRepoName.text = item.repoName
                tvRepoDescription.text = item.description ?: tvRepoDescription.context.getString(R.string.no_description)
                tvUserName.text = item.userName
                tvStarsNumber.text = item.stargazersCount.toString()
                tvForksNumber.text = item.forksCount.toString()

                Glide.with(ivUserPhoto.context)
                    .load(item.userPhoto)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .placeholder(R.drawable.ic_load_image)
                    .into(ivUserPhoto)
            }
        }
    }

    fun addMoreItems(list: List<ItemModel>) {
        repoList.addAll(list)
        notifyItemInserted(itemCount)
    }
}