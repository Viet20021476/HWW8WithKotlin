package com.example.hww8withkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_b.*
import model.Student

class ActivityB : AppCompatActivity() {

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        var pos: Int = intent.extras!!["pos"] as Int
        var student: Student = intent.extras!!["obj_std"] as Student

        tv_std_name_edit.setText(student.getName())
        tv_std_address_edit.setText(student.getAddress())
        tv_std_pnumber_edit.setText(student.getPhoneNumber())

        save_btn_B.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val newName = tv_std_name_edit.text.toString()
                val newAddress = tv_std_address_edit.text.toString()
                val newPNumber = tv_std_pnumber_edit.text.toString()
                val std = Student(newName, newAddress, newPNumber, student.getId())

                val intent = Intent()
                val bundle = Bundle()

                bundle.putSerializable("obj_std", std)
                bundle.putInt("pos", pos)

                intent.putExtras(bundle)
                setResult(RESULT_OK, intent)

                onBackPressed()
            }
        })
    }
}