package com.hiram.curiousapps.catalogoproductos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hiram on 05/10/2016.
 */

public class ProductosSQLiteHelper extends SQLiteOpenHelper {

    String bdSql = "CREATE TABLE Productos (" +
            "codigo TEXT," +
            "nombre TEXT," +
            "precio REAL," +
            "descripcion TEXT," +
            "CONSTRAINT llave PRIMARY KEY (codigo)" +
            ")";

    public ProductosSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(bdSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Productos");

        //Se crea la nueva versión de la tabla
        db.execSQL(bdSql);
    }
}
