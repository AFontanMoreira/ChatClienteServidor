import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class LeerCliente extends Thread {
    Socket clienteSocket;
    BufferedReader reader;
    JTextArea jTextArea1;
    String nombre;
    String mensaje = "";
    iChat chat;


    public LeerCliente(Socket clienteSocket, JTextArea jTextArea1, String nombre, iChat chat) throws IOException {
        this.clienteSocket = clienteSocket;
        this.jTextArea1 = jTextArea1;
        this.nombre = nombre;
        this.chat = chat;
        reader = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
    }

    public void run() {

        try {
            mensaje = reader.readLine();

            if (!mensaje.equals("cupo+")) {
                chat.setVisible(true);

                while (!mensaje.equals(nombre + "->/bye")) {
                    jTextArea1.append(mensaje + "\n");
                    mensaje = reader.readLine();
                }
            }

            chat.setVisible(false);
            clienteSocket.close();

            if (mensaje.equals("cupo+")) {
                JOptionPane.showMessageDialog(null, "Numero de usuarios superado, vuelva a intentarlo mas tarde");
            } else {
                sleep(1000);
            }
            System.exit(0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }catch(NullPointerException e){
            chat.setVisible(false);
            JOptionPane.showMessageDialog(null,"El servidor ha cerrado");
        }
    }
}
