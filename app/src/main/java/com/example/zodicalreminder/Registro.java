package com.example.zodicalreminder;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends MainActivity {
    TextView txtLogin;
    EditText txtEmail,txtPass1,txtPass2;
    Button btnReg;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        btnReg = findViewById(R.id.buttonRegistro);
        txtLogin = findViewById(R.id.textViewHaciaLogin);
        SpannableString spantxt = new SpannableString("¿Ya tienes cuenta? Inicia Sesion.");
        spantxt.setSpan(new UnderlineSpan(),0,spantxt.length(),0);
        txtLogin.setText(spantxt);
        txtEmail = findViewById(R.id.editTextTextEmailRegistro);
        txtPass1 = findViewById(R.id.editTextTextPasswordRegistro);
        txtPass2 = findViewById(R.id.editTextTextPasswordRegistro2);



        btnReg.setOnClickListener(v -> {
            try {
                createAcount(txtEmail.getText().toString(),txtPass1.getText().toString(),txtPass2.getText().toString());
                abrirActivity(AveriguarSigno.class,null);
                finish();
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            }
        });

        txtLogin.setOnClickListener(v -> {
            abrirActivity(MainActivity.class,txtLogin);
            finish();
        });
    }

    // Rergistra al usuario utilizando su email y password
    public void createAcount(String email,String password,String password2){
        // Comprueba que la contraseña del usuario sea igual en ambos campos
        if(password.equals(password2)){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Error al registrarte, vuelve a intentarlo mas tarde!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(), "Las contraseñas no coniciden", Toast.LENGTH_SHORT).show();
        }
    }
}