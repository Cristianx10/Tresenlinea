package com.example.ecosistemas.tresenlinea;

public class Casilla {
    public boolean clickeado;
    int status;

    MainActivity activity;

    public Casilla(MainActivity activity){
        this.activity = activity;
    }

    public int turno(){
        if((activity.ganador == false && clickeado == false)|| status ==0) {

                if (activity.turno == 1) {
                    status = 1;
                } else if (activity.turno == 2) {
                    status = 2;
                }
                activity.cambiarTurno();
                clickeado = true;

        }
        return status;
    }

}
