import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServidor {
    static int contador = 0;
    public static void main(String[] args) throws IOException {
        System.out.println("Creando socket servidor");

        ServerSocket serverSocket = new ServerSocket();

        System.out.println("Realizando el bind");

        String puerto = JOptionPane.showInputDialog("Puerto al que te quieres conectar: ","6000");

        InetSocketAddress addr = new InetSocketAddress("localhost", Integer.parseInt(puerto));
        serverSocket.bind(addr);

        System.out.println("Aceptando conexiones");
        while (true) {
            if (contador == 0) {
                System.out.println("Ningun cliente conectado");
            }
            Socket newSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(newSocket.getOutputStream(), true);

            System.out.println("Conexion recibida");
            getAccess(reader, writer, newSocket);

        }

    }

    public static void getAccess(BufferedReader reader, PrintWriter writer, Socket newSocket) throws IOException {
        contador++;
        String nombre = reader.readLine();
        Sala s = new Sala(nombre, newSocket);
        s.start();
    }
}
