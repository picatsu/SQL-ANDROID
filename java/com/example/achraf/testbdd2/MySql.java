package com.example.achraf.testbdd2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySql extends SQLiteOpenHelper {

	public MySql(Context context) {
		super(context, "test_android.db", null, 1);
	}
	// constructeur

	//https://www.sqlite.org/datatype3.html
		
	@Override
	public void onCreate(SQLiteDatabase database) {
		String sql = "create table utilisateur (id integer primary key autoincrement, usr text, pwd text);";
		database.execSQL(sql);//cree la base
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		database.execSQL("DROP TABLE IF EXISTS utilisateur");
		onCreate(database);
	}

}
