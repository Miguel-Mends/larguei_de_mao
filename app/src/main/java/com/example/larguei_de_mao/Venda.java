package com.example.larguei_de_mao;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Venda extends AppCompatActivity {
    private Spinner spCategoria;
    private EditText etTitulo, etPreco, etDescricao, etContato, etData;
    private final Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venda);

        View raiz = findViewById(R.id.main);
        View cabecalho = findViewById(R.id.cabecalho);
        ViewCompat.setOnApplyWindowInsetsListener(raiz, (v, insets) -> {
            Insets barras = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            cabecalho.setPadding(cabecalho.getPaddingLeft(), barras.top,
                    cabecalho.getPaddingRight(), 0);
            v.setPadding(0, 0, 0, barras.bottom);
            return insets;
        });

        spCategoria = findViewById(R.id.sp_categoria);
        etTitulo = findViewById(R.id.et_titulo);
        etPreco = findViewById(R.id.et_preco);
        etDescricao = findViewById(R.id.et_descricao);
        etContato = findViewById(R.id.et_contato);
        etData = findViewById(R.id.et_data);
        Button btPublicar = findViewById(R.id.bt_publicar);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, categorias);
        spCategoria.setAdapter(adapter);

        findViewById(R.id.bt_voltar).setOnClickListener(v -> finish());

        atualizarData();
        etData.setOnClickListener(v -> new DatePickerDialog(
                this,
                (view, ano, mes, dia) -> {
                    calendario.set(ano, mes, dia);
                    atualizarData();
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        ).show());

        btPublicar.setOnClickListener(v -> publicar());
    }

    private void atualizarData() {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "BR"));
        etData.setText(formato.format(calendario.getTime()));
    }

    private void publicar() {
        if (spCategoria.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Selecione uma categoria", Toast.LENGTH_SHORT).show();
            return;
        }
        if (etTitulo.getText().toString().trim().isEmpty()) {
            etTitulo.setError("Informe o título");
            etTitulo.requestFocus();
            return;
        }
        if (etPreco.getText().toString().trim().isEmpty()) {
            etPreco.setError("Informe o preço");
            etPreco.requestFocus();
            return;
        }
        if (etContato.getText().toString().trim().isEmpty()) {
            etContato.setError("Informe um contato");
            etContato.requestFocus();
            return;
        }

        // Instancia o objeto Anuncio com os valores preenchidos na tela
        Anuncio novoAnuncio = new Anuncio();
        novoAnuncio.setCategoria(spCategoria.getSelectedItem().toString());
        novoAnuncio.setTitulo(etTitulo.getText().toString().trim());
        novoAnuncio.setPreco(Double.parseDouble(etPreco.getText().toString().trim()));
        novoAnuncio.setDescricao(etDescricao.getText().toString().trim());
        novoAnuncio.setContato(etContato.getText().toString().trim());
        novoAnuncio.setData(etData.getText().toString().trim());

        // Salva no SQLite
        AnuncioControle controle = new AnuncioControle(this);
        boolean sucesso = controle.insere_anuncio(novoAnuncio);

        if (sucesso) {
            Toast.makeText(this, "Anúncio publicado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao publicar anúncio.", Toast.LENGTH_SHORT).show();
        }
    }
}