package com.example.anhquanha.demoapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text

/**
 * Created by anhquan.ha on 10/1/2017.
 */

class PostAdapter(var context:Context, var arrPost:ArrayList<Post>) : BaseAdapter() {

    class ViewHolder(row:View) {
        var postName: TextView
        init {
            postName = row.findViewById(R.id.postname)
        }
    }
    override fun getView(p0: Int, convertView: View?, viewGroup: ViewGroup?): View {
            var view:View?
            var viewholder:ViewHolder
            if (convertView == null)
            {
                var layoutinflater:LayoutInflater = LayoutInflater.from(context)
                view = layoutinflater.inflate(R.layout.post_view, null)
                viewholder = ViewHolder(view)
                view.tag = viewholder

            }else{
                view = convertView
                viewholder = convertView.tag as ViewHolder
            }
            var post:Post = getItem(p0) as Post
            viewholder.postName.text = post.title

            return view as View
    }

    override fun getItem(p0: Int): Any {
        return arrPost.get(p0)
    }

    override fun getItemId(p0: Int): Long {
       return p0.toLong()
    }

    override fun getCount(): Int {
        return arrPost.size
    }

    private var moviesList = ArrayList<Post>()

    init {
        this.moviesList = moviesList as ArrayList
    }




}