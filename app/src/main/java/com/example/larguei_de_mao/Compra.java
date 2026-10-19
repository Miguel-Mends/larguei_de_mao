package com.example.larguei_de_mao;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Compra extends AppCompatActivity {

    private Spinner spCategoria;
    private ListView lvClassificados;
    private List<Anuncio> listaDeAnuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compra);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spCategoria = findViewById(R.id.sp_categoria);
        lvClassificados = findViewById(R.id.itens_classificados);

        findViewById(R.id.bt_voltar).setOnClickListener(v -> finish());

        String[] categorias = {
                "Selecione uma categoria",
                "Eletrônicos",
                "Veículos",
                "Móveis",
                "Roupas e acessórios",
                "Esportes",
                "Casa e jardim",
                "Outros"
        };
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapterSpinner);

        // --- AQUI ESTÁ A CORREÇÃO: Escuta a seleção de categoria no Spinner ---
        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSelecionada = categorias[position];
                // Recarrega a lista filtrando pela categoria escolhida
                carregarListaDeAnuncios(categoriaSelecionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Clique no item da ListView para ir para a tela de Detalhes
        lvClassificados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listaDeAnuncios != null && position < listaDeAnuncios.size()) {
                    Anuncio anuncioClicado = listaDeAnuncios.get(position);
                    Intent intent = new Intent(Compra.this, Detalhes.class);
                    intent.putExtra("ID_ANUNCIO", anuncioClicado.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private void carregarListaDeAnuncios(String categoria) {
        AnuncioControle controle = new AnuncioControle(this);
        listaDeAnuncios = controle.lista_anuncios(categoria);

        List<String> exibicao = new ArrayList<>();
        if (listaDeAnuncios != null) {
            for (Anuncio a : listaDeAnuncios) {
                exibicao.add(a.getTitulo() + " - R$ " + String.format("%.2f", a.getPreco()));
            }
        }

        ArrayAdapter<String> adapterList = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, exibicao);
        lvClassificados.setAdapter(adapterList);
    }
}