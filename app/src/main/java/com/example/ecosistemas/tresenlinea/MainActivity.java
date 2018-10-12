package com.example.ecosistemas.tresenlinea;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Comunicacion.Recibir{

    public int turno;
    public boolean ganador;
    int[] seleccion;
    ImageButton[] boton;
    public ArrayList<Casilla> casillas;
    Comunicacion c;
    int[] temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temp = new int[10];
        casillas = new ArrayList<>();
        seleccion = new int[10];
        for(int i = 0; i < seleccion.length; i++){
            casillas.add(new Casilla(this));
        }

        c = new Comunicacion();
        c.setObserver(this);
        c.start();

        int numero = (int) (Math.random() * 5);

        if (numero%2 == 0){
            turno = 1;
        }else{
            turno = 2;
        }

        boton = new ImageButton[9];

        boton[0] = findViewById(R.id.ib_1);
        boton[1] = findViewById(R.id.ib_2);
        boton[2] = findViewById(R.id.ib_3);
        boton[3] = findViewById(R.id.ib_4);
        boton[4] = findViewById(R.id.ib_5);
        boton[5] = findViewById(R.id.ib_6);
        boton[6] = findViewById(R.id.ib_7);
        boton[7] = findViewById(R.id.ib_8);
        boton[8] = findViewById(R.id.ib_9);


        for (ImageButton btn: boton){
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(ganador == false) {
            switch (v.getId()) {
                case R.id.ib_1:
                    seleccion[0] = casillas.get(0).turno();
                    cambiarImg(0);
                    break;

                case R.id.ib_2:
                    seleccion[1] = casillas.get(1).turno();
                    cambiarImg(1);
                    break;

                case R.id.ib_3:
                    seleccion[2] = casillas.get(2).turno();
                    cambiarImg(2);
                    break;

                case R.id.ib_4:
                    seleccion[3] = casillas.get(3).turno();
                    cambiarImg(3);
                    break;

                case R.id.ib_5:
                    seleccion[4] = casillas.get(4).turno();
                    cambiarImg(4);
                    break;

                case R.id.ib_6:
                    seleccion[5] = casillas.get(5).turno();
                    cambiarImg(5);
                    break;

                case R.id.ib_7:
                    seleccion[6] = casillas.get(6).turno();
                    cambiarImg(6);
                    break;

                case R.id.ib_8:
                    seleccion[7] = casillas.get(7).turno();
                    cambiarImg(7);
                    break;

                case R.id.ib_9:
                    seleccion[8] = casillas.get(8).turno();
                    cambiarImg(8);
                    break;
            }
        }
        if(validar(seleccion, 1)){
            Toast.makeText(MainActivity.this, "Gano jugador 1", Toast.LENGTH_SHORT).show();
            ganador = true;
        }else if(validar(seleccion, 2)){
            Toast.makeText(MainActivity.this, "Gano jugador 2", Toast.LENGTH_SHORT).show();
            ganador = true;
        }

        refresh();

    }

    public void cambiarImg(int i){
        if(ganador == false) {
            if (turno == 1) {
                boton[i].setImageResource(R.drawable.circulo);
            } else if (turno == 2) {
                boton[i].setImageResource(R.drawable.formax);
            }
        }
    }

    public void refresh(){
        c.enviar(seleccion);
    }

    public void actualizar(int i, int turn){
            if (turn == 1) {
                boton[i].setImageResource(R.drawable.circulo);
            } else if (turn == 2) {
                boton[i].setImageResource(R.drawable.formax);
            }
    }

    public void cambiarTurno(){
        if(turno == 1){
            turno = 2;
        }else if(turno == 2){
            turno = 1;
        }
    }

    public void actualiza(int[] data) {

        for(int i = 0 ; i < seleccion.length-1; i++) {
            seleccion[i] = data[i];
            actualizar(i, seleccion[i]);
        }

        if(validar(seleccion, 1)){
            Toast.makeText(MainActivity.this, "Gano jugador 1", Toast.LENGTH_SHORT).show();
            ganador = true;
        }else if(validar(seleccion, 2)){
            Toast.makeText(MainActivity.this, "Gano jugador 2", Toast.LENGTH_SHORT).show();
            ganador = true;
        }

    }

    public boolean validar(int dato[], int criterio) {
        boolean ganador = false;
        if (dato[0] == criterio && dato[1] == criterio && dato[2] == criterio) {
            ganador = true;
        } else if (dato[3] == criterio && dato[4] == criterio && dato[5] == criterio) {
            ganador = true;
        } else if (dato[6] == criterio && dato[7] == criterio && dato[8] == criterio) {
            ganador = true;
        } else if (dato[0] == criterio && dato[3] == criterio && dato[6] == criterio) {
            ganador = true;
        } else if (dato[1] == criterio && dato[4] == criterio && dato[7] == criterio) {
            ganador = true;
        } else if (dato[2] == criterio && dato[5] == criterio && dato[8] == criterio) {
            ganador = true;
        } else if (dato[0] == criterio && dato[4] == criterio && dato[8] == criterio) {
            ganador = true;
        } else if (dato[2] == criterio && dato[4] == criterio && dato[6] == criterio) {
            ganador = true;
        }
        return ganador;
    }


    @Override
    public void recibido(int[] data) {
       System.out.println(data[0] + "," +data[1]+ "," +data[2]+ "," +data[3]+ "," +data[4]);
      // actualiza(data);
        temp = data;
        turno = temp[9];

        new Actualizar_Interfaz().execute();

    }


    private class Actualizar_Interfaz extends AsyncTask<Void, Void, int[]> {




        @Override
        protected int[] doInBackground(Void... voids) {
            return new int[0];
        }

        @Override
        protected void onPostExecute(int[] ints) {

            Toast.makeText(MainActivity.this, temp[0]+","+temp[1]+","+temp[2]+","+temp[3]+","+temp[4]+","+temp[5]+","+temp[6]+","+temp[7]+","+temp[8]+","+temp[9], Toast.LENGTH_SHORT).show();
            actualiza(temp);
        }
    }

}
