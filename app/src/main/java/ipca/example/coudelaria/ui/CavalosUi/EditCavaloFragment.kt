package ipca.example.coudelaria.ui.CavalosUi

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
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
import org.json.JSONObject


class EditCavaloFragment : Fragment() {

    // Global Variables
    private lateinit var cavalo: Cavalo

    private lateinit var textViewId: TextView
    private lateinit var textViewNome: TextView
    private lateinit var textViewBirthDate: TextView
    private lateinit var textViewPai: TextView
    private lateinit var textViewMae: TextView
    private lateinit var textViewCodCoudelariaNasc: TextView
    private lateinit var textViewCodCoudelariaResid: TextView
    private lateinit var radioGroupGender: RadioGroup
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se tiver argumentos recebe o cavalo e converte de string para JsonObject e depois para cavalo
        arguments?.let {
            val cavaloJsonStr = it.getString("cavalo")
            val cavaloJson = JSONObject(cavaloJsonStr)
            cavalo = Cavalo.fromJson(cavaloJson)
        }

    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_edit_cavalo, container, false)

        // Get the objects in the view to the variables
        textViewId = rootView.findViewById(R.id.textViewCavaloId)
        textViewNome = rootView.findViewById(R.id.editTextName)
        textViewBirthDate = rootView.findViewById(R.id.editTextBirthDate)
        textViewPai = rootView.findViewById(R.id.editTextPai)
        textViewMae = rootView.findViewById(R.id.editTextMae)
        textViewCodCoudelariaNasc = rootView.findViewById(R.id.editTextCodCoudelariaNasc)
        textViewCodCoudelariaResid = rootView.findViewById(R.id.editTextCodCoudelariaResid)
        radioGroupGender = rootView.findViewById(R.id.RadioGroupGender)
        saveButton = rootView.findViewById(R.id.buttonSaveChanges)

        setHasOptionsMenu(true)

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fill the text Views
        textViewId.text                 = if (cavalo.codCavalo          != null) cavalo.codCavalo.toString()          else null
        textViewNome.text               = if (cavalo.nomeCavalo         != null) cavalo.nomeCavalo.toString()         else null
        textViewBirthDate.text          = if (cavalo.dataNascimento     != null) cavalo.dataNascimento.toString()     else null
        textViewPai.text                = if (cavalo.pai                != null) cavalo.pai.toString()                else null
        textViewMae.text                = if (cavalo.mae                != null) cavalo.mae.toString()                else null
        textViewCodCoudelariaNasc.text  = if (cavalo.codCoudelariaNasc  != null) cavalo.codCoudelariaNasc.toString()  else null
        textViewCodCoudelariaResid.text = if (cavalo.codCoudelariaResid != null) cavalo.codCoudelariaResid.toString() else null

        // fill the gender radio button
        val genero = if (cavalo.genero == "M") R.id.radioBtMasculine else R.id.RadioBtFemale
        radioGroupGender.check(genero)

        // Save button event
        saveButton.setOnClickListener() {

            //Começar corrotina para interagir com a api
            GlobalScope.launch(Dispatchers.IO) {
                val client = OkHttpClient()

                // Receber os dados inseridos relativos ao novo cavalo
                val cavaloEdited = Cavalo(
                    if (textViewId.text.                isNotEmpty()) textViewId.text.toString().toInt()                 else null,
                    if (textViewNome.text.              isNotEmpty()) textViewNome.text.toString()                       else null,
                    if (textViewBirthDate.text.         isNotEmpty()) textViewBirthDate.text.toString()                  else null,
                    if (radioGroupGender.checkedRadioButtonId == R.id.radioBtMasculine) "M" else "F",
                    if (textViewMae.text.               isNotEmpty()) textViewMae.text.toString().toInt()                else null,
                    if (textViewPai.text.               isNotEmpty()) textViewPai.text.toString().toInt()                else null,
                    if (textViewCodCoudelariaNasc.text .isNotEmpty()) textViewCodCoudelariaNasc.text.toString().toInt()  else null,
                    if (textViewCodCoudelariaResid.text.isNotEmpty()) textViewCodCoudelariaResid.text.toString().toInt() else null,
                )

                // Mandar o cavalo para uma especie de body de uma pagina em string formato json
                val requestBody = RequestBody.create(
                    // Application/json é porque é um texto em json. se fosse uma imagem por exemplo era diferente
                    "application/json".toMediaTypeOrNull(),
                    cavaloEdited.toJson().toString()
                )

                // Criar a o pedido para inserir os dados (request)
                val request = Request.Builder()
                    .url("http://" + MainActivity.IP + ":5000/api/Cavalos")
                    .put(requestBody)
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


    /*
       Top menu with the remove button
    */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit_cavalo,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId){
            R.id.itemRemove -> {

                //Começar corrotina para interagir com a api
                GlobalScope.launch(Dispatchers.IO) {
                    val client = OkHttpClient()

                    // Mandar o cavalo para uma especie de body de uma pagina em string formato json
                    val requestBody = RequestBody.create(
                            // Application/json é porque é um texto em json. se fosse uma imagem por exemplo era diferente
                            "application/json".toMediaTypeOrNull(),
                            cavalo.toJson().toString()
                    )

                    // Criar a o pedido para inserir os dados (request)
                    val request = Request.Builder()
                            .url("http://" + MainActivity.IP + ":5000/api/Cavalos")
                            .delete(requestBody)
                            .build()

                    // Enviar a resposta (response)
                    client.newCall(request).execute().use { response ->

                        println("MSG ->" + response.message)

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

        return false
    }

}
