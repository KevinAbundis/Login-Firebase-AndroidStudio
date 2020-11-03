package com.abundis.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables de Login y Registrar usuario
    private EditText TextEmail;
    private EditText TextPassword;
    //private Button BotonRegistrar;
    private ProgressDialog progressDialog;

    private Button BotonLogin;

    //Declaramos objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //inicializamos objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Hacemos referencias de los views
        TextEmail = (EditText) findViewById(R.id.TxtEmail);
        TextPassword = (EditText) findViewById(R.id.TxtPassword);

        //BotonRegistrar = (Button) findViewById(R.id.BtnRegistrarUsuario);
        BotonLogin = (Button) findViewById(R.id.BtnIniciarSesion);

        progressDialog = new ProgressDialog(this);

        //listener de los botones
        //BotonRegistrar.setOnClickListener(this);
        BotonLogin.setOnClickListener(this);
    }

    //Método para mandar a llamar el activity de Registro de Usuario
    public void IrRegistroUsuario (View view){
        Intent irregistrousuario = new Intent(this, RegistroUsuario.class);
        startActivity(irregistrousuario);
    }


    /*private void registrarUsuario(){
        //Obtener el email y contraseña
        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        //Verificar que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Favor de ingresar su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Favor de ingresar su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        //Mandar mensaje en pantalla que se esta realizando registro
        progressDialog.setMessage("Realizando registro de usuario...");
        progressDialog.show();

        //Creando nuevo usuario
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checando si hay registro
                        if(task.isSuccessful()){
                                //Mensaje de que el usuario ha sido registrado
                                Toast.makeText(MainActivity.this, "Se ha registrado el usuario", Toast.LENGTH_SHORT).show();
                                //Limpiar cajas de texto al registrar usuario
                                TextEmail.setText("");
                                TextPassword.setText("");
                        }else {
                            //Verificar si el usuario ya esta registrado
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                //Mensaje de que el usuario ya existe
                                Toast.makeText(MainActivity.this, "Usuario ya existe, intente con otro", Toast.LENGTH_SHORT).show();
                            } else {
                                //Mensaje de que no se pudo registrar el usuario
                                Toast.makeText(MainActivity.this, "No se pudo registrar el usuario, verifique correo o contraseña", Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }*/


    private void loginUsuario(){
        //Obtener el email y contraseña
        final String email = TextEmail.getText().toString().trim();
        final String password = TextPassword.getText().toString().trim();

        //Verificar que las cajas de texto no esten vacias
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Favor de ingresar su correo electrónico", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Favor de ingresar su contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        //Mandar mensaje en pantalla que se esta realizando el inicio de sesión
        progressDialog.setMessage("Iniciando sesión...");
        progressDialog.show();

        //Login de usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Método para solo mandar lo que está del lado izquierdo del arroba
                            int pos = email.indexOf("@");
                            String user = email.substring(0,pos);

                            //Mensaje de que el usuario ha iniciado sesión exitosamente
                            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();

                            //Aquí estamos enviando el usuario a la otra actividad
                            Intent intencion = new Intent(getApplication(), BienvenidaActivity.class);
                            intencion.putExtra(BienvenidaActivity.user, user);

                            //Iniciamos la otra activity
                            startActivity(intencion);

                            //Finalizamos la activity actual, para que cuando el usuario presione el botón de atras ya no se visualize de nuevo
                            finish();

                        }else {


                            if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                //Mensaje de que el usuario no existe
                                Toast.makeText(MainActivity.this, "El usuario no esta registrado", Toast.LENGTH_SHORT).show();

                            }else{
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    //Mensaje de que la contraseña es incorrecta
                                    Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

                                }else{
                                    //Mensaje de que no se pudo iniciar sesión por alguna otra razón
                                    Toast.makeText(MainActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    //Método que activa el evento al momento de dar clic en los botones
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            /*case R.id.BtnRegistrarUsuario:
                //Invocamos al método
                registrarUsuario();
                break;*/

            case R.id.BtnIniciarSesion:
                //Invocamos al método
                loginUsuario();
                break;

        }

    }
}
