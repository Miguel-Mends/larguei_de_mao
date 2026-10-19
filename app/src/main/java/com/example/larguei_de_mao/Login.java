package com.example.larguei_de_mao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.larguei_de_mao.Cadastro;
import com.example.larguei_de_mao.MainActivity;
import com.example.larguei_de_mao.R;
import com.example.larguei_de_mao.usuariocontrole;

public class Login extends AppCompatActivity {

    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_SENHA = "123";

    private EditText etLogin, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etLogin = findViewById(R.id.et_email);
        etSenha = findViewById(R.id.et_senha);

        Button bt_entrar = findViewById(R.id.bt_entrar);
        bt_entrar.setOnClickListener(v -> entrar());

        Button bt_cadastro = findViewById(R.id.bt_cadastrar);
        bt_cadastro.setOnClickListener(v ->
                startActivity(new Intent(Login.this, Cadastro.class)));
    }

    private void entrar() {
        String login = etLogin.getText().toString().trim();
        String senha = etSenha.getText().toString();

        if (login.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Informe login e senha", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean autorizado = (login.equals(ADMIN_LOGIN) && senha.equals(ADMIN_SENHA))
                || new usuariocontrole(this).valida_login(login, senha);

        if (autorizado) {
            startActivity(new Intent(Login.this, MainActivity.class));
        } else {
            Toast.makeText(this, "Login ou senha inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}