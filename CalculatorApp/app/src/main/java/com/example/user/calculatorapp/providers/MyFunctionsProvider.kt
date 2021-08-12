package com.example.user.calculatorapp.providers

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.example.user.calculatorapp.database.DatabaseHelper

/**
 * Created by User on 11-08-2021.
 */
class MyFunctionsProvider : ContentProvider(){
    companion object {
        const val PROVIDER_NAME = "com.example.user.calculatorapp"

        // defining content URI
        const val URL = "content://${PROVIDER_NAME}/functions"
        const val TABLE_NAME = DatabaseHelper.FUNCTIONS_TABLE_NAME

        // parsing the content URI
        val CONTENT_URI = Uri.parse(URL)
        const val id = "id"
        const val action = "action"
        const val uriCode = 1
        var uriMatcher: UriMatcher? = null
        private val values: HashMap<String, String>? = null

        // declaring name of the database
        init {

            // to match the content URI
            // every time user access table under content provider
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            // to access whole table
            uriMatcher!!.addURI(
                    PROVIDER_NAME,
                    "functions",
                    uriCode
            )

            // to access a particular row
            // of the table
            uriMatcher!!.addURI(
                    PROVIDER_NAME,
                    "functions/*",
                    uriCode
            )
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher!!.match(uri)) {
            uriCode -> "vnd.android.cursor.dir/functions"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

    // creating the database
    override fun onCreate(): Boolean {
        val context = context
        val dbHelper =
                DatabaseHelper(context)
        db = dbHelper.writableDatabase
        return if (db != null) {
            true
        } else false
    }

    override fun query(
            uri: Uri, projection: Array<String>?, selection: String?,
            selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        // var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_NAME
        when (uriMatcher!!.match(uri)) {
            uriCode -> {//qb.projectionMap = values
//                println("uricode $uriCode")
                qb.setProjectionMap(values)
            }
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }

        val c = qb.query(
                db, projection, selection, selectionArgs, null,
                null, sortOrder
        )
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    // adding data to the database
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val rowID = db!!.insert(TABLE_NAME, "", values)
        if (rowID > 0) {
            val _uri =
                    ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(_uri, null)
            return _uri
        }
        throw SQLiteException("Failed to add a record into $uri")
    }

    override fun update(
            uri: Uri, values: ContentValues?, selection: String?,
            selectionArgs: Array<String>?
    ): Int {
        var count = 0
        count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.update(TABLE_NAME, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun delete(
            uri: Uri,
            selection: String?,
            selectionArgs: Array<String>?
    ): Int {
        var count = 0
        count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.delete(TABLE_NAME, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    // creating object of database
    // to perform query
    private var db: SQLiteDatabase? = null

}
