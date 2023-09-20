package bootcamp.sparta.nb_deepen_assignment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bootcamp.sparta.nb_deepen_assignment.R
import bootcamp.sparta.nb_deepen_assignment.databinding.MyPageFragmentBinding
import bootcamp.sparta.nb_deepen_assignment.databinding.SearchFragmentBinding

class MyPageFragment : Fragment() {
    companion object {
        fun newInstance() = MyPageFragment()
    }

    private var _binding: MyPageFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}