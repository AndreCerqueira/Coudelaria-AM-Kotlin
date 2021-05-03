package ipca.example.coudelaria.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import ipca.example.coudelaria.MainActivity
import ipca.example.coudelaria.R
import ipca.example.coudelaria.models.Criador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject


class CriadoresFragment : Fragment() {

    // Global Variables
    lateinit var listView: ListView
    lateinit var adapter: CriadoresAdapter
    var criadores: MutableList<Criador> = arrayListOf()

    /*
        Fragment creation method, returns the fragment created
        @inflater = xml layout that is being instantiated
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Create the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_criadores, container, false)

        // Set data
        listView = rootView.findViewById(R.id.listViewCriadores)
        adapter = CriadoresAdapter()
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
            val request = Request.Builder().url("http://" + MainActivity.IP + ":5000/api/Criadores").build()

            // fazer a chamada com o pedido http e analisar a resposta
            client.newCall(request).execute().use { response ->

                // Variables
                val str: String = response.body!!.string()
                val jsonArrayCriadores = JSONArray(str)

                // Add the horses to the list
                for (index in 0 until jsonArrayCriadores.length()) {
                    val jsonArticle = jsonArrayCriadores.get(index) as JSONObject
                    val criador = Criador.fromJson(jsonArticle)
                    criadores.add(criador)
                }

                // Refresh the list adapter
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }

            }

        }

    }

    inner class CriadoresAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return criadores.size
        }

        override fun getItem(position: Int): Any {
            return criadores[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_criador, parent, false)

            // Variables
            val textViewCod = rowView.findViewById<TextView>(R.id.textViewCodCriador)
            val textViewNomeCriador = rowView.findViewById<TextView>(R.id.textViewNomeCriador)

            // Set data
            textViewCod.text = criadores[position].codCriador.toString()
            textViewNomeCriador.text = criadores[position].nome.toString()

            return rowView
        }
    }
}