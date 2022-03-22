package com.example.hww8withkotlin

import adapter.StudentAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.StudentDatabase
import kotlinx.android.synthetic.main.student.*
import model.Student

class ActivityA : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var studentList: MutableList<Student>

    private lateinit var addStd: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        init()
        handleEvent()
    }

    fun init() {
        addStd = findViewById(R.id.add_std_iv)
        //studentName = findViewById(R.id.std_name)

        val studentDatabase = StudentDatabase(this)
        studentList = studentDatabase.getDataFromDB()

        recyclerView = findViewById(R.id.std_rcv)
        studentAdapter = StudentAdapter(studentList, this)
        val linearLayoutManager = LinearLayoutManager(this)

        recyclerView.adapter = studentAdapter
        recyclerView.layoutManager = linearLayoutManager

    }


    fun getRecyclerView() = recyclerView
    fun getStudentAdapter() = studentAdapter

    companion object {
        private const val REQUEST_CODE_2 = 222
    }

    fun handleEvent() {
        addStd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(applicationContext, ActivityC::class.java)
                startActivityForResult(intent, REQUEST_CODE_2)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == StudentAdapter.REQUEST_CODE_1 && resultCode == RESULT_OK) {
            val pos = data?.extras!!["pos"] as Int
            val student = data.extras!!["obj_std"] as Student
            writetoDB(student)
            studentList[pos].setName(student.getName())
            studentList[pos].setAddress(student.getAddress())
            studentList[pos].setPhoneNumber(student.getPhoneNumber())
            studentAdapter.notifyItemChanged(pos)
        } else if (requestCode == REQUEST_CODE_2 && resultCode == RESULT_FIRST_USER) {
            val student = data?.extras!!["obj_std"] as Student
            studentList.add(student)
            studentAdapter.notifyItemInserted(studentList.size - 1)
            Toast.makeText(this, "Thêm mới thành công!", Toast.LENGTH_SHORT).show()
            recyclerView.scrollToPosition(studentList.size - 1)
        }
    }

    fun writetoDB(std: Student) {
        val studentDatabase = StudentDatabase(this)
        studentDatabase.update(std)
    }
}