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
    private List<Anuncio> listaDeAnuncios; // Vai guardar os objetos carregados do banco

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
        lvClassificados = findViewById(R.id.itens_classificados); // ID da sua ListView

        // Botão voltar
        findViewById(R.id.bt_voltar).setOnClickListener(v -> finish());

        // ... (Seu código existente do Spinner vai aqui) ...
        String[] categorias = {
                "Selecione uma categoria", "Eletrônicos", "Veículos",
                "Móveis", "Roupas e acessórios", "Esportes", "Casa e jardim", "Outros"
        };
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapterSpinner);

        // --- LÓGICA PARA CARREGAR A LISTA E CONFIGURAR O CLIQUE ---
        carregarListaDeAnuncios(null); // Carrega tudo inicialmente

        // Adiciona o evento de clique nos itens da ListView
        lvClassificados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Pega o anúncio correspondente à posição clicada na lista
                Anuncio anuncioClicado = listaDeAnuncios.get(position);

                // Cria a intenção de ir para a tela de Detalhes
                Intent intent = new Intent(Compra.this, Detalhes.class);
                // Passa o ID do anúncio como parâmetro extra
                intent.putExtra("ID_ANUNCIO", anuncioClicado.getId());
                startActivity(intent);
            }
        });
    }

    // Método auxiliar para buscar os anúncios no banco e preencher a ListView
    private void carregarListaDeAnuncios(String categoria) {
        AnuncioControle controle = new AnuncioControle(this);
        listaDeAnuncios = controle.lista_anuncios(categoria);

        // Cria uma lista de Strings só para exibir o título e preço na ListView temporariamente
        List<String> exibicao = new ArrayList<>();
        for (Anuncio a : listaDeAnuncios) {
            exibicao.add(a.getTitulo() + " - R$ " + a.getPreco());
        }

        ArrayAdapter<String> adapterList = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, exibicao);
        lvClassificados.setAdapter(adapterList);
    }
}