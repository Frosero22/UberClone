package com.uberclone.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberclone.Activitys.Clientes.RegistroCliente;
import com.uberclone.Activitys.Conductores.RegisterConductor;
import com.uberclone.Inclusiones.Preferencias;
import com.uberclone.Inclusiones.toolb;
import com.uberclone.R;


public class OpcionDeSesion extends AppCompatActivity {

    Button xLogin, xRegister, xVolverElegir;
    SharedPreferences mPreferencia;
    public static final String PREFERENCE_ESTADO_BUTTON_SESION = "estado";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion_de_sesion);

        if (Preferencias.obtenerPreferenciaBoolean(this, PREFERENCE_ESTADO_BUTTON_SESION)) {
            Intent i = new Intent(OpcionDeSesion.this, Login.class);
            startActivity(i);
            finish();
        }

        toolb.mostrarT(this,"Tipo de Usuario",true);

        mPreferencia = getApplicationContext().getSharedPreferences("tipoUsuario",MODE_PRIVATE);


        xRegister = findViewById(R.id.btn_dRegister);
        xRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro();
            }
        });
            xLogin = findViewById(R.id.btn_dLogin);
        xLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        xVolverElegir = findViewById(R.id.btn_dRegresar);
        xVolverElegir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegresarElegirTipo();
            }
        });

    }



    private void Registro(){
        String UsuarioSeleccionado = mPreferencia.getString("user","");

        if(UsuarioSeleccionado.equals("conductor")){
            Intent intent = new Intent(OpcionDeSesion.this, RegisterConductor.class);
            Preferencias.savePreferenciaBoolean(OpcionDeSesion.this, true, PREFERENCE_ESTADO_BUTTON_SESION);

            startActivity(intent);
            finish();

        }else if(UsuarioSeleccionado.equals("cliente")){
            Intent intent = new Intent(OpcionDeSesion.this, RegistroCliente.class);
            Preferencias.savePreferenciaBoolean(OpcionDeSesion.this, true, PREFERENCE_ESTADO_BUTTON_SESION);
            startActivity(intent);
            finish();
        }


    }

    private void Login(){

        Intent intent = new Intent(OpcionDeSesion.this,Login.class);
        Preferencias.savePreferenciaBoolean(OpcionDeSesion.this, true, PREFERENCE_ESTADO_BUTTON_SESION);

        startActivity(intent);
        finish();
    }

    public void RegresarElegirTipo(){
        Preferencias.savePreferenciaBoolean(OpcionDeSesion.this, false, "estado.buton.sesion");
        Preferencias.savePreferenciaBoolean(OpcionDeSesion.this, false, "estado");
        Intent intent = new Intent(OpcionDeSesion.this,MainTipoUsuario.class);
        startActivity(intent);
        finish();


    }




}
