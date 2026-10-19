package com.example.larguei_de_mao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class usuariocontrole {

    private conectadb banco;

    public usuariocontrole(Context contexto) {
        this.banco = new conectadb(contexto);
    }

    // Retorna true se salvou, false se deu erro
    public boolean insere_usuario(usuario usr) {
        try {
            SQLiteDatabase db = banco.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("login", usr.getLogin());
            valores.put("senha", usr.getSenha());
            long id = db.insert("usuario", null, valores);
            db.close();
            return id != -1;
        } catch (Exception ex) {
            return false;
        }
    }

    // Verifica se já existe um usuário com esse login
    public boolean existe_login(String login) {
        try {
            SQLiteDatabase db = banco.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT 1 FROM usuario WHERE login = ?", new String[]{login});
            boolean existe = c.moveToFirst();
            c.close();
            db.close();
            return existe;
        } catch (Exception ex) {
            return false;
        }
    }

    // Verifica login + senha no banco
    public boolean valida_login(String login, String senha) {
        try {
            SQLiteDatabase db = banco.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT 1 FROM usuario WHERE login = ? AND senha = ?",
                    new String[]{login, senha});
            boolean ok = c.moveToFirst();
            c.close();
            db.close();
            return ok;
        } catch (Exception ex) {
            return false;
        }
    }

    public void apaga_usuario(usuario usr) {
        try {
            SQLiteDatabase db = banco.getWritableDatabase();
            db.delete("usuario", "id_usr = ?", new String[]{String.valueOf(usr.getId())});
            db.close();
        } catch (Exception ex) {
        }
    }

    public void atualiza_usuario(usuario usr) {
        try {
            SQLiteDatabase db = banco.getWritableDatabase();
            ContentValues valores = new ContentValues();
            valores.put("login", usr.getLogin());
            valores.put("senha", usr.getSenha());
            db.update("usuario", valores, "id_usr = ?", new String[]{String.valueOf(usr.getId())});
            db.close();
        } catch (Exception ex) {
        }
    }

    public List<String> Consulta_todos_usuarios() {
        List<String> lista = new ArrayList<>();
        try {
            SQLiteDatabase bd = banco.getReadableDatabase();
            Cursor c = bd.rawQuery("SELECT id_usr, login, senha FROM usuario ORDER BY login", null);
            if (c.moveToFirst()) {
                do {
                    lista.add("id :" + c.getString(0) + "\nLogin :" + c.getString(1)
                            + "\nSenha :" + c.getString(2));
                } while (c.moveToNext());
            }
            c.close();
            bd.close();
            return lista;
        } catch (Exception ex) {
            return null;
        }
    }
}