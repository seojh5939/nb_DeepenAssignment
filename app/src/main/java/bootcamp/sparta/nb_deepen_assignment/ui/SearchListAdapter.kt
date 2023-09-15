package bootcamp.sparta.nb_deepen_assignment.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.nb_deepen_assignment.databinding.RecyclerItemBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData

class SearchListAdapter(): ListAdapter<ContentData, SearchListAdapter.SearchHolder>(
    object: DiffUtil.ItemCallback<ContentData>() {
        override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem == newItem
        }
    }
) {
    class SearchHolder(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentData)=with(binding){
            // TODO 통신처리 후 받아온 url 어떻게 처리할지 결정후 변경
            image.setImageURI(Uri.parse(item.thumbnail))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        return SearchHolder(RecyclerItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(getItem(position))
    }
}