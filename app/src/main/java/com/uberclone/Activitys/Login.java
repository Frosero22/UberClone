package com.uberclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uberclone.Activitys.Clientes.MapaCliente;
import com.uberclone.Activitys.Conductores.MapaConductor;
import com.uberclone.Inclusiones.Preferencias;
import com.uberclone.Inclusiones.toolb;
import com.uberclone.R;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity {

    EditText lMail,lContraseña;
    Button lLogin,lCrearUsuario;
    AlertDialog lDialogo;
    SharedPreferences mPreferencia;

    //INSTANCIO OBJETO PARA LA AUTENTICACION
    FirebaseAuth lAuth;

    //INSTANCION OBJETO PARA LA BASE DE DATOS
    DatabaseReference ldatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolb.mostrarT(this,"Seleccion Tipo de Sesion",true);

        lMail = findViewById(R.id.edit_L_mail);
        lContraseña = findViewById(R.id.edit_L_pass);
        lLogin = findViewById(R.id.btn_L_login);

        mPreferencia = getApplicationContext().getSharedPreferences("tipoUsuario",MODE_PRIVATE);

        lAuth = FirebaseAuth.getInstance();
        ldatabase = FirebaseDatabase.getInstance().getReference();

        lDialogo = new SpotsDialog.Builder().setContext(Login.this).setMessage("Espere un Momento..").build();

        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        lCrearUsuario = findViewById(R.id.btn_L_crear_usuario);
        lCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearNuevoUsuario();
            }
        });

    }

    private void login(){
        lDialogo.show();
        String Email = lMail.getText().toString().trim();
        String Contraseña = lContraseña.getText().toString().trim();

        if(!Email.isEmpty() && !Contraseña.isEmpty()){
            if(Contraseña.length() >= 6){
                lAuth.signInWithEmailAndPassword(Email,Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String UsuarioSeleccionado = mPreferencia.getString("user", "");
                            lDialogo.dismiss();
                            Toast.makeText(Login.this, "Validacion Correcta", Toast.LENGTH_SHORT).show();
                            if(UsuarioSeleccionado.equalsIgnoreCase("cliente")){
                                Intent intent = new Intent(Login.this, MapaCliente.class);
                                startActivity(intent);
                                finish();

                            }else if(UsuarioSeleccionado.equalsIgnoreCase("conductor")){
                                Intent intent = new Intent(Login.this, MapaConductor.class);
                                startActivity(intent);
                                finish();

                            }

                        }else{
                            lDialogo.dismiss();
                            Toast.makeText(Login.this, "Usuario o Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }else{
                lDialogo.dismiss();
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
            }
        }else{
            lDialogo.dismiss();
            lMail.setError("CAMPO OBLIGATORIO");
            lContraseña.setError("CAMPO OBLIGATORIO");
        }

    }

    public void CrearNuevoUsuario(){
        Preferencias.savePreferenciaBoolean(Login.this, false, "estado.buton.sesion");
        Preferencias.savePreferenciaBoolean(Login.this, false, "estado");
        Intent intent = new Intent(Login.this,MainTipoUsuario.class);
        startActivity(intent);
        finish();
    }


}
