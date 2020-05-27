package com.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainTipoUsuario extends AppCompatActivity {

    Button sConductor, sCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sConductor = findViewById(R.id.btn_conductor);
        sCliente = findViewById(R.id.btn_cliente);

        sCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autenticacion();
            }
        });

        sConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Autenticacion();
            }
        });


    }

    private void Autenticacion(){
        Intent intent = new Intent(MainTipoUsuario.this,OpcionDeSesion.class);
        startActivity(intent);
        finish();
    }
}
