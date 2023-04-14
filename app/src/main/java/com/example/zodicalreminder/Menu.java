package com.example.zodicalreminder;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Menu extends MainActivity {
    private ImageView imgSignoActual;
    public TextView txtSignoActual;
    public Bundle bundle;
    private FirebaseAuth mAuth;
    public Button btnAveriguarSigno, btnLogOut;
    public static Integer signo = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        imgSignoActual = findViewById(R.id.imageViewSignoActual);
        btnAveriguarSigno = findViewById(R.id.buttonAveriguarSigno);
        txtSignoActual = findViewById(R.id.textViewSignoActual);
        btnLogOut = findViewById(R.id.buttonHoroscopoLogout);

        // Comprueba el signo zodiacal del usuario
        try {
            actualizarSignoActual(signo);
            bundle = getIntent().getExtras();
            txtSignoActual.setText(bundle.getString("signoZodiacal"));
        }catch (Exception ignored){

        }

        btnLogOut.setOnClickListener(v -> {
            try {

                logout();
            }catch (Exception e){

            }


        });

        btnAveriguarSigno.setOnClickListener(v -> {
            abrirActivity(AveriguarSigno.class,null);
        });

    }

    // Dependiendo el signo del usaurio cambia la imagen que se muestra al usuario
    public void actualizarSignoActual(int signo){
            switch(signo){
                case 0:
                    imgSignoActual.setImageResource(R.drawable.question);
                    break;
                case 1:
                    imgSignoActual.setImageResource(R.drawable.capricornio);
                    break;
                case 2:
                    imgSignoActual.setImageResource(R.drawable.acuario);
                    break;
                case 3:
                    imgSignoActual.setImageResource(R.drawable.piscis);
                    break;
                case 4:
                    imgSignoActual.setImageResource(R.drawable.aries);
                    break;
                case 5:
                    imgSignoActual.setImageResource(R.drawable.tauro);
                    break;
                case 6:
                    imgSignoActual.setImageResource(R.drawable.geminis);
                    break;
                case 7:
                    imgSignoActual.setImageResource(R.drawable.cancer);
                    break;
                case 8:
                    imgSignoActual.setImageResource(R.drawable.leo);
                    break;
                case 9:
                    imgSignoActual.setImageResource(R.drawable.virgo);
                    break;
                case 10:
                    imgSignoActual.setImageResource(R.drawable.libra);
                    break;
                case 11:
                    imgSignoActual.setImageResource(R.drawable.escorpion);
                    break;
                case 12:
                    imgSignoActual.setImageResource(R.drawable.sagitario);
                    break;
            }
    }

    public void logout(){
        Toast.makeText(getApplicationContext(), "Cerraste sesion", Toast.LENGTH_SHORT).show();
        abrirActivity(MainActivity.class,null);
        finish();
        mAuth.signOut();
    }


}