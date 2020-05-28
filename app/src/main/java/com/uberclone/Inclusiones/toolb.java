package com.uberclone.Inclusiones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.uberclone.R;

public class toolb {

    public static void mostrarT(AppCompatActivity activity, String title,boolean mostrar){

        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle(title);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(mostrar);


    }

}
