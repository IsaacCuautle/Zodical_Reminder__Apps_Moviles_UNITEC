package com.example.zodicalreminder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AveriguarSigno extends Menu {
    Button btnAveriguar;
    Spinner dia,mes;
    public TextView resultado;
    public ImageView imgSigno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_averiguar_signo);

        btnAveriguar = findViewById(R.id.buttonAveriguar);
        dia = findViewById(R.id.spinnerDia);
        mes = findViewById(R.id.spinnerMes);
        resultado = findViewById(R.id.textViewResultado);
        imgSigno = findViewById(R.id.imageViewNuevoSigno);


        btnAveriguar.setOnClickListener(v -> {
            averiguarSigno(Integer.parseInt(dia.getSelectedItem().toString()),Integer.parseInt(mes.getSelectedItem().toString()));
            // Verifica que el usario ingrese un mes y un dia
            if(Menu.signo != 0){
                actualizarMenu("signoZodiacal",resultado.getText().toString());
            }
        });
    }

    // Asigna un signo zodiacal segun el mes y dia que el usuario selecciono
    @SuppressLint("SetTextI18n")
    private void  averiguarSigno(Integer Dia, Integer Mes){
        String mensaje = "Felicidades eres: ";

        if(Dia == 0 || Mes == 0) {
            Toast.makeText(getApplicationContext(), "Elige una fecha valida", Toast.LENGTH_SHORT).show();
            Menu.signo = 0;
        }else if(Dia >= 22 && Mes == 12 || Dia <= 20 && Mes == 1){
            resultado.setText(mensaje + "Capricornio");
            imgSigno.setImageResource(R.drawable.capricornio);
            Menu.signo = 1;
        }else if(Dia >= 21 && Mes == 1 || Dia <= 19 && Mes == 2){
            resultado.setText(mensaje + "Acuario");
            imgSigno.setImageResource(R.drawable.acuario);
            Menu.signo = 2;
        }else if(Dia >= 22 && Mes == 2 || Dia <= 20 && Mes == 3){
            resultado.setText(mensaje + "Picis");
            imgSigno.setImageResource(R.drawable.piscis);
            Menu.signo = 3;
        }else if(Dia >= 21 && Mes == 3 || Dia <= 20 && Mes == 4){
            resultado.setText(mensaje + "Aries");
            imgSigno.setImageResource(R.drawable.aries);
            Menu.signo = 4;
        }else if(Dia >= 21 && Mes == 4 || Dia <= 21 && Mes == 5){
            resultado.setText(mensaje + "Tauro");
            imgSigno.setImageResource(R.drawable.tauro);
            Menu.signo = 5;
        }else if(Dia >= 22 && Mes == 5 || Dia <= 21 && Mes == 6){
            resultado.setText(mensaje + "Geminis");
            imgSigno.setImageResource(R.drawable.geminis);
            Menu.signo = 6;
        }else if(Dia >= 22 && Mes == 6 || Dia <= 22 && Mes == 7){
            resultado.setText(mensaje + "Cancer");
            imgSigno.setImageResource(R.drawable.cancer);
            Menu.signo = 7;
        }else if(Dia >= 23 && Mes == 7 || Dia <= 22 && Mes == 8){
            resultado.setText(mensaje + "Leo");
            imgSigno.setImageResource(R.drawable.leo);
            Menu.signo = 8;
        }else if(Dia >= 23 && Mes == 8 || Dia <= 22 && Mes == 9){
            resultado.setText(mensaje + "Virgo");
            imgSigno.setImageResource(R.drawable.virgo);
            Menu.signo = 9;
        }else if(Dia >= 23 && Mes == 9 || Dia <= 22 && Mes == 10){
            resultado.setText(mensaje + "Libra");
            imgSigno.setImageResource(R.drawable.libra);
            Menu.signo = 10;
        }else if(Dia >= 23 && Mes == 10 || Dia <= 22 && Mes == 11){
            resultado.setText(mensaje + "Escorpio");
            imgSigno.setImageResource(R.drawable.escorpion);
            Menu.signo = 11;
        }else if(Dia >= 23 && Mes == 11 || Dia <= 21 && Mes == 12) {
            resultado.setText(mensaje + "Sagitario");
            imgSigno.setImageResource(R.drawable.sagitario);
            Menu.signo = 12;
        }
    }

    // Manda el signo que le fue asignado al usuario al activity menu
    public void actualizarMenu(String key, String value){
        Intent i = new Intent(this,Menu.class);
        new Handler().postDelayed(() -> {
            i.putExtra(key,value);
            startActivity(i);
            finish();
        },750);
    }
}