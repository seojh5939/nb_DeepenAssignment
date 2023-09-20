package bootcamp.sparta.nb_deepen_assignment.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import bootcamp.sparta.nb_deepen_assignment.databinding.MyPageFragmentBinding
import bootcamp.sparta.nb_deepen_assignment.model.ContentData
import bootcamp.sparta.nb_deepen_assignment.ui.main.MainSharedEventForMyPage
import bootcamp.sparta.nb_deepen_assignment.ui.main.MainSharedViewModel
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MyPageViewModel

class MyPageFragment : Fragment() {
    companion object {
        fun newInstance() = MyPageFragment()
    }

    private var _binding: MyPageFragmentBinding? = null
    private val binding get() = _binding!!

    private val listAdapter by lazy {
        MyPageListAdapter(
            onLikeClickListener = { position, item ->
                removeMyPageItem(position, item)
                sharedViewModel.updateSearchedItem(item)
            }
        )
    }

    private val viewModel: MyPageViewModel by lazy {
        ViewModelProvider(this)[MyPageViewModel::class.java]
    }

    private val sharedViewModel: MainSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        recyclerView.adapter = listAdapter
    }

    private fun initViewModel() = with(viewModel) {
        list.observe(viewLifecycleOwner) { list ->
            listAdapter.submitList(list)
        }

        with(sharedViewModel) {
            myPageEvent.observe(viewLifecycleOwner) { event ->
                when (event) {
                    is MainSharedEventForMyPage.UpdateMyPageItem -> {
                        viewModel.updateMyPageItems(event.items)
                    }
                }
            }
        }
    }

    fun addMyPageItem(item: ContentData) {
        viewModel.addItem(item)
    }

    fun removeMyPageItem(position: Int?= null, item: ContentData) {
        viewModel.removeItem(
            position = position,
            item = item
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}