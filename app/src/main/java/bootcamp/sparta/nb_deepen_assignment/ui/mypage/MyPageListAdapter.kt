package bootcamp.sparta.nb_deepen_assignment.ui.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.nb_deepen_assignment.databinding.RecyclerItemBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

class SearchListAdapter(private val context: Context): ListAdapter<ContentData, SearchListAdapter.SearchHolder>(
    object: DiffUtil.ItemCallback<ContentData>() {
        override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
            return oldItem == newItem
        }
    }
) {

    class SearchHolder(private val binding: RecyclerItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ContentData)=with(binding) {
            Log.d("Adapter", item.dateTime)
            val old_format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000Z", Locale.KOREAN)
            val old_date = old_format.parse(item.dateTime)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN)
            val new_date = old_date?.let { formatter.format(it) }

            dttm.text = new_date
            Glide.with(context)
                .load(item.thumbnail)
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        val layoutParams = RecyclerView.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//        parent.rootView.layoutParams = layoutParams
//        parent.rootView.setPadding(10, 10, 10, 10dp)
        return SearchHolder(view, context)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(getItem(position))
    }
}