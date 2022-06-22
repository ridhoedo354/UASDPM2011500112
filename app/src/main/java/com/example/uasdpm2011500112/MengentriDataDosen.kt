package com.example.uasdpm2011500112

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MengentriDataDosen : AppCompatActivity () {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_dosen)

        val modeEdit = intent.hasExtra("kode") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golongan_pangkat")
                && intent.hasExtra("pendidikan") && intent.hasExtra("keahlian")
                && intent.hasExtra("program_studi")
        title = if (modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etNIDN = findViewById<EditText>(R.id.etNIDN)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnjabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolongan = findViewById<Spinner>(R.id.spnGolPangkat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etBidang = findViewById<EditText>(R.id.etBidangKeahlian)
        val etProgram = findViewById<EditText>(R.id.etProgramStudi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val Jabatan = arrayOf("Tenaga Pengajar", "Asisten Ahli", "Lektor", "Guru Besar")
        val Golongan = arrayOf("III/a - Penata Muda", "III/b - Penata Muda Tingkat I",
            "III/c - Penata", "III/d - Penata Tingkat 1", "IV/a - Pembina",
            "IV/b - Pembina Tingkat I", "IV/c - Pembina Utama Muda",
            "IV/d - Pembina Utama Madya", "IV/e - Pembina Utama")

        val adpjabatan = ArrayAdapter(
            this@MengentriDataDosen,
            android.R.layout.simple_spinner_dropdown_item,
            Jabatan
        )
        val adpGolongan = ArrayAdapter(
            this@MengentriDataDosen,
            android.R.layout.simple_spinner_dropdown_item,
            Golongan
        )
        spnjabatan.adapter = adpjabatan
        spnGolongan.adapter = adpGolongan

        if (modeEdit) {
            val kode = intent.getStringExtra("kode")
            val nama = intent.getStringExtra("nama")
            val jabatan = intent.getStringExtra("jabatan")
            val golonganpangkat = intent.getStringExtra("golongan_pangkat")
            val Pendidikan = intent.getStringExtra("pendidikan")
            val Keahlian = intent.getStringExtra("keahlian")
            val programstudi = intent.getStringExtra("program_studi")

            etNIDN.setText(kode)
            etNmDosen.setText(nama)
            spnjabatan.setSelection(Jabatan.indexOf(jabatan))
            spnGolongan.setSelection(Golongan.indexOf(golonganpangkat))
            if (Pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etBidang.setText(Keahlian)
            etProgram.setText(programstudi)
        }
        etNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if ("${etNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty()&&
                (rdS2.isChecked || rdS3.isChecked) && "${etBidang.text}".isNotEmpty()
                && "${etProgram.text}".isNotEmpty()) {
                val db = Campuss(this@MengentriDataDosen)
                db.NIDN = "${etNIDN.text}"
                db.NmDosen = "${etNmDosen.text}"
                db.Jabatan = spnjabatan.selectedItem as String
                db.GolonganPangkat = spnGolongan.selectedItem as String
                db.PendidikanTerakhir = if (rdS2.isChecked) "S2" else "S3"
                db.Keahlian = "${etBidang.text}"
                db.Program_studi = "${etProgram.text}"
                if (if (!modeEdit) db.simpan() else db.ubah("${etNIDN.text}")) {
                    Toast.makeText(
                        this@MengentriDataDosen,
                        "Data Dosen Berhasil Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else
                    Toast.makeText(
                        this@MengentriDataDosen,
                        "Data Dosen Gagal Disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            } else
                Toast.makeText(
                    this@MengentriDataDosen,
                    "Data Dosen Belum Lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}