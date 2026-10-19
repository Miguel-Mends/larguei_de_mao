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

        tvNome = findViewById(R.id.tv_nome_produto);
        tvPreco = findViewById(R.id.tv_preco_produto);
        tvDescricao = findViewById(R.id.tv_descricao_produto);

        findViewById(R.id.bt_voltar).setOnClickListener(v -> finish());

        int idAnuncio = getIntent().getIntExtra("ID_ANUNCIO", -1);

        if (idAnuncio != -1) {
            AnuncioControle controle = new AnuncioControle(this);
            Anuncio anuncio = controle.busca_anuncio(idAnuncio);

            if (anuncio != null) {
                if (tvNome != null) tvNome.setText(anuncio.getTitulo());
                if (tvPreco != null) tvPreco.setText(String.format("R$ %.2f", anuncio.getPreco()));
                if (tvDescricao != null) tvDescricao.setText(anuncio.getDescricao());
            } else {
                Toast.makeText(this, "Anúncio não encontrado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Erro ao carregar detalhes.", Toast.LENGTH_SHORT).show();
        }
    }
}