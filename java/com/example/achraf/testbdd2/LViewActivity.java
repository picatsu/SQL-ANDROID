package com.example.achraf.testbdd2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ec.edu.ute.dsii.R;

public class LViewActivity extends Activity {
	private MySql bdd;
	private SQLiteDatabase database;
	private Cursor cursor;
	private ListView lv;
	private SimpleCursorAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lview);
		
		bdd = new MySql(this);
		database = bdd.getWritableDatabase();
		//para que un CursorAdapter funcione, el resultado debe incluir una columna "_id"
		//así que "id as _id" es necesario solo por que vamos a usar SimpleCursorAdapter
		//ver: http://developer.android.com/reference/android/widget/CursorAdapter.html
		cursor = database.rawQuery("select id as _id, usr, pwd from utilisateur ", null);
		
		//información a presentar en la lista
		String[] from = {"_id", "usr", "pwd"}; //nombres de campos
		int [] to =  {R.id.txtid, R.id.txtusr, R.id.txtpwd}; //una vista por campo
		
		
		adapter = new SimpleCursorAdapter(this, R.layout.view_registro, cursor, from, to, 0);
		lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(adapter);

	}

	public void crearRegistro(View view) {
		EditText usr = (EditText) findViewById(R.id.identificateur);
		EditText pwd = (EditText) findViewById(R.id.Mots);
		
		String sql = String.format("insert into usuarios(usr, pwd) values('%s', '%s')",
				usr.getText().toString(), pwd.getText().toString());
		
		database.execSQL(sql);
		
		usr.setText("");
		pwd.setText("");
			
		//actualiza el cursor y la lista
		adapter.changeCursor(database.rawQuery("select id as _id, usr, pwd from utilisateur ", null));
		//cursor.requery(); //en desuso
	}
	
}
