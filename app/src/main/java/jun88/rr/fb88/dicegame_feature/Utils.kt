package jun88.rr.fb88.dicegame_feature

import android.os.Handler
import androidx.viewpager2.widget.ViewPager2
import jun88.rr.fb88.R
import java.util.*

class Utils {

    companion object {

        val RANDOM = Random()

        fun randomDiceValues(): Int {
            return RANDOM.nextInt(6) + 1 // 1-6

            }

    }
}