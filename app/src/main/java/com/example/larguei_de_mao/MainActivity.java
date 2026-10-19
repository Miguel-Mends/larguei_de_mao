package com.example.larguei_de_mao;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Botão Vender -> abre a tela de venda
        Button btVender = findViewById(R.id.bt_vender);
        btVender.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,Venda.class);
            startActivity(intent);
        });

        Button btClassificados = findViewById(R.id.bt_comprar);
        btClassificados.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Compra.class);
            startActivity(intent);
        });
    }
}