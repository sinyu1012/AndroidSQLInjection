package com.sinyu.android.sqlinjection.ui

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sinyu.android.sqlinjection.data.DatabaseHelper
import com.sinyu.android.sqlinjection.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper.getInstance(this)
        val db = dbHelper?.writableDatabase
        val values = ContentValues()
        values.put("name", "sinyu")
        values.put("psw", "123456")
        db?.insert("user", null, values)

        binding.login.setOnClickListener {
            if (loginWithSqlInjection(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            ) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginWithSqlInjection(username: String, password: String): Boolean {
        val db = dbHelper?.writableDatabase
        val sql =
            "select *from user where name='$username'and psw='$password'"
        Log.d("login", sql)
        val cursor = db?.rawQuery(sql, null)
        if (cursor?.moveToFirst() == true) {
            cursor.close()
            return true
        }
        return false
    }

    private fun loginWithoutSqlInjection(username: String, password: String): Boolean {
        val db = dbHelper?.writableDatabase
        val sql = "select *from user where name=? and psw=?"
        val cursor = db?.rawQuery(sql, arrayOf(username, password))
        Log.d("login", sql)
        if (cursor?.moveToFirst() == true) {
            cursor.close()
            return true
        }
        return false
    }

}