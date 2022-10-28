package jun88.rr.fb88.presentation.dashboard.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.findNavController
import jun88.rr.fb88.R
import jun88.rr.fb88.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var exit = false

    private val currentFragment by lazy {
        findNavController(R.id.fragView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onBackPressed() {
        if (currentFragment.currentDestination?.id == R.id.mainFragment) {
            if (exit) {
                finishAffinity()
                return
            }
            exit = true
            Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({ exit = false }, 2000)
        } else {
            findNavController(R.id.fragView).navigateUp()
        }
    }
}