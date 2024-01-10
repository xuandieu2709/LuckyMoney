package vn.xdeuhug.luckyMoney.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paginate.recycler.LoadingListItemCreator
import vn.xdeuhug.luckyMoney.R


class CustomLoadingListItemCreator : LoadingListItemCreator {
    override fun onCreateViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val view: View = inflater.inflate(R.layout.custom_loading, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
    }

}

internal class VH(itemView: View) : RecyclerView.ViewHolder(itemView)
