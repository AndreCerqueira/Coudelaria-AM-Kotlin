package ipca.example.coudelaria.models

import org.json.JSONObject

class Prova {

    // "cod_prova"
    // "nome_prova"
    // "data"

    var codProva: Int?     = null
    var nomeProva: String? = null
    var data: String?      = null

    constructor() {

    }

    constructor(codProva: Int?, nomeProva: String?, data: String?) {
        this.codProva = codProva
        this.nomeProva = nomeProva
        this.data = data
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("cod_prova"  , codProva  )
        jsonObject.put("nome_prova" , nomeProva )
        jsonObject.put("data"       , data      )

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : Prova {
            val prova = Prova()
            prova.codProva   = if (!jsonObject.isNull("cod_prova"  )) jsonObject.getInt   ("cod_prova"  ) else null
            prova.nomeProva  = if (!jsonObject.isNull("nome_prova" )) jsonObject.getString("nome_prova" ) else null
            prova.data       = if (!jsonObject.isNull("data"       )) jsonObject.getString("data"       ) else null

            return prova
        }
    }
}