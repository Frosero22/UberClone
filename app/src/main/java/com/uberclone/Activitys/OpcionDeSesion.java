package com.uberclone.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.uberclone.Activitys.Conductores.RegisterConductor;
import com.uberclone.Inclusiones.toolb;
import com.uberclone.R;


public class OpcionDeSesion extends AppCompatActivity {

    Button xLogin, xRegister;
    SharedPreferences mPreferencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion_de_sesion);
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


    }



    private void Registro(){
        String UsuarioSeleccionado = mPreferencia.getString("user","");

        if(UsuarioSeleccionado.equals("conductor")){
            Intent intent = new Intent(OpcionDeSesion.this, RegisterConductor.class);
            startActivity(intent);
        }else if(UsuarioSeleccionado.equals("cliente")){
            Intent intent = new Intent(OpcionDeSesion.this,Registro.class);
            startActivity(intent);
        }


    }

    private void Login(){
        Intent intent = new Intent(OpcionDeSesion.this,Login.class);
        startActivity(intent);
    }
}
