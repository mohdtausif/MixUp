package com.gerardbradshaw.v2mixup.ui.moreapps

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.gerardbradshaw.v2mixup.models.AppInfo
import com.gerardbradshaw.v2mixup.R
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class AppListAdapter @Inject constructor(
  private val context: Context,
  private val glideInstance: RequestManager
) : RecyclerView.Adapter<AppListAdapter.AppInfoViewHolder>() {

  private var appList: ArrayList<AppInfo>? = null
  private val inflater = LayoutInflater.from(context)

  fun setAppList(appList: ArrayList<AppInfo>?) {
    val listString = if (appList == null) "null" else Arrays.toString(appList.toArray())
    Log.d(TAG, "setAppList: list = $listString")
    this.appList = appList
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder {
    val itemView = inflater.inflate(R.layout.list_item_app, parent, false)
    return AppInfoViewHolder(itemView)
  }

  override fun getItemCount(): Int {
    return appList?.size ?: 0
  }

  override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
    if (appList != null) {
      val app = appList!![position]
      val resources = context.resources

      holder.titleView.text = resources.getString(app.titleRes)
      holder.descriptionView.text = resources.getString(app.descriptionRes)

      glideInstance
        .load(app.iconRes)
        .into(holder.iconView)

      holder.itemView.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(resources.getString(app.urlRes))
        context.startActivity(intent)
      }
    }
  }

  class AppInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleView: TextView = itemView.findViewById(R.id.app_title)
    val descriptionView: TextView = itemView.findViewById(R.id.app_description)
    val iconView: ImageView = itemView.findViewById(R.id.app_logo)
  }

  companion object {
    private const val TAG = "AppListAdapter"
  }
}