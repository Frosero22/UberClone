package com.uberclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.uberclone.Inclusiones.toolb;
import com.uberclone.Modelos.Usuario;
import com.uberclone.R;

import dmax.dialog.SpotsDialog;

public class Registro extends AppCompatActivity {

    Button rRegistrar, rCancelar;
    SharedPreferences mPreferencia;
    EditText rNombres, rApellidos, rCorreo, rContraseña;
    AlertDialog mDialogo;



    FirebaseAuth mAuth;

    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        toolb.mostrarT(this,"Tipo de Sesion",true);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mPreferencia = getApplicationContext().getSharedPreferences("tipoUsuario",MODE_PRIVATE);

        mDialogo = new SpotsDialog.Builder().setContext(Registro.this).setMessage("Registrando Usuario..").build();


        rNombres = findViewById(R.id.edit_R_nombre);
        rApellidos = findViewById(R.id.edit_R_apellido);
        rCorreo = findViewById(R.id.edit_R_mail);
        rContraseña = findViewById(R.id.edit_R_pass);

        rRegistrar = findViewById(R.id.btn_R_register);

        rRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar();
            }
        });

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

    private void Registrar(){
        mDialogo.show();
        final String UNombre = rNombres.getText().toString();
        final String UApellidos = rApellidos.getText().toString();
        final String UCorreo = rCorreo.getText().toString();
        final String UContraseña = rContraseña.getText().toString();

        if(!UNombre.isEmpty() && !UApellidos.isEmpty() && !UCorreo.isEmpty() && !UContraseña.isEmpty()){
            if(UContraseña.length() >= 6){

                mAuth.createUserWithEmailAndPassword(UCorreo,UContraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                           String id = mAuth.getCurrentUser().getUid();
                            GuardarRegistro(id,UNombre,UApellidos,UCorreo,UContraseña);
                            mDialogo.dismiss();
                            Toast.makeText(Registro.this, "USUARIO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registro.this,Login.class);
                            startActivity(intent);

                        }else{
                            mDialogo.dismiss();
                            Toast.makeText(Registro.this, "OCURRIO UN ERROR EN EL REGISTRO", Toast.LENGTH_SHORT).show();
                        }
                        
                    }

                });


            }else{

                mDialogo.dismiss();

            rContraseña.setError("LA CONTASEÑA DEBE CONTAR CON MAS DE 6 CARACTERES");
            }

        }else{
            mDialogo.dismiss();

            Toast.makeText(this, "POR FAVOR INGRESE TODOS LOS DATOS", Toast.LENGTH_SHORT).show();

        }
    }

    private void GuardarRegistro(String id,String UNombre, String UApellidos,String UCorreo,String UContraseña){
        String UsuarioSeleccionado = mPreferencia.getString("user","");

        Usuario usuario = new Usuario();
        usuario.setNombre(UNombre);
        usuario.setApellidos(UApellidos);
        usuario.setEmail(UCorreo);
        usuario.setContraseña(UContraseña);



        if(UsuarioSeleccionado.equals("conductor")){
            mDatabase.child("Usuarios").child("Conductores").child(id).push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Registro.this, "USUARIO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(Registro.this, "USUARIO NO REGISTRADO", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }else if(UsuarioSeleccionado.equals("cliente")){
            mDatabase.child("Usuarios").child("Clientes").child(id).push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(Registro.this, "USUARIO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();

                    }else{

                        Toast.makeText(Registro.this, "USUARIO NO REGISTRADO", Toast.LENGTH_SHORT).show();

                    }

                }

            });

        }

    }

}
