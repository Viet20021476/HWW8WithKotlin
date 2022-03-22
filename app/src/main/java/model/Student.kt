package model

import java.io.Serializable

class Student(
    private var name: String,
    private var address: String,
    private var phoneNumber: String,
    private var id: String
) : Serializable {

    fun getName() = name

    fun setName(name: String) {
        this.name = name
    }

    fun getAddress() = address

    fun setAddress(address: String) {
        this.address = address
    }

    fun getPhoneNumber() = phoneNumber

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun getId() = id

    fun setId(id: String) {
        this.id = id
    }
}