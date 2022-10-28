package jun88.rr.fb88.privacy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import jun88.rr.fb88.common.AppModel
import jun88.rr.fb88.databinding.PrivacyViewBinding

class PrivacyAdapter : RecyclerView.Adapter<PrivacyAdapter.AdapterHolder>() {

    private var privacyList = emptyList<AppModel>()
    class AdapterHolder (val adapts : PrivacyViewBinding) : RecyclerView.ViewHolder(adapts.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHolder = AdapterHolder(
        PrivacyViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: AdapterHolder, position: Int) {
      with(holder){
          with(privacyList[position]){
              adapts.privacyTitle.text = this.dataTitle
              adapts.privacyDesc.text = this.dataDesc
          }
      }
    }
    override fun getItemCount(): Int {
        return privacyList.size
    }
    fun setAdapter (setAdapt : List<AppModel>){
        this.privacyList = setAdapt
    }
}