package jun88.rr.fb88.presentation.dashboard.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import jun88.rr.fb88.R
import jun88.rr.fb88.databinding.FragmentMainBinding
import jun88.rr.fb88.presentation.dashboard.adapter.CarouselAdapter
import jun88.rr.fb88.utils.Utilities.Companion.dataImg
import jun88.rr.fb88.utils.Utilities.Companion.setTimer

class MainFragment : Fragment() , OnClickListener {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentMainBinding.inflate(inflater,container,false)
        onClickListener()
        setupCarousel()
        return binding.root
    }

    private fun setupCarousel(){
        binding.carouselView.apply {
            adapter = CarouselAdapter(dataImg)
            setTimer(this, dataImg.size)
        }
        binding.carouselIndicator.setViewPager2(binding.carouselView)

    }

    private fun onClickListener(){
        binding.miniGame.setOnClickListener(this)
        binding.webView.setOnClickListener(this)
        binding.privacyPolicy.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
       when (v){
           binding.webView -> {
               findNavController().navigate(R.id.action_mainFragment_to_webview)
           }
           binding.miniGame -> {
               findNavController().navigate(R.id.action_mainFragment_to_diceFragment)
           }
           binding.privacyPolicy -> {
               findNavController().navigate(R.id.action_mainFragment_to_privacyFragment)
           }
       }
    }
}