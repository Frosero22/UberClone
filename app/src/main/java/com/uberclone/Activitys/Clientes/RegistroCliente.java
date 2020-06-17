package com.uberclone.Activitys.Clientes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uberclone.Activitys.Conductores.MapaConductor;
import com.uberclone.Activitys.Conductores.RegisterConductor;
import com.uberclone.Activitys.Login;
import com.uberclone.Activitys.OpcionDeSesion;
import com.uberclone.Inclusiones.Preferencias;
import com.uberclone.Inclusiones.toolb;
import com.uberclone.Modelos.Usuario;
import com.uberclone.R;

import dmax.dialog.SpotsDialog;

public class RegistroCliente extends AppCompatActivity {

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

        toolb.mostrarT(this, "Tipo de Sesion", true);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mPreferencia = getApplicationContext().getSharedPreferences("tipoUsuario", MODE_PRIVATE);

        mDialogo = new SpotsDialog.Builder().setContext(RegistroCliente.this).setMessage("Registrando Usuario..").build();


        rNombres = findViewById(R.id.edit_R_nombre);
        rApellidos = findViewById(R.id.edit_R_apellido);
        rCorreo = findViewById(R.id.edit_R_mail);
        rContraseña = findViewById(R.id.edit_R_pass);

        rRegistrar = findViewById(R.id.btn_R_register);

        rRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validacion();
            }
        });

        rCancelar = findViewById(R.id.btn_R_cancelar);
        rCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias.savePreferenciaBoolean(RegistroCliente.this, false, "estado.buton.sesion");
                Preferencias.savePreferenciaBoolean(RegistroCliente.this, false, "estado");
                CancelarRegistro();
            }
        });

    }

    private void CancelarRegistro() {
        Intent intent = new Intent(RegistroCliente.this, OpcionDeSesion.class);
        startActivity(intent);
    }

    private void Registrar() {
        mDialogo.show();
        final String UNombre = rNombres.getText().toString();
        final String UApellidos = rApellidos.getText().toString();
        final String UCorreo = rCorreo.getText().toString();
        final String UContraseña = rContraseña.getText().toString();

        if (!UNombre.isEmpty() && !UApellidos.isEmpty() && !UCorreo.isEmpty() && !UContraseña.isEmpty()) {
            if (UContraseña.length() >= 6) {

                mAuth.createUserWithEmailAndPassword(UCorreo, UContraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String id = mAuth.getCurrentUser().getUid();
                            GuardarRegistro(id, UNombre, UApellidos, UCorreo, UContraseña);
                            mDialogo.dismiss();

                        } else {
                            mDialogo.dismiss();
                            Toast.makeText(RegistroCliente.this, "OCURRIO UN ERROR EN EL REGISTRO", Toast.LENGTH_SHORT).show();
                        }

                    }

                });


            } else {

                mDialogo.dismiss();

                rContraseña.setError("LA CONTASEÑA DEBE CONTAR CON MAS DE 6 CARACTERES");
            }

        } else {
            mDialogo.dismiss();

            Toast.makeText(this, "POR FAVOR INGRESE TODOS LOS DATOS", Toast.LENGTH_SHORT).show();

        }
    }

    private void GuardarRegistro(String id, String UNombre, String UApellidos, String UCorreo, String UContraseña) {

        Usuario usuario = new Usuario();
        usuario.setNombre(UNombre);
        usuario.setApellidos(UApellidos);
        usuario.setEmail(UCorreo);
        usuario.setContraseña(UContraseña);


            mDatabase.child("Usuarios").child("Clientes").child(id).push().setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistroCliente.this, "USUARIO REGISTRADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistroCliente.this, MapaCliente.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(RegistroCliente.this, "USUARIO NO REGISTRADO", Toast.LENGTH_SHORT).show();

                    }
                }
            });




    }

    public void Validacion() {
        new AlertDialog.Builder(RegistroCliente.this)
                .setIcon(R.drawable.cars)
                .setTitle("ALERTA")

                .setMessage("¿ESTA SEGURO DE REGISTRARSE COMO UN CLIENTE?")
                .setNegativeButton("NEGAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                            }
                        })
                .setPositiveButton("REGISTARA CONDUCTOR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Registrar();

                            }


                        })
                .show()
                .setCancelable(false);
    }

}
