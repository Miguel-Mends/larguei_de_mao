package com.example.larguei_de_mao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class conectadb extends SQLiteOpenHelper {

    String tab_usr = "CREATE TABLE IF NOT EXISTS usuario (" +
            "id_usr INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, senha TEXT)";

    String tab_anuncio = "CREATE TABLE IF NOT EXISTS anuncio (" +
            "id_anuncio INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "categoria TEXT, " +
            "titulo TEXT, " +
            "preco REAL, " +
            "descricao TEXT, " +
            "contato TEXT, " +
            "data TEXT)";

    conectadb(Context contexto) {
        super(contexto, "osdb", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tab_usr);
        sqLiteDatabase.execSQL(tab_anuncio);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String RECRIA_USR = "DROP TABLE IF EXISTS usuario";
        String RECRIA_ANUNCIO = "DROP TABLE IF EXISTS anuncio";

        sqLiteDatabase.execSQL(RECRIA_USR);
        sqLiteDatabase.execSQL(RECRIA_ANUNCIO);

        onCreate(sqLiteDatabase);
    }
}