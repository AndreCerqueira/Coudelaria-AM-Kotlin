package ipca.example.coudelaria.models

import org.json.JSONObject

class Classific {

    // "cod_prova"
    // "cod_cavalo"
    // "classific"

    var codProva:  Int? = null
    var codCavalo: Int? = null
    var classific: Int? = null

    constructor() {

    }

    constructor(codProva: Int?, codCavalo: Int?, classific: Int?) {
        this.codProva = codProva
        this.codCavalo = codCavalo
        this.classific = classific
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("cod_prova"  , codProva )
        jsonObject.put("cod_cavalo" , codCavalo)
        jsonObject.put("classific"  , classific)

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : Classific {
            val classific = Classific()
            classific.codProva  = if (!jsonObject.isNull("cod_prova"  )) jsonObject.getInt("cod_prova" ) else null
            classific.codCavalo = if (!jsonObject.isNull("cod_cavalo" )) jsonObject.getInt("cod_cavalo") else null
            classific.classific = if (!jsonObject.isNull("classific"  )) jsonObject.getInt("classific" ) else null

            return classific
        }
    }

}