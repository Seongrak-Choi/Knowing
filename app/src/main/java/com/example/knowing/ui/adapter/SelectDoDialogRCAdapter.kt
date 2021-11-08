package com.example.knowing.ui.adapter

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.databinding.ItemRcDialogDoBinding

class SelectDoDialogRCAdapter(private var doList: ArrayList<String>) :
    RecyclerView.Adapter<SelectDoDialogRCAdapter.ViewHolder>(), Filterable { //필터 기능을 위해 Filterable 상속

    private var files: ArrayList<String> = doList

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectDoDialogRCAdapter.ViewHolder {
        val binding =
            ItemRcDialogDoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    //필터기능 때문에 이부분이 좀 다름.
    override fun onBindViewHolder(holder: SelectDoDialogRCAdapter.ViewHolder, position: Int) {
        val current = files[position]
        holder.bind(current)  //holder부분에 넘겨받은 doList[position]을 넘겨주는게 아님
    }

    override fun getItemCount(): Int = files.size //필터기능 때문에 files길이 반환

    inner class ViewHolder(val binding: ItemRcDialogDoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.txName.text = data
            binding.btnSelect.setOnClickListener {
                listener?.onItemClick(data)
            }
        }
    }

    /*
    필터기능을 위해 getFilter 오버라이딩
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                println(charString)
                files = if (charString.isEmpty()) {
                    doList
                } else {
                    val filteredList = ArrayList<String>()
                    if (doList != null) {
                        for (name in doList) {
                            if (name.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(name)
                            }
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = files
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                files = results?.values as ArrayList<String>
                notifyDataSetChanged()
            }

        }
    }

}