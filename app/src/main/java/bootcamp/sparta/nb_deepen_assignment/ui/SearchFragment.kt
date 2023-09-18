package bootcamp.sparta.nb_deepen_assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import bootcamp.sparta.nb_deepen_assignment.databinding.SearchFragmentBinding

import bootcamp.sparta.nb_deepen_assignment.repository.ContentRepository
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainViewModel
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainViewModelFactory

class SearchFragment() : Fragment() {
    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        SearchListAdapter(requireContext())
    }

    private val repository by lazy {
        ContentRepository()
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
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

        viewModel.contentList.observe(requireActivity()) { result ->
            adapter.submitList(result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}