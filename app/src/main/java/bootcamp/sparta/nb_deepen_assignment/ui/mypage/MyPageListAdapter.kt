package bootcamp.sparta.nb_deepen_assignment.ui.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.nb_deepen_assignment.databinding.MyPageItemBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.utils.Utils.changeDateFormat
import com.bumptech.glide.Glide

class MyPageListAdapter(private val onLikeClickListener: (Int, ContentData) -> Unit): ListAdapter<ContentData, MyPageListAdapter.Holder>(
    object: DiffUtil.ItemCallback<ContentData>() {
        override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem == newItem
        }
    }
) {

    class Holder(
        private val binding: MyPageItemBinding,
        private val context: Context,
        private val onLikeClickListener: (Int, ContentData) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentData)=with(binding) {
            dttmMypage.text = changeDateFormat(item.dateTime)
            Glide.with(context)
                .load(item.thumbnail)
                .into(imageMypage)

            likeMypage.setOnClickListener {
                onLikeClickListener(
                    bindingAdapterPosition,
                    item.copy(
                        isLike = false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = MyPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view, parent.context, onLikeClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}