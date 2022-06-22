package com.example.uasdpm2011500112

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast


class AdapterDataKampus(
    private val getContext: Context,
    private val customListenItem: ArrayList<DataKampus>
) : ArrayAdapter<DataKampus>(getContext, 0, customListenItem){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val inflateList = (getContext as Activity).layoutInflater
            listLayout = inflateList.inflate(R.layout.activity_data, parent, false)
            holder = ViewHolder()
            with(holder){
                tvNmDosen = listLayout.findViewById(R.id.tvNamaDosen)
                tvNIDN = listLayout.findViewById(R.id.tvNIDN)
                tvProgramStudi = listLayout.findViewById(R.id.tvPROGRAMSTUDI)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        } else

            holder = listLayout.tag as ViewHolder
        val listItem = customListenItem[position]
        holder.tvNmDosen!!.setText(listItem.NmDosen)
        holder.tvNIDN!!.setText(listItem.NIDN)
        holder.tvProgramStudi!!.setText(listItem.ProgramStudi)

        holder.btnEdit!!.setOnClickListener {
            val i = Intent(context, MengentriDataDosen::class.java)
            i.putExtra("kode", listItem.NIDN)
            i.putExtra("nama", listItem.NmDosen)
            i.putExtra("jabatan", listItem.Jabatan)
            i.putExtra("golongan_pangkat", listItem.Golongan)
            i.putExtra("pendidikan", listItem.PendidikanTerakhir)
            i.putExtra("keahlian", listItem.BidangKeahlian)
            i.putExtra("program_studi", listItem.ProgramStudi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener{
            val db = Campuss(context)
            val alb = AlertDialog.Builder(context)
            val kode = holder.tvNIDN!!.text
            val nama = holder.tvNmDosen!!.text
            val programstudi = holder.tvProgramStudi!!.text
            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                    Apakah Anda Yakin Akan Menghapus Data 
                    Ini?
                    
                                 $nama 
                                 [$kode-$programstudi]
                    """.trimIndent())
                setPositiveButton("Ya") { _, _->
                    if (db.hapus("$kode"))
                        Toast.makeText(
                            context,
                            "Data Dosen Berhasil Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data Dosen Gagal Dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak",null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var tvNIDN: TextView? = null
        internal var tvNmDosen: TextView? = null
        internal var tvProgramStudi: TextView? = null
        internal var btnEdit: ImageButton? = null
        internal var btnHapus: ImageButton? = null

    }
}