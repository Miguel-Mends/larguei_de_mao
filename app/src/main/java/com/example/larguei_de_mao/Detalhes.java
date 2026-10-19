package com.example.larguei_de_mao;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Detalhes extends AppCompatActivity {

    private TextView tvNome, tvPreco, tvDescricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Vincular os elementos usando os IDs exatos do seu activity_detalhes.xml
        tvNome = findViewById(R.id.tv_nome_produto);
        tvPreco = findViewById(R.id.tv_preco_produto);
        tvDescricao = findViewById(R.id.tv_descricao_produto);

        // Botão voltar do cabeçalho
        findViewById(R.id.bt_voltar).setOnClickListener(v -> finish());

        // 2. Receber o ID enviado pela tela de Compra
        int idAnuncio = getIntent().getIntExtra("ID_ANUNCIO", -1);

        if (idAnuncio != -1) {
            // 3. Usa o controle para buscar os dados completos no banco de dados
            AnuncioControle controle = new AnuncioControle(this);
            Anuncio anuncio = controle.busca_anuncio(idAnuncio);

            // 4. Preenche a tela
            if (anuncio != null) {
                tvNome.setText(anuncio.getTitulo());
                tvPreco.setText(String.format("R$ %.2f", anuncio.getPreco()));
                tvDescricao.setText(anuncio.getDescricao());
            } else {
                Toast.makeText(this, "Anúncio não encontrado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Erro ao carregar detalhes.", Toast.LENGTH_SHORT).show();
        }
    }
}