import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Base64.decode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sebadracco.modak.R

class MainAdapter(
    var context: Activity,
    var dataList: List<DetailEntityResponse>? = mutableListOf(),
    private val onItemClickListener: IOnItemClickViewHolder
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_cell, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = dataList!![position]
        holder.textName.text = data.title
        holder.textDescription.text = if (data.titleDisplay != null) {
            HtmlCompat.fromHtml(
                data.titleDisplay,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        } else {
            HtmlCompat.fromHtml(
                "UnAvailable Information",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }


        holder.ivIcon.setOnClickListener {
            onItemClickListener.onItemClick(it, position, data)
        }
        holder.date.text = if (data.date != null) {
            HtmlCompat.fromHtml(
                data.date,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        } else {
            HtmlCompat.fromHtml(
                "UnAvailable Information",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
            data.date
        data.imageUrl?.let {
            Glide
                .with(context)
                .load(it)
                .centerCrop()
                .placeholder(R.drawable.circular_placeholder)
                .into(holder.ivIcon);
        }

    }


    override fun getItemCount(): Int {
        return dataList!!.size
    }

    fun setData(characters: List<DetailEntityResponse>) {
        dataList = characters
        notifyDataSetChanged()
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon = view.findViewById<ImageView>(R.id.main_photo_image)
        val textName = view.findViewById<TextView>(R.id.character_type_text)
        val textDescription = view.findViewById<TextView>(R.id.description_value_text)
        val date = view.findViewById<TextView>(R.id.date_value_text)
    }

}