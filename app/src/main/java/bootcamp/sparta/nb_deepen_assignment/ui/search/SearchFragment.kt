package bootcamp.sparta.nb_deepen_assignment.ui.search

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bootcamp.sparta.nb_deepen_assignment.databinding.SearchFragmentBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData

import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainSharedEventForSearch
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainSharedViewModel
import bootcamp.sparta.nb_deepen_assignment.viewmodel.SearchViewModel
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainViewModelFactory


class SearchFragment : Fragment() {
    companion object {
        fun newInstance() = SearchFragment()
    }
    private var page = 1

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        SearchListAdapter(
            onLikeClickListener = { position, item ->
                modifySearchItem(position, item)
                if(item.isLike) {
                    sharedViewModel.addMyPageItem(item)
                } else {
                    sharedViewModel.removeMyPageItem(item)
                }
            }
        )
    }

    private val repository by lazy {
        ContentRepository()
    }

    private val viewModel: SearchViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    private val sharedViewModel: MainSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        recyclerView.adapter = adapter

        // 무한스크롤 처리
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = recyclerView.adapter?.itemCount ?: 0

                if(lastVisibleItemPosition + 1 == totalItemCount) {
                    page += 1
                    viewModel.resultImageAndVideo(viewModel.query.value ?: "", page)
                }
            }
        })

        // SearchView
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if(!p0.isNullOrEmpty()) {
                    viewModel.query.value = p0
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initViewModel() = with(viewModel) {
        // 검색 쿼리
        query.observe(viewLifecycleOwner) {query ->
            adapter.clearItems()
            viewModel.searchedList.clear()
            viewModel.resultImageAndVideo(query, page)
        }

        // 로딩 스피너
        loading.observe(viewLifecycleOwner) {
            if(it) {
                binding.loading.visibility = View.VISIBLE
            } else {
                binding.loading.visibility = View.INVISIBLE
            }
        }

        // RecyclerView Item List
        list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // Event
        with(sharedViewModel) {
            searchedEvent.observe(viewLifecycleOwner) { event ->
                when (event) {
                    is MainSharedEventForSearch.UpdateSearcedContent -> {
                        viewModel.modifySearchItem(
                            item = event.item
                        )
                    }
                }
            }
        }
    }

    private fun modifySearchItem(position: Int, item: ContentData) {
        viewModel.modifySearchItem(position, item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}