package com.uberclone.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberclone.Inclusiones.toolb;
import com.uberclone.R;

public class MainTipoUsuario extends AppCompatActivity {

    Button sConductor, sCliente;
    SharedPreferences mPreferencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolb.mostrarT(this,"X-UBER",false);

        sConductor = findViewById(R.id.btn_conductor);
        sCliente = findViewById(R.id.btn_cliente);

        mPreferencia = getApplicationContext().getSharedPreferences("tipoUsuario",MODE_PRIVATE);

        final SharedPreferences.Editor editor = mPreferencia.edit();

        sCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","cliente");
                editor.apply();
                Autenticacion();
            }
        });

        sConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","conductor");
                editor.apply();
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
