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
import ipca.example.coudelaria.models.Coudelaria
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class CoudelariaFragment : Fragment() {

    // Global Variables
    lateinit var listView: ListView
    lateinit var adapter: CoudelariaAdapter
    var coudelarias: MutableList<Coudelaria> = arrayListOf()

    /*
        Fragment creation method, returns the fragment created
        @inflater = xml layout that is being instantiated
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Create the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_coudelaria, container, false)

        // Set data
        listView = rootView.findViewById(R.id.listViewCoudelarias)
        adapter = CoudelariaAdapter()
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
            val request = Request.Builder().url("http://" + MainActivity.IP + ":5000/api/Coudelarias").build()

            // fazer a chamada com o pedido http e analisar a resposta
            client.newCall(request).execute().use { response ->

                // Variables
                val str: String = response.body!!.string()
                val jsonArrayCoudelarias = JSONArray(str)

                // Add the horses to the list
                for (index in 0 until jsonArrayCoudelarias.length()) {
                    val jsonArticle = jsonArrayCoudelarias.get(index) as JSONObject
                    val coudelaria = Coudelaria.fromJson(jsonArticle)
                    coudelarias.add(coudelaria)
                }

                // Refresh the list adapter
                GlobalScope.launch(Dispatchers.Main) {
                    adapter.notifyDataSetChanged()
                }

            }

        }

    }

    inner class CoudelariaAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return coudelarias.size
        }

        override fun getItem(position: Int): Any {
            return coudelarias[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_coudelaria, parent, false)

            // Variables
            val textViewCod = rowView.findViewById<TextView>(R.id.textViewCodCoudelaria)
            val textViewNomeCoudelaria = rowView.findViewById<TextView>(R.id.textViewNomeCoudelaria)
            val textViewDataInicio = rowView.findViewById<TextView>(R.id.textViewDataInicio)

            // Set data
            textViewCod.text = coudelarias[position].codCoudelaria.toString()
            textViewNomeCoudelaria.text = coudelarias[position].nomeCoudelaria.toString()
            textViewDataInicio.text = coudelarias[position].dataInicioActividade.toString()

            return rowView
        }
    }
}

