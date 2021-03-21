package ru.skillbranch.kotlinexemple

import androidx.annotation.VisibleForTesting

object UserHolder {
    private val  map = mutableMapOf<String, User>()

    fun registerUser(
            fullName: String,
            email: String,
            password: String
    ): User {
        if (map.containsKey(email?.toLowerCase()))  throw IllegalArgumentException("A user with this email already exists")
        else
            return User.makeUser(fullName, email = email, password = password)
                    .also { user -> map[user.login] = user }
    }

    fun registerUserByPhone(
            fullName: String,
            rawPhone: String
    ): User {
        val phone = rawPhone.replace("[^+\\w]".toRegex(), "")
        when {
            !Regex(pattern = """\+\d{11}""").matches(phone) -> throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
            map.containsKey(phone) -> throw IllegalArgumentException("A user with this phone already exists")
            else ->
                return User.makeUser(fullName, phone = rawPhone)
                    .also { user -> map[user.login] = user }
        }
    }

    fun loginUser(login: String, password: String): String? {
        return map[login.trim().toLogin()]?.run {
            if (checkPassword(password)) this.userInfo
            else null
        }
    }

    fun requestAccessCode(login: String): Unit {
        map[login.toLogin()]?.requestAccessCode()
    }

    private fun String.toLogin():String {
        return  if (this.contains(Regex("""[a-zA-Z]""")))
                    this.toLowerCase()
                else
                    this.replace("[^+\\d]".toRegex(), "")
    }

    fun importUsers(source: List<String>): List<User> {
        val result = mutableListOf<User>()
        for (item in source) {
            //println("___Import from: $item ___")
            val (rawFullName, rawEmail, rawSaltHash, rawPhone) = item.split(";")
            val fullName = rawFullName.trim()
            val email = normalizeField(rawEmail)
            val saltHash = normalizeField(rawSaltHash)
            val phone = normalizeField(rawPhone)
            val salt: String?
            val hash: String?
            when (saltHash) {
                null -> {
                    salt = null
                    hash = null
                }
                else -> {
                    val (drawSalt, drawHash) = saltHash.trim().split(":")
                    salt = normalizeField(drawSalt)
                    hash = normalizeField(drawHash)
                }
            }
            val currentUser = User.makeCsvUser(
                fullName = fullName,
                email = email,
                phone = phone,
                salt = salt,
                hash = hash
            )
            map[currentUser.login] = currentUser
            result.add(currentUser)
        }
        return result
    }

    private fun normalizeField(source: String) = if (source.isEmpty()) null else source.trim()

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder() {
        map.clear()
    }
}