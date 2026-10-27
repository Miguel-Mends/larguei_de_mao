package com.example.larguei_de_mao;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cadastro extends AppCompatActivity {

    private EditText etLogin, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.bt_voltar).setOnClickListener(v -> finish());

        etLogin = findViewById(R.id.et_email);
        etSenha = findViewById(R.id.et_senha);
        Button btSalvar = findViewById(R.id.bt_registrar);
        btSalvar.setOnClickListener(v -> cadastrar());
    }

    private void cadastrar() {
        String login = etLogin.getText().toString().trim();
        String senha = etSenha.getText().toString();

        if (login.isEmpty()) {
            etLogin.setError("Informe o login");
            etLogin.requestFocus();
            return;
        }
        if (senha.isEmpty()) {
            etSenha.setError("Informe a senha");
            etSenha.requestFocus();
            return;
        }
        if (login.equalsIgnoreCase("admin")) {
            etLogin.setError("Esse login é reservado");
            etLogin.requestFocus();
            return;
        }

        usuariocontrole controle = new usuariocontrole(this);
        if (controle.existe_login(login)) {
            etLogin.setError("Esse login já está em uso");
            etLogin.requestFocus();
            return;
        }

        usuario novo = new usuario();
        novo.setLogin(login);
        novo.setSenha(senha);

        if (controle.insere_usuario(novo)) {
            Toast.makeText(this, "Cadastro realizado! Faça login.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
        }
    }
}