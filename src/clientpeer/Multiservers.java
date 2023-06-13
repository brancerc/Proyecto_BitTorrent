package clientpeer;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Angel
 */
public class Multiservers extends Thread {

    static int NoClients = 0;
    static int NoRegistro = 1;

    public void run() {
        ServerSocket socketServidor = null;
        Socket socketCliente = null;

        try {
            socketServidor = new ServerSocket(8000);
        } catch (IOException e) {
            System.out.println("Multiservers: ERROR 2- " + e.toString());
            System.exit(0);
        }

        System.out.println("\t**Iniciado servidor PEER**");
        int enproceso = 1;
        while (enproceso == 1) {
            try {
                socketCliente = socketServidor.accept();
                MultiServerThread controlThread = new MultiServerThread(socketCliente);
                controlThread.start();
            } catch (IOException e) {
                System.out.println("Multiservers: ERROR 2- " + e.toString());
                try {
                    socketServidor.close();
                } catch (IOException ex) {
                    Logger.getLogger(Multiservers.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.exit(0);
            }
        }
        System.out.println("Finalizando Servidor...");

    }
}
