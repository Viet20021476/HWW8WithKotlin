package com.example.hww8withkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import database.StudentDatabase
import kotlinx.android.synthetic.main.activity_c.*
import model.Student
import java.util.*

class ActivityC : AppCompatActivity() {


    fun init() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        handleEvent()
    }

    fun handleEvent() {
        save_btn_C.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View) {
                val newName = tv_std_name_add.text.toString()
                val newAddr = tv_std_address_add.text.toString()
                val newPNumber = tv_std_pnumber_add.text.toString()

                if (newName != "" && newAddr != "" && newPNumber != "") {
                    val randomID = UUID.randomUUID().toString()
                    val student = Student(newName, newAddr, newPNumber, randomID)
                    val intent = Intent()
                    val bundle = Bundle()
                    bundle.putSerializable("obj_std", student)
                    intent.putExtras(bundle)
                    setResult(RESULT_FIRST_USER, intent)
                    writetoDB(student)
                    onBackPressed()
                } else {
                    Toast.makeText(
                        this@ActivityC,
                        "Thông tin không được để trống!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    fun writetoDB(student: Student) {
        var studentDatabase: StudentDatabase = StudentDatabase(this)
        studentDatabase.add(student)
    }


}