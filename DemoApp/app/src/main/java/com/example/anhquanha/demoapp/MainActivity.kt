package com.example.anhquanha.demoapp

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(){

    var arrPost:ArrayList<Post> = ArrayList()
    var arrUser:ArrayList<User> = ArrayList()
    var arrComment:ArrayList<Comment> = ArrayList()
    var postAdapter:PostAdapter? = null
    var listview:ListView? = null
    var progressbar:ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GetPost().execute("http://jsonplaceholder.typicode.com/posts")
        GetUser().execute("http://jsonplaceholder.typicode.com/users")
        GetComment().execute("http://jsonplaceholder.typicode.com/comments")

        listview = findViewById(R.id.list_title)
        postAdapter = PostAdapter(this, arrPost)
        listview?.adapter = postAdapter

        listview?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent:Intent = Intent(this, DetailActivity::class.java)
            var userIdstr:String = ""
            var cmtCount:Int = 0
            for(element in arrUser)
            {
                if(arrPost.get(position).userId == element.id) {
                    userIdstr = element.name
                    break
                }
            }
            for(cmt in arrComment)
            {
                if(arrPost.get(position).id == cmt.postId)  cmtCount++
            }
            intent.putExtra("userId", userIdstr)
            intent.putExtra("body", arrPost.get(position).body)
            intent.putExtra("cmtCount", cmtCount)
            startActivity(intent)

        }

    }

//    inner class MyThread:Thread{
//        var threadName:String? = null
//        constructor(threadName:String):super(){
//            this.threadName = threadName
//        }
//
//        override fun run() {
//            super.run()
//        }
//    }

    inner class  GetPost:AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var jsonArray:JSONArray = JSONArray(result)
            var userId:Int = 0
            var id:Int = 0
            var title:String = ""
            var body:String =""
            for(post in 0..jsonArray.length()-1)
            {
                var jsonObject:JSONObject = jsonArray.getJSONObject(post)
                userId = jsonObject.getInt("userId")
                id = jsonObject.getInt("id")
                title = jsonObject.getString("title")
                body = jsonObject.getString("body")
                var postexp:Post = Post(userId, id, title, body)
                arrPost.add(postexp)
            }
            //Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
        }

    }
    private fun getContentURL(url: String?) : String{
        var content: StringBuilder = StringBuilder();
        val url: URL = URL(url)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val inputStreamReader: InputStreamReader = InputStreamReader(urlConnection.inputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

        var line: String = ""
        try {
            do {
                line = bufferedReader.readLine()
                if(line != null){
                    content.append(line)
                }
            }while (line != null)
            bufferedReader.close()
        }catch (e: Exception){
            Log.d("AAA", e.toString())
        }
        return content.toString()
    }

    inner class  GetUser:AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var jsonArray:JSONArray = JSONArray(result)
            var id:Int
            var name:String = ""

            for(user in 0..jsonArray.length()-1)
            {
                var jsonObject:JSONObject = jsonArray.getJSONObject(user)
                id = jsonObject.getInt("id")
                name = jsonObject.getString("name")
                var userexp:User = User(id, name)
                arrUser.add(userexp)

            }

        }

    }

    inner class  GetComment:AsyncTask<String, Void, String>(){
        override fun doInBackground(vararg p0: String?): String {
            return getContentURL(p0[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var jsonArray:JSONArray = JSONArray(result)
            var postId:Int

            for(comment in 0..jsonArray.length()-1)
            {
                var jsonObject:JSONObject = jsonArray.getJSONObject(comment)
                postId = jsonObject.getInt("postId")
                var cmtexp:Comment = Comment(postId)
                arrComment.add(cmtexp)

            }

        }

    }



}







