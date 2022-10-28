package jun88.rr.fb88.dicegame_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jun88.rr.fb88.databinding.FragmentDiceBinding


class DiceFragment : Fragment() {

    private var _binding: FragmentDiceBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentDiceBinding.inflate(inflater, container, false)
        onClick()
        return binding.root

    }

    private fun onClick() {
        binding.pressToPlay.setOnClickListener {
            playDiceFun()
        }
    }

    private fun playDiceFun() {

        val value1 = Utils.randomDiceValues()
        val value2 = Utils.randomDiceValues()

        val picture1 = resources.getIdentifier("dice_$value1", "drawable", context?.packageName)
        val picture2 = resources.getIdentifier("dice_$value2", "drawable", context?.packageName)


        binding.diceImage1.setImageResource(picture1)
        binding.diceImage2.setImageResource(picture2)

        binding.diceNumber1.text = value1.toString()
        binding.diceNumber2.text = value2.toString()
    }
}