package adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hww8withkotlin.ActivityA
import com.example.hww8withkotlin.ActivityB
import com.example.hww8withkotlin.R
import database.StudentDatabase
import model.Student

class StudentAdapter(
    private var studentsList: MutableList<Student>,
    private var activityA: ActivityA
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        var context: Context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        var studentView: View = layoutInflater.inflate(R.layout.student, parent, false)

        return StudentViewHolder(studentView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        var student: Student = studentsList[position]

        holder.getStudentName().text = student.getName()
        holder.getStudentAddress().text = student.getAddress()
        holder.getStudentPNumber().text = student.getPhoneNumber()

        holder.getStudentView().setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View) {
                val pos: Int = activityA.getRecyclerView().getChildLayoutPosition(p0)
                var intent: Intent = Intent(activityA.applicationContext, ActivityB::class.java)
                var bundle: Bundle = Bundle()
                bundle.putSerializable("obj_std", student)
                bundle.putInt("pos", pos)
                intent.putExtras(bundle)
                activityA.startActivityForResult(intent, REQUEST_CODE_1)
            }
        })

        holder.getStudentDel().setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val pos = holder.adapterPosition

                val studentDatabase = StudentDatabase(activityA)
                studentDatabase.delete(studentsList[pos])

                studentsList.removeAt(pos)
                activityA.getStudentAdapter().notifyItemRemoved(pos)

            }
        })
    }

    override fun getItemCount() = studentsList.size


    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var studentView: View
        private lateinit var studentPic: ImageView
        private lateinit var studentName: TextView
        private lateinit var studentAddress: TextView
        private lateinit var studentPNumber: TextView
        private lateinit var studentDel: ImageView

        init {
            studentView = itemView.findViewById(R.id.std_view)
            studentPic = itemView.findViewById(R.id.std_pic_iv)
            studentName = itemView.findViewById(R.id.std_name)
            studentAddress = itemView.findViewById(R.id.std_address)
            studentPNumber = itemView.findViewById(R.id.std_pn)
            studentDel = itemView.findViewById(R.id.del_iv)
        }

        fun getStudentView() = studentView
        fun getStudentPic() = studentPic
        fun getStudentName() = studentName
        fun getStudentAddress() = studentAddress
        fun getStudentPNumber() = studentPNumber
        fun getStudentDel() = studentDel
    }

    companion object {
        const val REQUEST_CODE_1 = 22
    }
}