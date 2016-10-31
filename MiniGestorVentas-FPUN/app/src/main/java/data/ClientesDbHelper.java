package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static data.ConstantsDB.*;
import static data.ConstantsDB.CLI_NOMBRE;
import static data.ConstantsDB.TABLA_CLIENTES;
//import data.clientesContract.clientesEntry;

/**
 * Created by appleyae on 19/10/2016.
 */

public class ClientesDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="gestorventas.db";

    public ClientesDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*@Override
    public void onCreate (SQLiteDatabase sqLiteDatabase){
        //comandos sql
    }*/

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE" + TABLA_CLIENTES + " ( "
        + CLI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + CLI_NOMBRE + "TEXT NOT NULL,"
        + CLI_APELLIDO + "TEXT NOT NULL,"
        + CLI_DIRECCION + "TEXT NOT NULL,"
        + CLI_TELEFONO + "TEXT NOT NULL,"
        + CLI_CEDULA + "TEXT NOT NULL,"
        + "UNIQUE ( " + CLI_CEDULA + "))"
        );

        /*ContentValues values = new ContentValues();
        values.put(clientesEntry.NOMBRE, "Juan");
        values.put(clientesEntry.APELLIDO, "Perez");
        values.put(clientesEntry.TELEFONO, "0971444888");
        values.put(clientesEntry.CEDULA, "2444555");
        values.put(clientesEntry.DIRECCION, "11 de setiembre - Fernando de la Mora");
        db.insert */
    }




}
