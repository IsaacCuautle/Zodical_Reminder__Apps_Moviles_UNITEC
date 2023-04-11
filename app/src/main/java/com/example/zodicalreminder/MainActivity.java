package com.example.zodicalreminder;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    Button btnLogin;
    SignInButton btnLoginGoogle;
    TextView txtRegistro;
    EditText txtEmail, txtPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.editTextTextEmailLogin);
        txtPass = findViewById(R.id.editTextTextPasswordLogin);
        btnLogin = findViewById(R.id.buttonLogin);
        txtRegistro = findViewById(R.id.textViewHaciaRegistro);
        SpannableString spantxt = new SpannableString("¿Aun no tienes cuenta? Registrate.");
        spantxt.setSpan(new UnderlineSpan(),0,spantxt.length(),0);
        txtRegistro.setText(spantxt);
        btnLoginGoogle = findViewById(R.id.btnGoogle);

        //Intializa un objeto usuario para Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        btnLoginGoogle.setOnClickListener(v -> signIn());

        txtRegistro.setOnClickListener(v -> {
            abrirActivity(Registro.class, this.txtRegistro);
            finish();
        });

        btnLogin.setOnClickListener(v -> {
            // Intenta iniciar la session del usuario
            try {
                signInEmailAndPass(txtEmail.getText().toString(), txtPass.getText().toString());
            }
            catch (Exception e) {
                if(txtEmail.getText().toString().equals("") || txtPass.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(), "firebaseAuthWithGoogle: ",Toast.LENGTH_SHORT).show();
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(),"Google sign in failed ",Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Permite al usuario logearse usando una cuenta de google
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "Iniciaste sesion con Google",Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), "Error al inicar session con Google",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Permite registrar al usuario con un email y un password
    public void signInEmailAndPass(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "Bienvenido " + email, Toast.LENGTH_SHORT).show();
                        abrirActivity(Menu.class, null);
                        finish();
                    } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getApplicationContext(), "Error al Inicar Sesion, vuelve a intentarlo mas tarde",
                    Toast.LENGTH_SHORT).show();
                    }
            });
    }

    // Abre otra vista de la aplicacion y agrega un efecto a los enlaces
    public void abrirActivity(Class activity, TextView txt) {
        Intent i = new Intent(this, activity);

        // Si se le pasa un enlace aplica una animacion cada que este sea presionado
        ObjectAnimator animator = ObjectAnimator.ofInt(txt, "textColor", Color.BLUE, Color.RED, Color.GREEN);
        animator.setDuration(500);
        animator.start();
        startActivity(i);
    }

    // Crea un fragment para el inicio de sesion con Google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

}