package ipca.example.coudelaria.models

import org.json.JSONObject

class Criador {

    // "cod_criador"
    // "nome"

    var codCriador: Int? = null
    var nome: String?    = null

    constructor() {

    }

    constructor(codCriador: Int?, nome: String?) {
        this.codCriador = codCriador
        this.nome = nome
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("cod_criador"  , codCriador )
        jsonObject.put("nome"         , nome       )

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : Criador {
            val criador = Criador()
            criador.codCriador   = if (!jsonObject.isNull("cod_criador"  )) jsonObject.getInt   ("cod_criador"  ) else null
            criador.nome         = if (!jsonObject.isNull("nome"         )) jsonObject.getString("nome"         ) else null

            return criador
        }
    }

}