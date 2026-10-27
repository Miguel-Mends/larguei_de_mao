package com.example.larguei_de_mao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AnuncioControle {

    private conectadb banco;

    public AnuncioControle(Context contexto) {
        this.banco = new conectadb(contexto);
    }

    // Usa ContentValues: aceita apóstrofo no título/descrição sem quebrar o SQL
    public boolean insere_anuncio(Anuncio anuncio) {
        try {
            SQLiteDatabase db = banco.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("categoria", anuncio.getCategoria());
            v.put("titulo", anuncio.getTitulo());
            v.put("preco", anuncio.getPreco());
            v.put("descricao", anuncio.getDescricao());
            v.put("contato", anuncio.getContato());
            v.put("data", anuncio.getData());
            long id = db.insert("anuncio", null, v);
            db.close();
            return id != -1;
        } catch (Exception ex) {
            return false;
        }
    }

    public void apaga_anuncio(Anuncio anuncio) {
        try {
            SQLiteDatabase db = banco.getWritableDatabase();
            db.delete("anuncio", "id_anuncio = ?", new String[]{String.valueOf(anuncio.getId())});
            db.close();
        } catch (Exception ex) {
        }
    }

    // Lista para a tela Compra: só precisa de id, título e preço
    public List<Anuncio> lista_anuncios(String categoria) {
        List<Anuncio> lista = new ArrayList<>();
        try {
            SQLiteDatabase bd = banco.getReadableDatabase();
            Cursor c;
            if (categoria == null || categoria.startsWith("Selecione")) {
                c = bd.rawQuery("SELECT id_anuncio, titulo, preco FROM anuncio ORDER BY id_anuncio DESC", null);
            } else {
                c = bd.rawQuery("SELECT id_anuncio, titulo, preco FROM anuncio WHERE categoria = ? ORDER BY id_anuncio DESC",
                        new String[]{categoria});
            }
            while (c.moveToNext()) {
                Anuncio a = new Anuncio();
                a.setId(c.getInt(0));
                a.setTitulo(c.getString(1));
                a.setPreco(c.getDouble(2));
                lista.add(a);
            }
            c.close();
            bd.close();
        } catch (Exception ex) {
        }
        return lista;
    }

    // Busca um anúncio o para a tela Detalhes
    public Anuncio busca_anuncio(int id) {
        try {
            SQLiteDatabase bd = banco.getReadableDatabase();
            Cursor c = bd.rawQuery(
                    "SELECT id_anuncio, categoria, titulo, preco, descricao, contato, data FROM anuncio WHERE id_anuncio = ?",
                    new String[]{String.valueOf(id)});
            Anuncio a = null;
            if (c.moveToFirst()) {
                a = new Anuncio();
                a.setId(c.getInt(0));
                a.setCategoria(c.getString(1));
                a.setTitulo(c.getString(2));
                a.setPreco(c.getDouble(3));
                a.setDescricao(c.getString(4));
                a.setContato(c.getString(5));
                a.setData(c.getString(6));
            }
            c.close();
            bd.close();
            return a;
        } catch (Exception ex) {
            return null;
        }
    }
}