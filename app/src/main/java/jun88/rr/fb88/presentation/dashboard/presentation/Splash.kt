package jun88.rr.fb88.presentation.dashboard.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import jun88.rr.fb88.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val handler = Handler()
        handler.postDelayed(
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1500
        )
    }
}