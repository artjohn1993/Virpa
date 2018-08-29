package com.local.virpa.virpa.local_db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.local.virpa.virpa.enum.Table
import com.local.virpa.virpa.enum.VirpaDB
import com.local.virpa.virpa.model.SignIn


class DatabaseHandler(val context : Context) : SQLiteOpenHelper(context, VirpaDB.DATABASE_NAME.getValue(), null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + VirpaDB.USER_INFO.getValue() + " (" +
                Table.UserInfo.ID.getValue() + " VARCHAR(200)," +
                Table.UserInfo.USERNAME.getValue() + " VARCHAR(50)," +
                Table.UserInfo.EMAIL.getValue() + " VARCHAR(50)," +
                Table.UserInfo.FULLNAME.getValue() + " VARCHAR(100)," +
                Table.UserInfo.MOBILE_NUMBER.getValue() + " VARCHAR(20)," +
                Table.UserInfo.CREATED_AT.getValue() + " VARCHAR(100)," +
                Table.UserInfo.UPDATED_AT.getValue() + " VARCHAR(100))"
        )
        db?.execSQL("CREATE TABLE " + VirpaDB.TABLE_REFRESH.getValue() + " (" +
                Table.Refresh.TOKEN.getValue() + " TEXT," +
                Table.Refresh.EXPIRED.getValue() + " TEXT)"
        )
        db?.execSQL("CREATE TABLE " + VirpaDB.TABLE_SESSION.getValue() + " (" +
                Table.Session.TOKEN.getValue() + " TEXT," +
                Table.Session.EXPIRED.getValue() + " TEXT)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
    fun updateRefresh(token : String, expiredAt : String) {
        val db = this.readableDatabase
        var query = "Select *  from " + VirpaDB.TABLE_SESSION.getValue()
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do{
                var cv = ContentValues()
                cv.put(Table.Session.TOKEN.getValue(),token)
                cv.put(Table.Session.EXPIRED.getValue(),expiredAt)
                db.update(VirpaDB.TABLE_SESSION.getValue(),cv, "",null)
            }while (result.moveToNext())
        }
        db.close()
    }

    fun insertSignInResult(data : SignIn.Result) : Boolean {
        val db = this.writableDatabase
        var cvUser = ContentValues()
        cvUser.put(Table.UserInfo.ID.getValue(), data.data.user.id)
        cvUser.put(Table.UserInfo.USERNAME.getValue(), data.data.user.userName)
        cvUser.put(Table.UserInfo.EMAIL.getValue(), data.data.user.email)
        cvUser.put(Table.UserInfo.FULLNAME.getValue(), data.data.user.fullname)
        cvUser.put(Table.UserInfo.MOBILE_NUMBER.getValue(), data.data.user.mobileNumber)
        cvUser.put(Table.UserInfo.CREATED_AT.getValue(), data.data.user.createdAt)
        cvUser.put(Table.UserInfo.UPDATED_AT.getValue(), data.data.user.updatedAt)
        var result1 = db.insert(VirpaDB.USER_INFO.getValue(), null, cvUser)

        var cvSession = ContentValues()
        cvSession.put(Table.Session.TOKEN.getValue(), data.data.authorization.sessionToken.token)
        cvSession.put(Table.Session.EXPIRED.getValue(), data.data.authorization.sessionToken.expiredAt)
        var result2 = db.insert(VirpaDB.TABLE_SESSION.getValue(), null, cvSession)


        var cvRefresh = ContentValues()
        cvRefresh.put(Table.Refresh.TOKEN.getValue(), data.data.authorization.refreshToken.token)
        cvRefresh.put(Table.Refresh.EXPIRED.getValue(), data.data.authorization.refreshToken.expiredAt)
        var result3 = db.insert(VirpaDB.TABLE_REFRESH.getValue(), null, cvRefresh)

        db.close()
        return result1 != (-1).toLong() && result2 != (-1).toLong() && result3 != (-1).toLong()
    }

    fun checkSignInResult() : Boolean {
        val db = this.readableDatabase
        val query = "SELECT * from " + VirpaDB.USER_INFO.getValue()
        var result = db.rawQuery(query, null)
        return result != null && result.moveToFirst()
    }

    fun readSignResult() : MutableList<SignIn.Data> {
        val list : MutableList<SignIn.Data> = ArrayList()
        val db = this.readableDatabase
        val result1 = db.rawQuery("SELECT * from " + VirpaDB.USER_INFO.getValue(), null)
        val result2 = db.rawQuery("SELECT * from " + VirpaDB.TABLE_SESSION.getValue(), null)
        val result3 = db.rawQuery("SELECT * from " + VirpaDB.TABLE_REFRESH.getValue(), null)

        if(result1.moveToFirst() && result2.moveToFirst() && result3.moveToFirst()) {
            do {
                var session = SignIn.SessionToken(
                        result2.getString(result2.getColumnIndex(Table.Session.TOKEN.getValue())),
                        result2.getString(result2.getColumnIndex(Table.Session.EXPIRED.getValue()))
                )
                var refresh = SignIn.RefreshToken(
                        result3.getString(result3.getColumnIndex(Table.Refresh.TOKEN.getValue())),
                        result3.getString(result3.getColumnIndex(Table.Refresh.EXPIRED.getValue()))
                )
                var authorization = SignIn.Authorization(session, refresh)
                var user = SignIn.User(
                        result1.getString(result1.getColumnIndex(Table.UserInfo.ID.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.USERNAME.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.EMAIL.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.FULLNAME.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.MOBILE_NUMBER.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.CREATED_AT.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.UPDATED_AT.getValue()))
                )
                var data = SignIn.Data(authorization, user)
                list.add(data)
            }while(result1.moveToNext() && result2.moveToNext() && result3.moveToNext())
        }
        db.close()
        return list
    }

    fun deleteDatabase() {
        val db = this.writableDatabase
        db.delete(VirpaDB.TABLE_REFRESH.getValue(),null,null)
        db.delete(VirpaDB.TABLE_SESSION.getValue(),null,null)
        db.delete(VirpaDB.USER_INFO.getValue(),null,null)
    }

}