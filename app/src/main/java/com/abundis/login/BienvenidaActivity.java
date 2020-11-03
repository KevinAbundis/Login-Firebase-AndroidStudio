package com.abundis.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BienvenidaActivity extends AppCompatActivity {

    //Variables para recibir el usuario del MainActivity
    public static final String user="names";
    TextView txtUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        //Realizando las referencias
        txtUser = (TextView) findViewById(R.id.TextUser);

        //Recibiendo el usuario de la otra actividad
        String user = getIntent().getStringExtra("names");

        txtUser.setText("¡Bienvenido "+user+" !");

    }
}
