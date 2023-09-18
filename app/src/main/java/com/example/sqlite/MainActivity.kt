package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etname = findViewById<EditText>(R.id.etName)
        val etage = findViewById<EditText>(R.id.etAge)

        val btn = findViewById<Button>(R.id.btnsubmit)

        val btnRead = findViewById<Button>(R.id.btnread)
        val btnUpdate = findViewById<Button>(R.id.btnupdate)
        val btnDelete = findViewById<Button>(R.id.btndelete)

        val tvResult = findViewById<TextView>(R.id.tvresult)


        val context =this
        val db = DataBaseHandler(context)
        btn.setOnClickListener {
            if ( etname.text.toString().isNotEmpty() &&
                etage.text.toString().isNotEmpty() ) {
                val user = User(
                    etname.text.toString(),
                    etage.text.toString().toInt())

                db.insertData(user)
            }
            else {
                Toast.makeText(context, "Please Fill All Your Details", Toast.LENGTH_SHORT).show()

            }
        }

        btnRead.setOnClickListener {
            var data = db.readData()
            tvResult.text = ""
            for (i in 0..(data.size - 1)) {
                tvResult.append(data.get(i).id.toString() + " " + data.get(i).name + " " + data.get(i,).age.toString() + "\n")
            }
        }

        btnUpdate.setOnClickListener {
            db.updateData()
            btnRead.performClick()
        }
        btnDelete.setOnClickListener {
            db.deleteData()
            btnRead.performClick()
        }
    }
}