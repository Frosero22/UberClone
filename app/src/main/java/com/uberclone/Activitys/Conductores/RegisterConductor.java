package com.uberclone.Activitys.Conductores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.uberclone.Activitys.Clientes.RegistroCliente;
import com.uberclone.Activitys.Login;
import com.uberclone.Activitys.MainTipoUsuario;
import com.uberclone.Activitys.OpcionDeSesion;
import com.uberclone.Inclusiones.Preferencias;
import com.uberclone.Inclusiones.toolb;
import com.uberclone.Modelos.Conductor;
import com.uberclone.Modelos.Usuario;
import com.uberclone.R;

import dmax.dialog.SpotsDialog;

public class RegisterConductor extends AppCompatActivity {

    Button rcRegistrar, rcCancelar;
    EditText rcNombres, rcApellidos, rcCorreo, rcContraseña, rcMarcaVehiculo, rcPlacaVehiculo;
    AlertDialog rcDialogo;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_conductor);

        toolb.mostrarT(this,"Registro de Conductor",true);


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        rcDialogo = new SpotsDialog.Builder().setContext(RegisterConductor.this).setMessage("Registrando Usuario..").build();


        rcNombres = findViewById(R.id.edit_RC_nombre);
        rcApellidos = findViewById(R.id.edit_RC_apellido);
        rcCorreo = findViewById(R.id.edit_RC_mail);
        rcContraseña = findViewById(R.id.edit_RC_pass);
        rcPlacaVehiculo = findViewById(R.id.edit_RC_placa);
        rcMarcaVehiculo = findViewById(R.id.edit_RC_marca);

        rcRegistrar = findViewById(R.id.btn_RC_register);

        rcRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validacion();
            }
        });

        rcCancelar = findViewById(R.id.btn_RC_cancelar);
        rcCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferencias.savePreferenciaBoolean(RegisterConductor.this, false, "estado.buton.sesion");
                Preferencias.savePreferenciaBoolean(RegisterConductor.this, false, "estado");
                CancelarRegistro();
            }
        });

    }

    private void CancelarRegistro(){
        Preferencias.savePreferenciaBoolean(RegisterConductor.this, false, "estado.buton.sesion");
        Preferencias.savePreferenciaBoolean(RegisterConductor.this, false, "estado");
        Intent intent = new Intent(RegisterConductor.this, OpcionDeSesion.class);
        startActivity(intent);
    }

    private void Registrar(){
        rcDialogo.show();
        final String UNombre = rcNombres.getText().toString();
        final String UApellidos = rcApellidos.getText().toString();
        final String UPlaca = rcPlacaVehiculo.getText().toString();
        final String UMarca = rcMarcaVehiculo.getText().toString();
        final String UCorreo = rcCorreo.getText().toString();
        final String UContraseña = rcContraseña.getText().toString();

        if(!UNombre.isEmpty() && !UApellidos.isEmpty() && !UCorreo.isEmpty() && !UContraseña.isEmpty()){
            if(UContraseña.length() >= 6){

                mAuth.createUserWithEmailAndPassword(UCorreo,UContraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            String id = mAuth.getCurrentUser().getUid();
                            GuardarRegistro(id,UNombre,UApellidos,UPlaca,UMarca,UCorreo,UContraseña);
                            rcDialogo.dismiss();

                        }else{
                            rcDialogo.dismiss();
                            Toast.makeText(RegisterConductor.this, "OCURRIO UN ERROR EN EL REGISTRO", Toast.LENGTH_SHORT).show();
                        }

                    }

                });


            }else{

                rcDialogo.dismiss();

                rcContraseña.setError("LA CONTASEÑA DEBE CONTAR CON MAS DE 6 CARACTERES");
            }

        }else{
            rcDialogo.dismiss();

            Toast.makeText(this, "POR FAVOR INGRESE TODOS LOS DATOS", Toast.LENGTH_SHORT).show();

        }
    }

    private void GuardarRegistro(String id,String UNombre, String UApellidos,String UPlaca,String UMarca,String UCorreo,String UContraseña){

        Conductor conductor = new Conductor();
        conductor.setNombre(UNombre);
        conductor.setApellidos(UApellidos);
        conductor.setPlacaVehiculo(UPlaca);
        conductor.setMarcaVehiculo(UMarca);
        conductor.setEmail(UCorreo);
        conductor.setContraseña(UContraseña);

        mDatabase.child("Conductores").child("Conductores Registrados").child(id).push().setValue(conductor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterConductor.this, "CONDUCTOR INCORPORADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterConductor.this, MapaConductor.class);
                    startActivity(intent);
                    finish();
                }else{

                    Toast.makeText(RegisterConductor.this, "CONDUCTOR NO INCORPORADO", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void Validacion() {
        new AlertDialog.Builder(RegisterConductor.this)
                .setIcon(R.drawable.cars)
                .setTitle("ALERTA")

                .setMessage("¿ESTA SEGURO DE REGISTRARSE COMO UN CONDUCTOR??")
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
