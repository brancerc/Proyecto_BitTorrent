package clientpeer;

import java.net.*;
import java.io.*;

/**
 *
 * @author Angel
 */
public class ClientDesc {

    public String Preguntar(String datos, String IP) throws IOException, InterruptedException {

        Socket cliente = null;
        PrintWriter escritor = null;
        String DatosEnviados = null;
        BufferedReader entrada = null;

        String maquina = IP;
        int puerto = 8000;

        //BufferedReader DatosTeclado = new BufferedReader(new InputStreamReader(System.in));

        //System.out.println("\n\nClientDesc: Conectando a Peer " + IP);

        try {
            cliente = new Socket(maquina, puerto);
        } catch (IOException e) {
            System.out.println("Fallo 2 en ClientDesc: " + e.toString());
            System.exit(0);
        }
        try {
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (IOException e) {
            System.out.println("Fallo 2 en ClientDesc: " + e.toString());
            cliente.close();
            System.exit(0);
        }
        String line;

        //System.out.println("\tConectado con el Servidor. Listo para enviar datos...");
        String reg = datos;
        escritor.println(reg);
        line = entrada.readLine();
        //System.out.println("Respuesta del Peer: " + line);
        //System.out.println("Descarga terminada");

        escritor.println("FIN");

        System.out.println("Finalizada conexion con el servidor");
        try {
            escritor.close();
        } catch (Exception e) {
        }
        return line;
    }
}
