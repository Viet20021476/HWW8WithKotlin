package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import model.Student

class StudentDatabase(context: Context) :
    SQLiteOpenHelper(context, "student.db", null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val createTableStatement =
            "CREATE TABLE $STUDENT_TABLE($ID TEXT PRIMARY KEY, $NAME TEXT, $ADDRESS TEXT, $P_NUMBER TEXT)"
        sqLiteDatabase.execSQL(createTableStatement)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}
    fun add(std: Student) {
        val sqLiteDatabase = this.writableDatabase
        val insertStatement =
            "INSERT INTO $STUDENT_TABLE($ID,$NAME,$ADDRESS,$P_NUMBER) VALUES(?,?,?,?)"
        sqLiteDatabase.execSQL(
            insertStatement,
            arrayOf(std.getId(), std.getName(), std.getAddress(), std.getPhoneNumber())
        )
    }

    fun update(std: Student) {
        val sqLiteDatabase = this.writableDatabase
        val updateStatement =
            "UPDATE $STUDENT_TABLE SET $NAME = ?,$ADDRESS = ?,$P_NUMBER = ? WHERE $ID = ?"
        sqLiteDatabase.execSQL(
            updateStatement, arrayOf(
                std.getName(), std.getAddress(),
                std.getPhoneNumber(), std.getId()
            )
        )
    }

    fun delete(std: Student) {
        val sqLiteDatabase = this.writableDatabase
        val deleteStatement = "DELETE FROM $STUDENT_TABLE WHERE $ID = ?"
        sqLiteDatabase.execSQL(deleteStatement, arrayOf(std.getId()))
    }

    fun getDataFromDB(): MutableList<Student> {

        val returnData: MutableList<Student> = ArrayList()
        val queryString = "SELECT * FROM $STUDENT_TABLE"
        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                val stdId = cursor.getString(0)
                val stdName = cursor.getString(1)
                val stdAddress = cursor.getString(2)
                val stdPNumber = cursor.getString(3)
                returnData.add(Student(stdName, stdAddress, stdPNumber, stdId))
            } while (cursor.moveToNext())
        }
        cursor.close()
        sqLiteDatabase.close()
        return returnData
    }


    companion object {
        private const val STUDENT_TABLE = "Student_Table"
        private const val ID = "ID"
        private const val NAME = "name"
        private const val ADDRESS = "address"
        private const val P_NUMBER = "pNumber"
    }
}