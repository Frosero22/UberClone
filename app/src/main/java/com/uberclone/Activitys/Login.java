package com.uberclone.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.uberclone.R;

import dmax.dialog.SpotsDialog;

public class Login extends AppCompatActivity {

    EditText lMail,lContraseña;
    Button lLogin;
    AlertDialog lDialogo;

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


        lAuth = FirebaseAuth.getInstance();
        ldatabase = FirebaseDatabase.getInstance().getReference();

        lDialogo = new SpotsDialog.Builder().setContext(Login.this).setMessage("Espere un Momento..").build();

        lLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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
                            lDialogo.dismiss();
                            Toast.makeText(Login.this, "Validacion Correcta", Toast.LENGTH_SHORT).show();
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

}
