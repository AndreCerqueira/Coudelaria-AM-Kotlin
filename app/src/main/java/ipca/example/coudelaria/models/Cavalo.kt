package ipca.example.coudelaria.models

import org.json.JSONObject

class Cavalo {

    //"2005-05-31T00:00:00"

    //"cod_cavalo"
    //"nome_cavalo"
    //"data_nascimento"
    //"genero"
    //"mae"
    //"pai"
    //"cod_coudelaria_nasc"
    //"cod_coudelaria_resid"

    var codCavalo            : Int? = null
    var nomeCavalo           : String? = null
    var dataNascimento       : String? = null
    var genero               : String? = null
    var mae                  : Int? = null
    var pai                  : Int? = null
    var codCoudelariaNasc    : Int? = null
    var codCoudelariaResid   : Int? = null

    constructor() {

    }

    constructor(
        codCavalo: Int?,
        nomeCavalo: String?,
        dataNascimento: String?,
        genero: String?,
        mae: Int?,
        pai: Int?,
        codCoudelariaNasc: Int?,
        codCoudelariaResid: Int?
    ) {
        this.codCavalo            = codCavalo
        this.nomeCavalo           = nomeCavalo
        this.dataNascimento       = dataNascimento
        this.genero               = genero
        this.mae                  = mae
        this.pai                  = pai
        this.codCoudelariaNasc    = codCoudelariaNasc
        this.codCoudelariaResid   = codCoudelariaResid
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("cod_cavalo"              , codCavalo           )
        jsonObject.put("nome_cavalo"             , nomeCavalo          )
        jsonObject.put("data_nascimento"         , dataNascimento      )
        jsonObject.put("genero"                  , genero              )
        jsonObject.put("mae"                     , mae                 )
        jsonObject.put("pai"                     , pai                 )
        jsonObject.put("cod_coudelaria_nasc"     , codCoudelariaNasc   )
        jsonObject.put("cod_coudelaria_resid"    , codCoudelariaResid  )

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : Cavalo {
            val cavalo = Cavalo()
            cavalo.codCavalo          = if (!jsonObject.isNull("cod_cavalo"          )) jsonObject.getInt   ("cod_cavalo"          )else null
            cavalo.nomeCavalo         = if (!jsonObject.isNull("nome_cavalo"         )) jsonObject.getString("nome_cavalo"         )else null
            cavalo.dataNascimento     = if (!jsonObject.isNull("data_nascimento"     )) jsonObject.getString("data_nascimento"     )else null
            cavalo.genero             = if (!jsonObject.isNull("genero"              )) jsonObject.getString("genero"              )else null
            cavalo.mae                = if (!jsonObject.isNull("mae"                 )) jsonObject.getInt   ("mae"                 )else null
            cavalo.pai                = if (!jsonObject.isNull("pai"                 )) jsonObject.getInt   ("pai"                 )else null
            cavalo.codCoudelariaNasc  = if (!jsonObject.isNull("cod_coudelaria_nasc" )) jsonObject.getInt   ("cod_coudelaria_nasc" )else null
            cavalo.codCoudelariaResid = if (!jsonObject.isNull("cod_coudelaria_resid")) jsonObject.getInt   ("cod_coudelaria_resid")else null

            return cavalo
        }
    }

}