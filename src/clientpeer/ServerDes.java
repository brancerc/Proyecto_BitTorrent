package clientpeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class ServerDes extends Thread {

    static int NoClients = 0;
    static int NoRegistro = 1;
    public int porcentaje = 0;

    public void run() {
        ServerSocket socketServidor = null;
        Socket socketCliente = null;

        try {
            socketServidor = new ServerSocket(4040);
        } catch (IOException e) {
            System.out.println("Error1 en ServerDes: " + e.toString());
            System.exit(0);
        }

        System.out.println("\t" + "**Servidor de descarga iniciado**");
        int enproceso = 1;
        while (enproceso == 1) {
            try {
                socketCliente = socketServidor.accept();
                ProcesoDesc controlThread = new ProcesoDesc(socketCliente);
                controlThread.start();
                porcentaje = controlThread.porcentaje;
            } catch (IOException e) {
                System.out.println("Error2 en ServerDes: " + e.toString());
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
