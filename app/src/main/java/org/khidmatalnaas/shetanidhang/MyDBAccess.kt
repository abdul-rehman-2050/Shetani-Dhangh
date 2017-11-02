package org.khidmatalnaas.shetanidhang

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper
import android.content.Context


/**
 * Created by abdulrehman on 02/11/2017.
 */
class MyDBAccess(context:Context) {


    val sqldb = SQLiteAssetHelper(context,"test_final.db",null,1).writableDatabase


    fun getDescription(title: String):String{
        val c = sqldb?.rawQuery("SELECT * FROM book1 WHERE title=\'$title\'", null)

        val list = (1 .. c!!.count).map {
            c.moveToNext()
            c.getString(c.getColumnIndex("body"))
        }
        c.close()


        return list[0]
    }

    fun getAllTitlesFromTableBook1(): ArrayList<String>? {

        val c = sqldb?.rawQuery("SELECT * FROM book1", null)
        val list = (1 .. c!!.count).map {
            c.moveToNext()
            c.getString(c.getColumnIndex("title"))
        }
        c.close()
        return ArrayList(list)
    }



    fun closeMyDB() {
        if (sqldb != null && sqldb.isOpen()) {
            sqldb.close()
        }
    }


}