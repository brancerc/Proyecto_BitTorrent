
package clientpeer;

import java.net.*;
import java.io.*;
/**
 *
 * @author Angel
 */
public class ClientFullDuplex {

    public String Preguntar(String datos) throws IOException {

        Socket cliente = null;
        PrintWriter escritor = null;
        String DatosEnviados = null;
        BufferedReader entrada = null;

        String maquina = ClientPeer.IP2;
        int puerto = 12345;
        //BufferedReader DatosTeclado = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("Conectando al Tracker:\n\tEQUIPO = " + ClientPeer.IP2 + "\n\tPORT = " + puerto);

        try {
            cliente = new Socket(maquina, puerto);
        } catch (IOException e) {
            System.out.println("\tFallo 1 en ClientFullDuplex: " + e.toString());
            System.exit(0);
        }
        try {
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (IOException e) {
            System.out.println("\tFallo 2 en ClientFullDuplex: " + e.toString());
            cliente.close();
            System.exit(0);
        }
        String line;

        //System.out.println("\tConectado con el Servidor. Listo para enviar datos...");
        String reg = datos;
        escritor.println(reg);
        line = entrada.readLine();
        System.out.println("\tRespuesta del Tracker: " + line);
        escritor.println("FIN");

        //System.out.println("Finalizada conexion con el servidor\n");
        try {
            escritor.close();
        } catch (Exception e) {
        }
        return line;
    }
}
