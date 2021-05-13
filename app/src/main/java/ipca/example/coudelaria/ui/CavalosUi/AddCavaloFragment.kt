package ipca.example.coudelaria.ui.CavalosUi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import ipca.example.coudelaria.MainActivity
import ipca.example.coudelaria.R
import ipca.example.coudelaria.models.Cavalo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.util.*

fun Date.toServerString() : String  {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    format.timeZone = TimeZone.getTimeZone("GMT")
    return format.format(this)
}


class AddCavaloFragment : Fragment() {

    lateinit var  editText   : EditText
    lateinit var  buttonSave : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_add_cavalo, container, false)
        editText = rootView.findViewById(R.id.editTextName)
        buttonSave = rootView.findViewById(R.id.buttonSave)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Botao de Guardar Dados
        buttonSave.setOnClickListener {

            //Começar corrotina para interagir com a api
            GlobalScope.launch(Dispatchers.IO) {
                val client = OkHttpClient()

                // Receber os dados inseridos relativos ao novo cavalo
                val cavalo = Cavalo(
                        null,
                        editText.text.toString(),
                        Date().toServerString(),
                        "M",
                        null,
                        null,
                        10,
                        10  )

                // Mandar o cavalo para uma especie de body de uma pagina em string formato json
                val requestBody = RequestBody.create(
                        // Application/json é porque é um texto em json. se fosse uma imagem por exemplo era diferente
                        "application/json".toMediaTypeOrNull(),
                        cavalo.toJson().toString()
                )

                // Criar a o pedido para inserir os dados (request)
                val request = Request.Builder()
                        .url("http://" + MainActivity.IP + ":5000/api/Cavalos")
                        .post(requestBody)
                        .build()

                // Enviar a resposta (response)
                client.newCall(request).execute().use { response ->

                    // Tem de voltar ao main para poder trocar de fragment
                    GlobalScope.launch (Dispatchers.Main){

                        // Se o resultado indicar que tudo deu certo
                        if (response.message == "OK"){
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }

    }

}