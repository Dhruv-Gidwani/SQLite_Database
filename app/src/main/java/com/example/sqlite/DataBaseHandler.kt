package com.example.sqlite


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


const val databaseName = "STUDENT_DATABASE"
const val tableName = "ENROLLED"
const val ID = "ID"
const val NAME = "NAME"
const val AGE = "AGE"




class DataBaseHandler(var context: Context) : SQLiteOpenHelper(context, databaseName,null,1) {
    override fun onCreate(p0: SQLiteDatabase?) {

     val createTable = "CREATE TABLE " + tableName +" (" +
             ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
             NAME + " VARCHAR(250)," +
             AGE + " INTEGER)"

        p0?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertData(user : User){
        val p0 = this.writableDatabase
        val cv = ContentValues()

        cv.put(NAME,user.name)
        cv.put(AGE,user.age)

        val result = p0.insert(tableName,null,cv)

        if(result == -1.toLong())
        {
            Toast.makeText(context,"Falied!!!",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
        }
    }

    fun readData() : MutableList<User>{
        val list : MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $tableName"
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                val user = User()
                user.id = result.getString(0).toInt()
                user.name = result.getString(1)
                user.age = result.getString(2).toInt()
                list.add(user)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }

    fun deleteData () {
        val db = this.writableDatabase
        db.delete(tableName,null, null)
        db.close()
    }


    fun updateData() {
        val db = this.writableDatabase
        val query = "Select * from $tableName"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val cv = ContentValues()
                cv.put(AGE, (result.getInt(2) + 1))
                db.update(tableName, cv, "$ID = ? AND $NAME = ?", arrayOf(result.getString(0), result.getString(1)))
            } while (result.moveToNext())
        }
    }
}