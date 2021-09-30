package com.ssacproject.secondweek

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SQLiteHelper(context: Context, name: String, version: Int) :
                SQLiteOpenHelper(context, name, null, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val create ="create table record (" +
                    "no integer primary key, " +
                    "title text, " +
                    "poster text, " +
                    "progress integer, " +
                    "duration integer" +
                    ")"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertRecord(record: Record) {
        val values = ContentValues()
        values.put("title", record.title)
        values.put("poster", record.poster)
        values.put("progress", record.progress)
        values.put("duration", record.duration)

        val wd = writableDatabase
        wd.insert("record", null, values)
        wd.close()
    }

    fun selectItemRecord(inputTitle: String): Int {
        val select = "select * from record"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)
        var flag = -1
        while (cursor.moveToNext()) {
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            if (title.equals(inputTitle)) {
                flag = no.toInt()
                break
            }
        }
        cursor.close()
        rd.close()
        return flag
    }

    fun selectRecord(): MutableList<Record> {
        val list = mutableListOf<Record>()
        val select = "select * from record"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)
        while (cursor.moveToNext()) {
            val no = cursor.getLong(cursor.getColumnIndex("no"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val poster = cursor.getString(cursor.getColumnIndex("poster"))
            val progress = cursor.getLong(cursor.getColumnIndex("progress"))
            val duration = cursor.getLong(cursor.getColumnIndex("duration"))
            list.add(Record(no, title, poster, progress, duration))
        }
        cursor.close()
        rd.close()
        return list
    }

    fun updateRecord(no: Int, record: Record) {
        val values = ContentValues()
        values.put("title", record.title)
        values.put("poster", record.poster)
        values.put("progress", record.progress)
        values.put("duration", record.duration)

        val wd = writableDatabase
        wd.update("record", values, "no = ${no}", null)
        wd.close()
    }

    fun deleteRecord(record: Record) {
        val delete = "delete from record where no = ${record.no}"
        val db = writableDatabase
        db.execSQL(delete)
        db.close()
    }
}

data class Record(var no: Long?, var title: String, var poster: String, var progress: Long, var duration: Long)