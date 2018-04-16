package com.example.achraf.testbdd2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import ec.edu.ute.dsii.R;

public class MainActivity extends Activity {
	private MySql bdd;
	private SQLiteDatabase database;
	private Cursor cursor;
	private TextView tv;
	private TextView tvfinal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//tv = (TextView) findViewById(R.id.tv);
		//tvfinal = (TextView) findViewById(R.id.Motschercher);


		bdd = new MySql(this);
		database = bdd.getWritableDatabase();

	}

	@Override
	protected void onResume() {
		super.onResume();
		leerRegistros();
	}

	public void creerregistre(View view) {
		EditText usr = (EditText) findViewById(R.id.identificateur);
		EditText pwd = (EditText) findViewById(R.id.Mots);
		
		String sql = String.format("insert into utilisateur(usr, pwd) values('%s', '%s')",
				usr.getText().toString(), pwd.getText().toString());
		
		database.execSQL(sql);

		usr.setText("");
		pwd.setText("");


        android.content.Context context = getApplicationContext();
        CharSequence text = "Ajouté ! ";
        int duration = android.widget.Toast.LENGTH_SHORT;

        android.widget.Toast toast = android.widget.Toast.makeText(context, text, duration);
        toast.setGravity(android.view.Gravity.BOTTOM|android.view.Gravity.LEFT, 0, 0);
        toast.show();
        InputMethodManager inputManager = (InputMethodManager)
                                  getSystemService(android.content.Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                     InputMethodManager.HIDE_NOT_ALWAYS);
		//leerRegistros();
	}

	//Ejemplifica cómo leer un cursor
	private void leerRegistros() {
	    Cursor cursor = database.rawQuery("select * from utilisateur", null);
		String datos = "id; usr; pwd\n";



		int filas = cursor.getCount();

		for(int i = 0; i < filas; i++) {

			cursor.moveToPosition(i);

			datos = datos + String.format("%d; %s; %s\n",
					cursor.getInt(0),
					cursor.getString(1),
					cursor.getString(2));
		}
		
		cursor.close();

	}

	/*public  void chercher(View view){
		EditText motchercher  = (EditText) findViewById(R.id.Motschercher);
		String a = motchercher.getText().toString();
		Cursor cursor = database.rawQuery("select * from utilisateur", null);
		String datos = "resultat : ";



		ArrayList<String> marksArrayList = this.transformer();
		for(String striiing : marksArrayList){
			if(striiing.contains(a))
				datos += a;
		}
		cursor.close();

		tvfinal.setText(datos);
	}
*/
	public void showliste(View view) {
		Intent intent = new Intent(this, LViewActivity.class);
		startActivity(intent);
	}

	public ArrayList<String> transformer(){
		ArrayList<String> marksArrayList = new ArrayList<String>();



		Cursor c = database.rawQuery("select * from utilisateur", null);

		c.moveToFirst();
		while(!c.isAfterLast()){

			if(c.getColumnIndex("subject")!=0){
				String dbName = c.getString(c.getColumnIndex("name"));
				String dbValue = c.getString(c.getColumnIndex("value"));

				marksArrayList.add(new String(dbName+" "+" "+dbValue));
			}
			c.moveToNext();
		}
		return marksArrayList;


	}


}
