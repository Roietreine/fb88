package jun88.rr.fb88.privacy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import jun88.rr.fb88.databinding.FragmentPrivacyBinding
import jun88.rr.fb88.privacy.adapter.PrivacyAdapter
import jun88.rr.fb88.privacy.viewmodel.PrivacyViewModel

class PrivacyFragment : Fragment() {

    private var _binding: FragmentPrivacyBinding? = null
    private val binding get() = _binding!!
    private var privacyData = PrivacyViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPrivacyBinding.inflate(inflater, container, false)
        privacyData = ViewModelProvider(this)[PrivacyViewModel::class.java]


        privacyData.pvcFun()
        val adapts = PrivacyAdapter()
        privacyData.prvcyNf.observe(viewLifecycleOwner) {
            if (it != null) {
                adapts.setAdapter(it)
                binding.privacyRec.apply {
                    setHasFixedSize(true)
                    adapter = adapts
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
        return binding.root
    }
}