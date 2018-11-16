package com.local.virpa.virpa.local_db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.local.virpa.virpa.enum.Table
import com.local.virpa.virpa.enum.VirpaDB
import com.local.virpa.virpa.model.Feed
import com.local.virpa.virpa.model.SignIn
import com.squareup.moshi.Moshi


class DatabaseHandler(val context : Context) : SQLiteOpenHelper(context, VirpaDB.DATABASE_NAME.getValue(), null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE " + VirpaDB.USER_INFO.getValue() + " (" +
                Table.UserInfo.ID.getValue() + " VARCHAR(200)," +
                Table.UserInfo.USERNAME.getValue() + " VARCHAR(50)," +
                Table.UserInfo.EMAIL.getValue() + " VARCHAR(50)," +
                Table.UserInfo.FULLNAME.getValue() + " VARCHAR(100)," +
                Table.UserInfo.MOBILE_NUMBER.getValue() + " VARCHAR(20)," +
                Table.UserInfo.FOLLOWERS.getValue() + " INTEGER," +
                Table.UserInfo.BACKGROUND_SUMMARY.getValue() + " TEXT," +
                Table.UserInfo.FILE_PATH.getValue() + " TEXT," +
                Table.UserInfo.CREATED_AT.getValue() + " VARCHAR(100)," +
                Table.UserInfo.UPDATED_AT.getValue() + " VARCHAR(100))"
        )
        db?.execSQL("CREATE TABLE " + VirpaDB.TABLE_REFRESH.getValue() + " (" +
                Table.Refresh.TOKEN.getValue() + " TEXT," +
                Table.Refresh.EXPIRED.getValue() + " TEXT)"
        )

        db?.execSQL("CREATE TABLE " + VirpaDB.USER_DATA.getValue() + " (" +
                Table.UserData.FEED.getValue() + " TEXT," +
                Table.UserData.NOTIFICATION.getValue() + " TEXT," +
                Table.UserData.LOCATION.getValue() + " TEXT)"
        )

        db?.execSQL("CREATE TABLE " + VirpaDB.TABLE_SESSION.getValue() + " (" +
                Table.Session.TOKEN.getValue() + " TEXT," +
                Table.Session.EXPIRED.getValue() + " TEXT)"
        )

        db?.execSQL("CREATE TABLE " + VirpaDB.TABLE_LOCATION.getValue() + " (" +
                Table.UserLocation.LATITUDE.getValue() + " VARCHAR(50)," +
                Table.UserLocation.LONGITUDE.getValue() + " VARCHAR(50)," +
                Table.UserLocation.ADDRESS.getValue() + " VARCHAR(100)," +
                Table.UserLocation.CITY_NAME.getValue() + " VARCHAR(100)," +
                Table.UserLocation.STATE.getValue() + " VARCHAR(100)," +
                Table.UserLocation.COUNTRY_NAME.getValue() + " VARCHAR(100)," +
                Table.UserLocation.POSTAL_CODE.getValue() + " VARCHAR(100))"
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

    fun updateData(table : String, column : String, newData : String) {
        val db = this.readableDatabase
        var query = "Select * from " + table
        var result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(column,newData)
                db.update(table, cv, "", null)
            }while (result.moveToNext())
        }
        db.close()
    }

    fun updateUserData(column : String, newData : String) {
        var table = VirpaDB.USER_DATA.getValue()
        val db = this.readableDatabase
        var query = "Select * from " + table
        var result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(column,newData)
                db.update(table, cv, "", null)
            }while (result.moveToNext())
        }
        db.close()
    }

    fun readFeed() : Feed.Result? {
        var moshi = Moshi.Builder().build()
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * from " + VirpaDB.USER_DATA.getValue(), null)
        var data = ""
        var adapter = moshi.adapter<Feed.Result>(Feed.Result::class.java)
        if(result.moveToFirst()) {
            do {
                data = result.getString(result.getColumnIndex(Table.UserData.FEED.getValue()))
            } while (result.moveToNext())
        }
        db.close()

        if (data != "") {
            return adapter.fromJson(data)
        }
        else {
            return null
        }
    }

    fun insertUserData() : Boolean {
        val db = this.writableDatabase
        var cvUser = ContentValues()

        cvUser.put(Table.UserData.FEED.getValue(), "")
        cvUser.put(Table.UserData.LOCATION.getValue(), "")
        cvUser.put(Table.UserData.NOTIFICATION.getValue(), "")
        var result = db.insert(VirpaDB.USER_DATA.getValue(), null , cvUser)
        db.close()
        return result != (-1).toLong()
    }

    fun insertSignInResult(data : SignIn.Result) : Boolean {
        val db = this.writableDatabase
        var cvUser = ContentValues()

        cvUser.put(Table.UserInfo.ID.getValue(), data.data.user.detail.id)
        cvUser.put(Table.UserInfo.USERNAME.getValue(), data.data.user.detail.userName)
        cvUser.put(Table.UserInfo.EMAIL.getValue(), data.data.user.detail.email)
        cvUser.put(Table.UserInfo.FULLNAME.getValue(), data.data.user.detail.fullname)
        cvUser.put(Table.UserInfo.MOBILE_NUMBER.getValue(), data.data.user.detail.mobileNumber)
        cvUser.put(Table.UserInfo.FOLLOWERS.getValue(), data.data.user.detail.followersCount)
        if (data.data.user.detail.backgroundSummary != null) {
            cvUser.put(Table.UserInfo.BACKGROUND_SUMMARY.getValue(), data.data.user.detail.backgroundSummary!!)
        }
        else {
            cvUser.put(Table.UserInfo.BACKGROUND_SUMMARY.getValue(), "")
        }
        if (data.data.user.profilePicture != null) {
            cvUser.put(Table.UserInfo.FILE_PATH.getValue(), data.data.user.profilePicture!!.filePath)
        }
        else {
            cvUser.put(Table.UserInfo.FILE_PATH.getValue(), "")
        }

        cvUser.put(Table.UserInfo.CREATED_AT.getValue(), data.data.user.detail.createdAt)
        cvUser.put(Table.UserInfo.UPDATED_AT.getValue(), data.data.user.detail.updatedAt)
        var result1 = db.insert(VirpaDB.USER_INFO.getValue(), null, cvUser)

        var cvSession = ContentValues()
        cvSession.put(Table.Session.TOKEN.getValue(), data.data.authorization.sessionToken.token)
        cvSession.put(Table.Session.EXPIRED.getValue(), data.data.authorization.sessionToken.expiredAt)
        var result2 = db.insert(VirpaDB.TABLE_SESSION.getValue(), null, cvSession)


        var cvRefresh = ContentValues()
        cvRefresh.put(Table.Refresh.TOKEN.getValue(), data.data.authorization.refreshToken.token)
        cvRefresh.put(Table.Refresh.EXPIRED.getValue(), data.data.authorization.refreshToken.expiredAt)
        var result3 = db.insert(VirpaDB.TABLE_REFRESH.getValue(), null, cvRefresh)

        var cvUserLocation = ContentValues()
        if (data.data.user.location != null) {
            var locationInfo = data.data.user.location;
            cvUserLocation.put(Table.UserLocation.LATITUDE.getValue(), locationInfo?.latitude.toString())
            cvUserLocation.put(Table.UserLocation.LONGITUDE.getValue(), locationInfo?.longitude.toString())
            cvUserLocation.put(Table.UserLocation.ADDRESS.getValue(), locationInfo?.address)
            cvUserLocation.put(Table.UserLocation.CITY_NAME.getValue(), locationInfo?.cityName)
            cvUserLocation.put(Table.UserLocation.STATE.getValue(), locationInfo?.state)
            cvUserLocation.put(Table.UserLocation.COUNTRY_NAME.getValue(), locationInfo?.countryName)
            cvUserLocation.put(Table.UserLocation.POSTAL_CODE.getValue(), locationInfo?.postalCode)
        }
        else {
            cvUserLocation.put(Table.UserLocation.LATITUDE.getValue(), "0.0")
            cvUserLocation.put(Table.UserLocation.LONGITUDE.getValue(), "0.0")
            cvUserLocation.put(Table.UserLocation.ADDRESS.getValue(), "")
            cvUserLocation.put(Table.UserLocation.CITY_NAME.getValue(), "")
            cvUserLocation.put(Table.UserLocation.STATE.getValue(), "")
            cvUserLocation.put(Table.UserLocation.COUNTRY_NAME.getValue(), "")
            cvUserLocation.put(Table.UserLocation.POSTAL_CODE.getValue(), "0")
        }
        var result4 = db.insert(VirpaDB.TABLE_LOCATION.getValue(), null, cvUserLocation)

        db.close()
        return result1 != (-1).toLong() && result2 != (-1).toLong() && result3 != (-1).toLong() && result4 != (-1).toLong()
    }

    fun checkSignInResult() : Boolean {
        val list : MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * from " + VirpaDB.USER_INFO.getValue()
        var result = db.rawQuery(query, null)
        var checkResult = result.moveToFirst()

        if(result.moveToFirst()) {
            do {
                result.getString(result.getColumnIndex(Table.UserInfo.FULLNAME.getValue()))
                list.add(result.getString(result.getColumnIndex(Table.UserInfo.FULLNAME.getValue())))
            } while (result.moveToNext())
        }

        db.close()
        result.close()

        return list.size != 0
    }

    fun readSignResult() : MutableList<SignIn.Data> {
        val list : MutableList<SignIn.Data> = ArrayList()
        val db = this.readableDatabase
        val result1 = db.rawQuery("SELECT * from " + VirpaDB.USER_INFO.getValue(), null)
        val result2 = db.rawQuery("SELECT * from " + VirpaDB.TABLE_SESSION.getValue(), null)
        val result3 = db.rawQuery("SELECT * from " + VirpaDB.TABLE_REFRESH.getValue(), null)
        val result4 = db.rawQuery("SELECT * from " + VirpaDB.TABLE_LOCATION.getValue(), null)

        if(result1.moveToFirst() && result2.moveToFirst() && result3.moveToFirst() && result4.moveToFirst()) {
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
                var detail = SignIn.Detail(
                        result1.getString(result1.getColumnIndex(Table.UserInfo.ID.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.USERNAME.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.EMAIL.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.FULLNAME.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.MOBILE_NUMBER.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.FOLLOWERS.getValue())).toInt(),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.BACKGROUND_SUMMARY.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.CREATED_AT.getValue())),
                        result1.getString(result1.getColumnIndex(Table.UserInfo.UPDATED_AT.getValue()))
                )
                var profile = SignIn.ProfilePicture(
                        "",
                "",
                "",
                "",
                result1.getString(result1.getColumnIndex(Table.UserInfo.FILE_PATH.getValue())),
                3,
                ""
                )
                var location = SignIn.UserLocation(
                        result4.getString(result4.getColumnIndex(Table.UserLocation.LATITUDE.getValue())).toDouble(),
                        result4.getString(result4.getColumnIndex(Table.UserLocation.LONGITUDE.getValue())).toDouble(),
                        result4.getString(result4.getColumnIndex(Table.UserLocation.ADDRESS.getValue())),
                        result4.getString(result4.getColumnIndex(Table.UserLocation.CITY_NAME.getValue())),
                        result4.getString(result4.getColumnIndex(Table.UserLocation.STATE.getValue())),
                        result4.getString(result4.getColumnIndex(Table.UserLocation.COUNTRY_NAME.getValue())),
                        result4.getString(result4.getColumnIndex(Table.UserLocation.POSTAL_CODE.getValue()))
                )

                var user = SignIn.User(detail, profile,location)
                var data = SignIn.Data(authorization, user)
                list.add(data)
            }while(result1.moveToNext() && result2.moveToNext() && result3.moveToNext() && result4.moveToNext())
        }
        db.close()
        return list
    }
    fun checkData(data : String?) : String {
        var dataHolder = ""
        if (data != null ) {
          dataHolder = data
        }
        return dataHolder
    }
    fun deleteDatabase() {
        val db = this.writableDatabase
        db.delete(VirpaDB.TABLE_REFRESH.getValue(),null,null)
        db.delete(VirpaDB.TABLE_SESSION.getValue(),null,null)
        db.delete(VirpaDB.USER_INFO.getValue(),null,null)
        db.delete(VirpaDB.TABLE_LOCATION.getValue(),null,null)
        db.close()
    }

}
