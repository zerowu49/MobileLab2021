package id.ac.umn.uts

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_lagu.view.*


class ListLaguAdapter internal constructor(context: Context, listLagu: ArrayList<MusicFiles>) : RecyclerView.Adapter<ListLaguAdapter.LaguViewHolder>(){
    private val mListLagu: ArrayList<MusicFiles> = listLagu
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val mContext: Context = context

    inner class LaguViewHolder(itemView: View, adapter: ListLaguAdapter): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val judulLagu: TextView = itemView.judulLagu
        val lokasiLagu: TextView = itemView.pathLagu
        val mAdapter: ListLaguAdapter = adapter

        override fun onClick(v: View) {
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaguViewHolder {
        val mItemView = mInflater.inflate(R.layout.list_lagu, parent, false)
        return LaguViewHolder(mItemView, this)
    }

    override fun onBindViewHolder(holder: LaguViewHolder, position: Int) {
        holder.judulLagu.text = mListLagu.get(position).title
        holder.lokasiLagu.text = mListLagu.get(position).path

        holder.itemView.setOnClickListener {
            val playingIntent = Intent(mContext, PlayingActivity::class.java)
            playingIntent.putExtra("position", position)
            mContext.startActivity(playingIntent)
        }
    }

    override fun getItemCount(): Int {
        return mListLagu.size
    }
}