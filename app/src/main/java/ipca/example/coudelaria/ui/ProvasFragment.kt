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
import ipca.example.coudelaria.models.Prova
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class ProvasFragment : Fragment() {

    // Global Variables
    lateinit var listView: ListView
    lateinit var adapter: ProvasAdapter
    var provas: MutableList<Prova> = arrayListOf()

    /*
        Fragment creation method, returns the fragment created
        @inflater = xml layout that is being instantiated
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Create the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_provas, container, false)

        // Set data
        listView = rootView.findViewById(R.id.listViewProvas)
        adapter = ProvasAdapter()
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
                    val prova = Prova.fromJson(jsonArticle)
                    provas.add(prova)
                }

                // Refresh the list adapter
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }

            }

        }

    }

    inner class ProvasAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return provas.size
        }

        override fun getItem(position: Int): Any {
            return provas[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_provas, parent, false)

            // Variables
            val textViewCod = rowView.findViewById<TextView>(R.id.textViewCodProva)
            val textViewNomeProva = rowView.findViewById<TextView>(R.id.textViewNomeProva)
            val textViewDataProva = rowView.findViewById<TextView>(R.id.textViewDataProva)

            // Set data
            textViewCod.text = provas[position].codProva.toString()
            textViewNomeProva.text = provas[position].nomeProva.toString()
            textViewDataProva.text = provas[position].data.toString()

            return rowView
        }
    }
}