package com.rxmobileteam.lecture9sample.features.feeds.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.rxmobileteam.lecture9sample.GlideRequests
import com.rxmobileteam.lecture9sample.databinding.CollectionItemLayoutBinding
import com.rxmobileteam.lecture9sample.databinding.PhotoItemLayoutBinding

object PhotoUiItemDiffUtilCallback : DiffUtil.ItemCallback<PhotosUiItem>() {
  override fun areItemsTheSame(oldItem: PhotosUiItem, newItem: PhotosUiItem): Boolean =
    oldItem.id == newItem.id

  override fun areContentsTheSame(oldItem: PhotosUiItem, newItem: PhotosUiItem): Boolean =
    oldItem == newItem
}

class PhotoUiItemAdapter(
  private val glide: GlideRequests
) :
  ListAdapter<PhotosUiItem, PhotoUiItemAdapter.VH>(PhotoUiItemDiffUtilCallback) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
    PhotoItemLayoutBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
  )

  override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

  inner class VH(private val binding: PhotoItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PhotosUiItem) {
      binding.run {
        textViewTitle.text = item.title
        textViewDescription.text = item.description

        textViewLike.text = item.like.toString()
        tvAuthor.text = item.nameAuthor

        glide
          .load(item.authorUrl)
          .fitCenter()
          .centerCrop()
          .transition(withCrossFade())
          .into(imgAuthor)

        glide
          .load(item.coverUrl)
          .fitCenter()
          .centerCrop()
          .transition(withCrossFade())
          .into(imageView)
      }
    }
  }
}