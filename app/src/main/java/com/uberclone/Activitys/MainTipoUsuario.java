package com.uberclone.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberclone.Inclusiones.Preferencias;
import com.uberclone.Inclusiones.toolb;
import com.uberclone.R;

public class MainTipoUsuario extends AppCompatActivity {

    Button sConductor, sCliente;
    SharedPreferences mPreferencia;
    public static final String PREFERENCE_ESTADO_BUTTON_SESION = "estado.buton.sesion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Preferencias.obtenerPreferenciaBoolean(this, PREFERENCE_ESTADO_BUTTON_SESION)) {
            Intent i = new Intent(MainTipoUsuario.this, OpcionDeSesion.class);
            startActivity(i);
            finish();
        }

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
                Validacion();
            }
        });

        sConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user","conductor");
                editor.apply();
                Validacion();
            }
        });


    }



    public void Validacion() {
        new AlertDialog.Builder(MainTipoUsuario.this)
                .setIcon(R.drawable.cars)
                .setTitle("ALERTA")

                .setMessage("¿Esta seguro de continuar?")
                .setPositiveButton("ACEPTAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Autenticacion();

                            }
                        })
                .setNegativeButton("NEGAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }


                        })
                .show()
                .setCancelable(false);
    }



    private void Autenticacion(){
        Intent intent = new Intent(MainTipoUsuario.this,OpcionDeSesion.class);
        startActivity(intent);
        Preferencias.savePreferenciaBoolean(MainTipoUsuario.this, true, PREFERENCE_ESTADO_BUTTON_SESION);

        finish();
    }
}
