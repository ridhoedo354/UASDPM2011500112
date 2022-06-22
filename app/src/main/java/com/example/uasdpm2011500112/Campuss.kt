package com.example.uasdpm2011500112

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Campuss (context: Context): SQLiteOpenHelper(context,"Campuss", null, 1){
    var NIDN = ""
    var NmDosen = ""
    var Jabatan = ""
    var GolonganPangkat = ""
    var PendidikanTerakhir = ""
    var Keahlian = ""
    var Program_studi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            NIDN char(10) primary key,
            Nm_Dosen varchar (50) not null,
            Jabatan varchar(15) not null,
            golongan_pangkat varchar(30) not null,
            pendidikan varchar(7) not null,
            keahlian varchar(30) not null,
            program_studi varchar(50) not null
            )
            """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("NIDN",NIDN)
            put("nm_dosen",NmDosen)
            put("jabatan", Jabatan)
            put("golongan_pangkat",GolonganPangkat)
            put("pendidikan",PendidikanTerakhir)
            put("keahlian",Keahlian)
            put("program_studi",Program_studi)

        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }
    fun ubah (kode: String): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv){
            put("nm_dosen",NmDosen)
            put("jabatan",Jabatan)
            put("golongan_pangkat",GolonganPangkat)
            put("pendidikan", PendidikanTerakhir)
            put("keahlian", Keahlian)
            put("program_studi", Program_studi)
        }
        val cmd = db.update(tabel, cv, "NIDN = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }

    fun hapus(kode: String) : Boolean{
        val db = writableDatabase
        val cmd = db.delete(tabel,"NIDN = ?", arrayOf(kode))
        return cmd != -1
    }
    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }
}