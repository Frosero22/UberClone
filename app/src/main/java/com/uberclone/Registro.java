package com.uberclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Registro extends AppCompatActivity {

    Button rRegistrar, rCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        rRegistrar = findViewById(R.id.btn_R_register);
        rCancelar = findViewById(R.id.btn_R_cancelar);
        rCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelarRegistro();
            }
        });

    }

    private void CancelarRegistro(){
        Intent intent = new Intent(Registro.this,OpcionDeSesion.class);
        startActivity(intent);
    }

}
