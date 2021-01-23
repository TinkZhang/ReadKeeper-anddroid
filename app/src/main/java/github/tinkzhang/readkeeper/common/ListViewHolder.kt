package github.tinkzhang.readkeeper.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.tinkzhang.readkeeper.R

class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: IconListItem) {
        itemView.findViewById<ImageView>(R.id.listIcon).setImageResource(item.icon)
        itemView.findViewById<TextView>(R.id.listText).text = item.text
    }
}

data class IconListItem (val icon: Int, val text: String)

class ListViewAdapter(val items: List<IconListItem>): RecyclerView.Adapter<ListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.icon_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}