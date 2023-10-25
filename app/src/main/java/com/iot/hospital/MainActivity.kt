package com.iot.hospital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    lateinit var btn_pasien1 : Button
    lateinit var btn_pasien2 : Button
    lateinit var status1 : TextView
    lateinit var status2 : TextView
    lateinit var ref1 : DatabaseReference
    lateinit var ref2 : DatabaseReference
//    lateinit var window: Window
    var status_global : Int? = null

    fun initComponent(){
        btn_pasien1 = findViewById(R.id.btn_pasien1)
        btn_pasien2 = findViewById(R.id.btn_pasien2)
        status1 = findViewById(R.id.status_1)
        status2 = findViewById(R.id.status_2)
        ref1 = FirebaseDatabase.getInstance().reference.child("pasien01")
        ref2 = FirebaseDatabase.getInstance().reference.child("pasien02")

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        panggilPerawat1()
        panggilPerawat2()
    }

    fun panggilPerawat1(){
        var status:Int = 0
        try{
            ref1.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    status = snapshot.child("status").value.toString().toInt()
//                    status1.text = "status : $status"
                    status_global = status

                    if(status_global == 0){
                        btn_pasien1.text = "Panggil perawat"
                        status1.text = "Belum ada keluhan"

//                        btn_pasien2.text = "Tidak jadi"
//                        status2.text = "Memanggil perawat"
                    }else{
                        btn_pasien1.text = "Tidak jadi"
                        status1.text = "Memanggil perawat"

//                        btn_pasien2.text = "Panggil perawat"
//                        status2.text = "Belum ada keluhan"

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }catch (e:Exception){

        }

        btn_pasien1.setOnClickListener {
            if(status == 0) {
                ref1.child("status").setValue(1)
                ref1.child("pesan").setValue("Pasien Anda meminta bantuan!!!")
                status1.text = "Memanggil perawat"
//                btn_pasien1.text = "Tidak jadi"

            }else {
                ref1.child("status").setValue(0)
                ref1.child("pesan").setValue("Pasien belum ada keluhan")

                status1.text = "Belum ada keluhan"
//                btn_pasien1.text = "Panggil perawat"
            }
        }
    }

    fun panggilPerawat2(){
        var status:Int = 0
        try{
            ref2.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    status = snapshot.child("status").value.toString().toInt()
                    if(status == 0){
                        btn_pasien2.text = "Panggil perawat"
                        status2.text = "Belum ada keluhan"
                    }else{
                        btn_pasien2.text = "Tidak jadi"
                        status2.text = "Memanggil perawat"

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        }catch (e:Exception){
            Toast.makeText(this.applicationContext, "Gagal memuat data", Toast.LENGTH_SHORT)
        }

        btn_pasien2.setOnClickListener {
            if(status == 0) {
                ref2.child("status").setValue(1)
                ref2.child("pesan").setValue("Pasien Anda meminta bantuan!!!")
                status2.text = "Memanggil perawat"
//                btn_pasien2.text = "Tidak jadi"
            }else {
                ref2.child("status").setValue(0)
//                ref2.child("pesan").setValue("Pasien Anda meminta bantuan!!!")
                ref2.child("pesan").setValue("Pasien belum ada keluhan")

                status2.text = "belum ada keluhan"
//                btn_pasien2.text = "Panggil perawat"

            }
        }
    }
}