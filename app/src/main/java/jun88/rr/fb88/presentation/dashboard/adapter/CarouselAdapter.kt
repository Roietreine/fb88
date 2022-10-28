package jun88.rr.fb88.presentation.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jun88.rr.fb88.databinding.CarouselPlaceholderBinding

class CarouselAdapter(var dataImg : ArrayList<Int>) : RecyclerView.Adapter<CarouselAdapter.AdapterHolder>() {

    class AdapterHolder (val adapts : CarouselPlaceholderBinding): RecyclerView.ViewHolder(adapts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder = AdapterHolder(
        CarouselPlaceholderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {

        holder.adapts.apply {
            carouselPlaceholder.setBackgroundResource(dataImg[position])

        }
    }

    override fun getItemCount(): Int {
        return  dataImg.size
    }
}