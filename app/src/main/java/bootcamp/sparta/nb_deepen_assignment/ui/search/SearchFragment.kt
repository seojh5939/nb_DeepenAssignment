package bootcamp.sparta.nb_deepen_assignment.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import bootcamp.sparta.nb_deepen_assignment.databinding.SearchFragmentBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData

import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import bootcamp.sparta.nb_deepen_assignment.ui.main.MainActivity
import bootcamp.sparta.nb_deepen_assignment.ui.main.MainSharedEventForMyPage
import bootcamp.sparta.nb_deepen_assignment.ui.main.MainSharedEventForSearch
import bootcamp.sparta.nb_deepen_assignment.ui.main.MainSharedViewModel
import bootcamp.sparta.nb_deepen_assignment.viewmodel.SearchViewModel
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainViewModelFactory

class SearchFragment() : Fragment() {
    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        SearchListAdapter(
            onLikeClickListener = { position, item ->
                modifySearchItem(position, item)
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

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    viewModel.resultImageAndVideo(p0)
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun initViewModel() = with(viewModel) {
        list.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            sharedViewModel.updateMyPAgeItems(it)
        }

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