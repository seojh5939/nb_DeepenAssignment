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
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainSharedEventForMyPage
import bootcamp.sparta.nb_deepen_assignment.viewmodel.MainSharedViewModel
import bootcamp.sparta.nb_deepen_assignment.utils.Utils
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
                likeClickActions(position, item)
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
        initData()
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
                    is MainSharedEventForMyPage.AddMyPageContent -> addBookmarkedItem(event.item)
                    is MainSharedEventForMyPage.RemoveMyPageContent -> removeBookmarkedItem(event.item)
                }
            }
        }
    }

    private fun initData() {
        // Load SharedPrefs
        val list = Utils.loadMyPageContents(requireContext())
        viewModel.list.value = list
    }

    // MyPage의 Adapter item 클릭시 실행하는 행동.
    private fun likeClickActions(position: Int, item: ContentData) {
        viewModel.removeItem(position, item)
        sharedViewModel.updateSearchedItem(item)
        viewModel.deleteSharedPrefs(item)
    }

    // MyPage 좋아요 업데이트 이벤트
    private fun addBookmarkedItem(item: ContentData) {
        viewModel.addItem(item)
        viewModel.saveSharedPrefs(item)
    }

    private fun removeBookmarkedItem(item: ContentData) {
        viewModel.removeItem(item = item)
        viewModel.deleteSharedPrefs(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}