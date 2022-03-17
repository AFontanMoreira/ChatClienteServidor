import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
public class Sala extends Thread {
    Socket newSocket;
    public static ArrayList<Sala> usuarios = new ArrayList();
    BufferedReader reader ;
    PrintWriter writer;
    String mensaje = "";


    public Sala(String str, Socket newSocket) throws IOException {
        super(str);
        this.newSocket = newSocket;
        usuarios.add(this);
        reader = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
        writer = new PrintWriter(newSocket.getOutputStream(),true);
    }

    public void run() {
        if(usuarios.size()<=10) {
            String conexion = getName() + " se ha conectado a la sala. Hay "+usuarios.size()+" usuario/os conectados";
            for (int i = 0; i < usuarios.size(); i++) {
                try {
                    usuarios.get(i).escritura(conexion);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {


                while (!mensaje.equals("/bye")) {
                    mensaje = reader.readLine();
                    String name = getName();
                    for (int x = 0; x < usuarios.size(); x++) {
                        usuarios.get(x).escritura(name + "->" + mensaje);
                    }
                }
                usuarios.remove(this);
                String desconexion = getName() + " se ha desconectado de la sala. Hay "+usuarios.size()+" usuario/os conectados";

                for (int i = 0; i < usuarios.size(); i++) {
                    try {
                        usuarios.get(i).escritura(desconexion);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ChatServidor.contador--;
                if (ChatServidor.contador == 0){
                    System.out.println("Ningun cliente conectado");
                }

                newSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            writer.println("cupo+");
            usuarios.remove(this);
            try {
                newSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void escritura(String mensaje) throws IOException {
        writer.println(mensaje);
    }
}

