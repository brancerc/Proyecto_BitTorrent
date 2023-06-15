package clientpeer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class ClientPeer {

    static HashMap<String, Integer> Archivos = new HashMap();
    static String IP;
    static String IP2;
    static String IPhost = IpClient.Obtener();
    static String HostName;
    
    public static void main(String[] args) throws IOException {

        IP = IPhost;
        IP2 = "192.168.196.136";
        Socket cliente = null;
        PrintWriter escritor = null;
        //String DatosEnviados = null;
        BufferedReader entrada = null;

        String maquina = IP2;
        int puerto = 12345;

        //BufferedReader DatosTeclado = new BufferedReader(new InputStreamReader(System.in));
        try {
            cliente = new Socket(maquina, puerto);
        } catch (IOException e) {
            System.out.println("Fallo1 1 ClientPeer: " + e.toString());
            System.exit(0);
        }

        try {
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (IOException e) {
            System.out.println("Fallo1 2 ClientPeer: " + e.toString());
            cliente.close();
            System.exit(0);
        }
        

        
        String line;
        String reg = "Registro/" + IP + "/" + args[0];
        escritor.println(reg);
        line = entrada.readLine();
        System.out.println(line);
        escritor.println("FIN");
        Multiservers ClientServ = new Multiservers();
        ServerDes Srd = new ServerDes();
        Srd.start();
        int porcentaje = Srd.porcentaje;
        ClientServ.start();
        Interfaz Inter = new Interfaz();
        Inter.show(true);
    }
}
