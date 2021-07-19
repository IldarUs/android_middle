package ru.skillbranch.skillarticles.data.adapters

import org.json.JSONObject
import ru.skillbranch.skillarticles.data.local.User
import ru.skillbranch.skillarticles.extensions.asMap

class UserJsonAdapter() : JsonAdapter<User> {
    override fun fromJson(json: String): User? {
        if (json.isEmpty()) {
            return null
        }

        val jsonObj = JSONObject(json)
        val user = User(
                jsonObj.getString("id"),
                jsonObj.getString("name"),
                jsonObj.getStringOrNull("avatar"),
                jsonObj.getInt("rating"),
                jsonObj.getInt("respect"),
                jsonObj.getStringOrNull("about")
        )
        return user
    }

    override fun toJson(obj: User?): String {
        val jsonObj = JSONObject()
        obj?.asMap()?.forEach{ (key, value) -> jsonObj.put(key, value) }
        val str = jsonObj.toString()
        return str
    }
}

fun JSONObject.getStringOrNull(key: String): String? {
    return if (isNull(key)) null
    else optString(key)
}