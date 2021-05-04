package ipca.example.coudelaria.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ipca.example.coudelaria.MainActivity
import ipca.example.coudelaria.R
import ipca.example.coudelaria.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class UserFragment : Fragment() {

    // Global Variables
    lateinit var listView: ListView
    lateinit var adapter: UsersAdapter
    var users: MutableList<User> = arrayListOf()

    /*
        Fragment creation method, returns the fragment created
        @inflater = xml layout that is being instantiated
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Create the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_user, container, false)

        // Set data
        listView = rootView.findViewById(R.id.listViewUsers)
        adapter = UsersAdapter()
        listView.adapter = adapter

        return rootView
    }


    /*
        After fragment creation
        @view = fragment
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.IO) {

            // Variables
            val client = OkHttpClient()
            val request = Request.Builder().url("http://" + MainActivity.IP + ":5000/api/Provas").build()

            // fazer a chamada com o pedido http e analisar a resposta
            client.newCall(request).execute().use { response ->

                // Variables
                val str: String = response.body!!.string()
                val jsonArrayCriadores = JSONArray(str)

                // Add the horses to the list
                for (index in 0 until jsonArrayCriadores.length()) {
                    val jsonArticle = jsonArrayCriadores.get(index) as JSONObject
                    val user = User.fromJson(jsonArticle)
                    users.add(user)
                }

                // Refresh the list adapter
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }

            }

        }

    }

    inner class UsersAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return users.size
        }

        override fun getItem(position: Int): Any {
            return users[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_user, parent, false)

            // Variables
            val textViewIdUser   = rowView.findViewById<TextView>(R.id.textViewCodProva)
            val textViewNomeUser = rowView.findViewById<TextView>(R.id.textViewNomeProva)

            // Set data
            textViewIdUser.text   = users[position].id.toString()
            textViewNomeUser.text = users[position].username.toString()

            return rowView
        }
    }
}