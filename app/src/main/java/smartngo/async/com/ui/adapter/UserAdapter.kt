package smartngo.async.com.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import smartngo.async.com.databinding.ItemListBinding
import smartngo.async.com.domain.model.User

class UserAdapter (
    val onSelect: (position:Int)->Unit,
):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    var listData: MutableList<User> = ArrayList()
    var selectedIndex = 0;

    fun insertAll(data: List<User>, selectedIndex: Int = 0) {
        this.selectedIndex = selectedIndex
        data.forEach {
            listData.add(it)
            notifyItemInserted(listData.size - 1)
        }
    }

    fun clear() {
        if (listData.isNotEmpty()) {
            listData.clear()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.bindTo(item)

    }

    override fun getItemCount() = listData.size

    inner class ViewHolder(val binding: ItemListBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: User) {

            binding.tvName.text = user.name
            binding.tvLocation.text = user.location

            binding.card.setOnClickListener {
                onSelect(position)
            }

        }
    }
}
