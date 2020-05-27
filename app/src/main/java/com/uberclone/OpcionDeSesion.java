package com.uberclone;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;


public class OpcionDeSesion extends AppCompatActivity {

    Button xLogin, xRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcion_de_sesion);


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
        Intent intent = new Intent(OpcionDeSesion.this,Registro.class);
        startActivity(intent);
    }

    private void Login(){
        Intent intent = new Intent(OpcionDeSesion.this,Login.class);
        startActivity(intent);
    }
}
