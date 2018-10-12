package com.example.ecosistemas.tresenlinea;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class Comunicacion extends Thread {

    DatagramSocket socket;
    int PUERTO = 5000;
    static InetAddress address;

    Recibir r;

    public Comunicacion() {

    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket();
            socket.setReuseAddress(true);

            address = InetAddress.getByName("172.30.120.45");
//            socket.bind(new InetSocketAddress(5000)); // <-- now bind it
            //172.30.120.45
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        while (true) {
            recibir();
        }
    }

    public void recibir() {

        byte[] mensaje = new byte[1000];
        DatagramPacket datagrama = new DatagramPacket(mensaje, mensaje.length);
        try {

            System.out.println("Esperando dato");
            socket.receive(datagrama);
            System.out.println("Dato recivido ........................................................................................");
            String m = new String(datagrama.getData());
            System.out.println("Error ........................................................................................");


            String[] datos = m.split(",");
            final int[] data = new int[10];
            for (int i = 0; i < data.length; i++) {
                data[i] = Integer.parseInt(datos[i].trim());
            }

            r.recibido(data);





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviar(final int[] i) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String mensaje = i[0] + "," + i[1] + "," + i[2] + "," + i[3] + "," + i[4] + "," + i[5] + "," + i[6] + "," + i[7] + "," + i[8] + "," + i[9];
                DatagramPacket datagrama = new DatagramPacket(mensaje.getBytes(), mensaje.length(), address, PUERTO);
                try {

                    socket.send(datagrama);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public interface Recibir {
        public void recibido(int[] data);
    }

    public void setObserver(Recibir r) {
        this.r = r;
    }

/*
    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
        protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }

        protected void onProgressUpdate(Integer... progress) {
            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            showDialog("Downloaded " + result + " bytes");
        }
    }*/
}


