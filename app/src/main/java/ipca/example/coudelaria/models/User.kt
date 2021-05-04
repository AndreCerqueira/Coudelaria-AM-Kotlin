package ipca.example.coudelaria.models

import org.json.JSONObject

class User {

    // "id"
    // "username"
    // "password"

    var id      : Int?    = null
    var username: String? = null
    var password: String? = null

    constructor() {

    }

    constructor(id: Int?, username: String?, password: String?) {
        this.id = id
        this.username = username
        this.password = password
    }

    fun toJson() : JSONObject {
        val jsonObject = JSONObject()

        jsonObject.put("id"       , id       )
        jsonObject.put("username" , username )
        jsonObject.put("password" , password )

        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject) : User {
            val user = User()
            user.id       = if (!jsonObject.isNull("id"       )) jsonObject.getInt   ("id"       ) else null
            user.username = if (!jsonObject.isNull("username" )) jsonObject.getString("username" ) else null
            user.password = if (!jsonObject.isNull("password" )) jsonObject.getString("password" ) else null

            return user
        }
    }

}