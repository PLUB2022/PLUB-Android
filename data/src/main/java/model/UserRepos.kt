package model

import com.google.gson.annotations.SerializedName

data class UserRepos(

    @SerializedName("name")
    private val _name : String,

    @SerializedName("id")
    private val _id : String,

    @SerializedName("email")
    private val _email : String,

    @SerializedName("phone")
    private val _phone : String,

    @SerializedName("password")
    private val _password : String
) : User{

    override val email: String
        get() = _email

    override val id: String
        get() = _id

    override val name: String
        get() = _name

    override val password: String
        get() = _password

    override val phone: String
        get() = _phone
}
