package ipca.example.coudelaria.models

import org.json.JSONObject

class Coudelaria {

    // "cod_coudelaria"
    // "nome_coudelaria"
    // "data_inicio_actividade"
    // "cod_criador"

    var codCoudelaria         : Int? = null
    var nomeCoudelaria        : String? = null
    var dataInicioActividade  : String? = null
    var codCriador            : Int? = null

    constructor() {

    }

    constructor(
        codCoudelaria: Int?,
        nomeCoudelaria: String?,
        dataInicioActividade: String?,
        codCriador: Int?
    ) {
        this.codCoudelaria         = codCoudelaria
        this.nomeCoudelaria        = nomeCoudelaria
        this.dataInicioActividade  = dataInicioActividade
        this.codCriador            = codCriador
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("cod_coudelaria"          , codCoudelaria        )
        jsonObject.put("nome_coudelaria"         , nomeCoudelaria       )
        jsonObject.put("data_inicio_actividade"  , dataInicioActividade )
        jsonObject.put("cod_criador"             , codCriador           )

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : Coudelaria {
            val coudelaria = Coudelaria()
            coudelaria.codCoudelaria          = if (!jsonObject.isNull("cod_coudelaria"          )) jsonObject.getInt   ("cod_coudelaria"          )else null
            coudelaria.nomeCoudelaria         = if (!jsonObject.isNull("nome_coudelaria"         )) jsonObject.getString("nome_coudelaria"         )else null
            coudelaria.dataInicioActividade   = if (!jsonObject.isNull("data_inicio_actividade"  )) jsonObject.getString("data_inicio_actividade"  )else null
            coudelaria.codCriador             = if (!jsonObject.isNull("cod_criador"             )) jsonObject.getInt   ("cod_criador"             )else null

            return coudelaria
        }
    }

}