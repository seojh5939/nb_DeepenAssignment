package bootcamp.sparta.nb_deepen_assignment.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.nb_deepen_assignment.R
import bootcamp.sparta.nb_deepen_assignment.databinding.SearchItemBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.utils.Utils.changeDateFormat
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class SearchListAdapter(
    private val onLikeClickListener: (Int, ContentData) -> Unit
) : ListAdapter<ContentData, SearchListAdapter.SearchHolder>(
    object : DiffUtil.ItemCallback<ContentData>() {
        override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem == newItem
        }
    }
) {

    class SearchHolder(
        private val binding: SearchItemBinding,
        private val context: Context,
        private val onLikeClickListener: (Int, ContentData) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentData) = with(binding) {
            // DateTime
            dttm.text = changeDateFormat(item.dateTime)

            // Image
            Glide.with(context)
                .load(item.thumbnail)
                .into(image)

            // 좋아요
            if(item.isLike) {
                like.setBackgroundResource(R.drawable.baseline_star_24)
            } else {
                like.setBackgroundResource(R.drawable.baseline_star_border_24)
            }

            like.setOnClickListener {
                if(!item.isLike) {
                    onLikeClickListener(
                        bindingAdapterPosition,
                        item.copy(
                            isLike = true
                        )
                    )
                } else {
                    onLikeClickListener(
                        bindingAdapterPosition,
                        item.copy(
                            isLike = false
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(
            view,
            parent.context,
            onLikeClickListener)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems() {
        submitList(null)
        notifyDataSetChanged()
    }
}