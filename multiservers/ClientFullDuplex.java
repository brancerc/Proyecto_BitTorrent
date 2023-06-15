package multiservers;

import java.net.*;
import java.io.*;

/**
 *
 * @author Brando
 */
public class ClientFullDuplex {

    public String Preguntar(String IP) throws IOException {

        Socket cliente = null;
        PrintWriter escritor = null;
        String DatosEnviados = null;
        BufferedReader entrada = null;

        String maquina = IP;
        int puerto = 8080;
   
        System.out.println("Establecidos valores por defecto:\nEQUIPO = localhost\nPORT = 8080");

        try {
            cliente = new Socket(maquina, puerto);
        } catch (IOException e) {
            System.out.println("ClientFullDuplex: ERROR1- : " + e.toString());
            System.exit(0);
        }
        try {
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (IOException e) {
            System.out.println("ClientFullDuplex: ERROR1- " + e.toString());
            cliente.close();
            System.exit(0);
        }
        String line;

        System.out.println("Conectado con el Servidor. Listo para enviar datos...");
        String reg = "Actualizar";
        escritor.println(reg);
        line = entrada.readLine();
        System.out.println(line);

        System.out.println("Finalizada conexion con el servidor");
        try {
            escritor.close();
        } catch (Exception e) {
        }
        return line;
    }
}
