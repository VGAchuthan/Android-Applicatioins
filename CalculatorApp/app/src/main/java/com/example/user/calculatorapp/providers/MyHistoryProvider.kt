//package com.example.user.calculatorapp.providers
//
//import android.content.*
//import android.database.Cursor
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteException
//import android.database.sqlite.SQLiteOpenHelper
//import android.database.sqlite.SQLiteQueryBuilder
//import android.net.Uri
//import android.util.Log
//import com.example.user.calculatorapp.database.DatabaseHelper
//
///**
// * Created by User on 06-08-2021.
// */
//class MyHistoryProvider : ContentProvider() {
//    companion object {
//        // defining authority so that other application can access it
//        const val PROVIDER_NAME = "com.example.user.calculatorapp"
//
//        // defining content URI
//        const val URL = "content://$PROVIDER_NAME/operations"
//        const val TABLE_NAME = DatabaseHelper.OPERATION_TABLE_NAME
//
//        // parsing the content URI
//        val CONTENT_URI = Uri.parse(URL)
//        const val id = "id"
//        const val name = "name"
//        const val uriCode = 1
//        var uriMatcher: UriMatcher? = null
//        private val values: HashMap<String, String>? = null
//
//        // declaring name of the database
//        init {
//            Log.e("IN PROVIDER CLASS","INIT")
//
//            // to match the content URI
//            // every time user access table under content provider
//            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
//
//            // to access whole table
//            uriMatcher!!.addURI(
//                    PROVIDER_NAME,
//                    "operations",
//                    uriCode
//            )
//
//            // to access a particular row
//            // of the table
//            uriMatcher!!.addURI(
//                    PROVIDER_NAME,
//                    "operations/*",
//                    uriCode
//            )
//        }
//    }
//
//    override fun getType(uri: Uri): String? {
//        return when (uriMatcher!!.match(uri)) {
//            uriCode -> "vnd.android.cursor.dir/operations"
//            else -> throw IllegalArgumentException("Unsupported URI: $uri")
//        }
//    }
//
//    // creating the database
//    override fun onCreate(): Boolean {
//        val context = context
//        val dbHelper =
//                DatabaseHelper(context)
//        db = dbHelper.writableDatabase
//        return if (db != null) {
//            true
//        } else false
//    }
//
//    override fun query(
//            uri: Uri, projection: Array<String>?, selection: String?,
//            selectionArgs: Array<String>?, sortOrder: String?
//    ): Cursor? {
//        // var sortOrder = sortOrder
//        val qb = SQLiteQueryBuilder()
//        qb.tables = TABLE_NAME
//        when (uriMatcher!!.match(uri)) {
//            uriCode -> {//qb.projectionMap = values
////                println("uricode $uriCode")
//                qb.setProjectionMap(values)
//            }
//            else -> throw IllegalArgumentException("Unknown URI $uri")
//        }
//
//        val c = qb.query(
//                db, projection, selection, selectionArgs, null,
//                null, sortOrder
//        )
//        c.setNotificationUri(context!!.contentResolver, uri)
//        return c
//    }
//
//    // adding data to the database
//    override fun insert(uri: Uri, values: ContentValues?): Uri? {
//        val rowID = db!!.insert(TABLE_NAME, "", values)
//        if (rowID > 0) {
//            val _uri =
//                    ContentUris.withAppendedId(CONTENT_URI, rowID)
//            context!!.contentResolver.notifyChange(_uri, null)
//            return _uri
//        }
//        throw SQLiteException("Failed to add a record into $uri")
//    }
//
//    override fun update(
//            uri: Uri, values: ContentValues?, selection: String?,
//            selectionArgs: Array<String>?
//    ): Int {
//        var count = 0
//        count = when (uriMatcher!!.match(uri)) {
//            uriCode -> db!!.update(TABLE_NAME, values, selection, selectionArgs)
//            else -> throw IllegalArgumentException("Unknown URI $uri")
//        }
//        context!!.contentResolver.notifyChange(uri, null)
//        return count
//    }
//
//    override fun delete(
//            uri: Uri,
//            selection: String?,
//            selectionArgs: Array<String>?
//    ): Int {
//        var count = 0
//        count = when (uriMatcher!!.match(uri)) {
//            uriCode -> db!!.delete(TABLE_NAME, selection, selectionArgs)
//            else -> throw IllegalArgumentException("Unknown URI $uri")
//        }
//        context!!.contentResolver.notifyChange(uri, null)
//        return count
//    }
//
//    // creating object of database
//    // to perform query
//    private var db: SQLiteDatabase? = null
//
//    // creating a database
//
//}