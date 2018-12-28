package id.co.eltisia.asseteltisia

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class MainAdapter: RecyclerView.Adapter<CustomViewHolder>() {
    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CustomViewHolder {
        val layInflate = LayoutInflater.from(parent.context)
        val itemForRow = layInflate.inflate(R.layout.asset_row,parent,false)
        return CustomViewHolder(itemForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, p1: Int) {

    }
}

class CustomViewHolder(v:View):RecyclerView.ViewHolder(v){

}
