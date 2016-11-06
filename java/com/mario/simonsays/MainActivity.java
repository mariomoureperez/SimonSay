package com.mario.simonsays;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button start = (Button) findViewById(R.id.empezar);
        Button azul = (Button) findViewById(R.id.azul);
        azul.setEnabled(false);
        Button rojo = (Button) findViewById(R.id.rojo);
        rojo.setEnabled(false);
        Button amarillo = (Button) findViewById(R.id.amarillo);
        amarillo.setEnabled(false);
        Button verde = (Button) findViewById(R.id.verde);
        verde.setEnabled(false);
        TextView nivel = (TextView) findViewById(R.id.nivel);
    }

    int[] botones = {R.id.azul, R.id.rojo, R.id.amarillo, R.id.verde};
    String[] colorBoton = {"#00B0FF", "#E57373", "#FFFF99", "#8BC34A"};
    int[] colorClaro = {Color.BLUE, Color.RED, Color.parseColor("#FFFF00"), Color.GREEN};
    int[] arrayAudio = {R.raw.azul, R.raw.rojo, R.raw.amarillo, R.raw.verde};

    TimerTask tiempoTarea;
    Timer tiempo;
    ArrayList<Integer> colores = new ArrayList();
    ArrayList<Integer> jugador = new ArrayList();

    protected static int CONTADOR = 0;// contador para comprobar si ganas o pierdes, cuando sea igual a dificultad
    protected static int CONTTIEMPO=0;// variable para ver cuantas veces se iluminan los botones automaticamente
    protected static int DIFICULT=4; // dificultad del juego, numeros de colores a mostrar
    protected static int NIVEL=1; // nivel en el qeu estamos
    protected static int A=0;// vairable que nos da la posicion del Array que queremos mostrar

    void empezar(View e) {
        findViewById(R.id.azul).setEnabled(true);
        findViewById(R.id.rojo).setEnabled(true);
        findViewById(R.id.amarillo).setEnabled(true);
        findViewById(R.id.verde).setEnabled(true);
        aleatorio();
        CONTADOR = 0;
        A=0;
        empezarTimer();
        e.setEnabled(false);
    }

    void eventoAzul(View a) {
        CONTADOR++;
        jugador.add(0);
        parpadear(0);
        restablecer();
    }

    void eventoRojo(View r) {
        CONTADOR++;
        jugador.add(1);
        parpadear(1);
        restablecer();
    }

    void eventoAmarillo(View am) {
        CONTADOR++;
        jugador.add(2);
        parpadear(2);
        restablecer();
    }

    void eventoVerde(View a) {
        CONTADOR++;
        jugador.add(3);
        parpadear(3);
        restablecer();
    }

    /*
    método aleatorio que genera cuatro numeros en el primer nivel y los añade al Array
    y en los siguietes niveles solo genera uno para que se vaya sumando un color cada vez que ganes.
     */
    void aleatorio() {
        if(DIFICULT==4){
            for (int i=0;i<4;i++){
                int valorDado = (int) Math.floor(Math.random()*4);
                colores.add(valorDado);
            }}else{
            int valorDado = (int) Math.floor(Math.random()*4);
            colores.add(valorDado);}



    }
    /*
    método para comprobar si los Arrays son iguales o no y donde si acertamos se incremetara un nivel
    y si perdemos se restablecen los niveles a 1
     */
    void comprobar() {
        TextView nivel = (TextView) findViewById(R.id.nivel);

        if (colores.toString().equals(jugador.toString())) {
            Toast.makeText(this, "Has Ganado!!! Pulsa Simon dice para continuar", Toast.LENGTH_SHORT).show();
            DIFICULT++;
            NIVEL++;
            nivel.setText("Nivel: "+NIVEL);
        } else{
            Toast.makeText(this, "Has Perdido, lo que cuenta es participar ;)", Toast.LENGTH_SHORT).show();
            DIFICULT=4;
            NIVEL=1;
            colores.clear();
            nivel.setText("Nivel: "+NIVEL);
        }
        findViewById(R.id.azul).setEnabled(false);
        findViewById(R.id.rojo).setEnabled(false);
        findViewById(R.id.amarillo).setEnabled(false);
        findViewById(R.id.verde).setEnabled(false);
    }
    /*
    método que hace qeu el boton parpadee
     */
    void parpadear(final int posicionBotn){
        findViewById(botones[posicionBotn]).setBackgroundColor(colorClaro[posicionBotn]);
        final MediaPlayer audio = MediaPlayer.create(this, arrayAudio[posicionBotn]);
        audio.start();
        findViewById(botones[posicionBotn]).postDelayed(new Runnable() {
            public void run() {
                audio.reset();
                findViewById(botones[posicionBotn]).setBackgroundColor(Color.parseColor(colorBoton[posicionBotn]));
            }
        }, 600);

    }
    /*
    método que llama a comprobar cuando el usuario llegue al final de la secuencia
     */
    void restablecer(){
        Button start = (Button) findViewById(R.id.empezar);
        if (CONTADOR == DIFICULT) {
            comprobar();
            jugador.clear();
            CONTTIEMPO=0;
            start.setEnabled(true);
        }
    }

    /*
    método utilizado para que los botones parpadeen en una secuencia concreta al pulsar el boton empezar
     */
    void inicializarTimer (){
        tiempoTarea = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(colores.get(A)==0){
                            parpadear(0);}
                        if(colores.get(A)==1){
                            parpadear(1);
                        }
                        if(colores.get(A)==2){
                            parpadear(2);
                        }
                        if(colores.get(A)==3){
                            parpadear(3);
                        }
                        A++;
                        CONTTIEMPO++;
                        if(CONTTIEMPO==DIFICULT){
                            pararTimer();
                        }

                    }
                });

            }
        };


    }
    /*
    método que llama a inicializarTimer y qeu se le asigna el tiempo que queremos que se ejecute cada boton
     */
    public void empezarTimer(){
        tiempo = new Timer();
        inicializarTimer();
        tiempo.schedule(tiempoTarea, 200, 1000);
    }
    /*
    metodo que hace parar el TimerTask, porque si no se estaria ejecutando sin parar
     */
    public void pararTimer(){
        if (tiempo !=null){
            tiempo.cancel();
            tiempo= null;
        }
    }

}
