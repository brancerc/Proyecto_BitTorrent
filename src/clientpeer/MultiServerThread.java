package clientpeer;

import java.net.*;
import java.io.*;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class MultiServerThread extends Thread {

    private Socket socket = null;
    Socket cliente = null;
    PrintWriter escritor = null;
    String DatosEnviados = null;
    BufferedReader entrada = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
        Multiservers.NoClients++;
    }

    public void EnviarArchivo(String Destino, String Archivo) throws IOException {
        DataInputStream input;
        BufferedInputStream bis;
        BufferedOutputStream bos;
        int in;
        byte[] byteArray;
        //Obtenemos el fichero del usuario
        String dir = System.getProperty("user.home");
        //Añadimos Desktop
        final String filename = dir + "/Desktop/" + Archivo;
        //final String filename = "/Users/alonsobernal/Desktop/" + Archivo;

        try {
            final File localFile = new File(filename);
            Socket client = new Socket(Destino, 4040);
            
            bis = new BufferedInputStream(new FileInputStream(localFile));
            bos = new BufferedOutputStream(client.getOutputStream());
            
            //Enviamos el nombre del fichero
            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            // dos.size();
            int Tam = (int) localFile.length();
            System.out.println(Tam/10024+" MB");
            
            String TamA = Integer.toString(Tam);
            FileInputStream Fichero = new FileInputStream(localFile);
            Byte contenido = new Byte((byte) (int) localFile.length());

            dos.writeUTF(localFile.getName());
            dos.writeUTF(TamA);
            
            //Enviamos el fichero
            byteArray = new byte[8192];
            int y = 0;
            while ((in = bis.read(byteArray)) != -1) {
                y++;
                //System.out.println(y);
                bos.write(byteArray, 0, in);
                Thread.sleep(50);
            }
            bis.close();
            bos.close();

        } catch (IOException | InterruptedException e) {
            System.err.println(e);
        }

    }

    public String conexion(String maquina, int puerto, String peticion) throws IOException {

        try {
            cliente = new Socket(maquina, puerto);
        } catch (IOException e) {
            System.out.println("MultiServerThread : ERROR 1 - " + e.toString());
            System.exit(0);
        }
        try {
            escritor = new PrintWriter(cliente.getOutputStream(), true);
            entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));

        } catch (IOException e) {
            System.out.println("MultiServerThread : ERROR 2 - " + e.toString());
            cliente.close();
            System.exit(0);
        }
        String line;
        DatosEnviados = peticion;
        escritor.println(DatosEnviados);
        line = entrada.readLine();
        try {
            escritor.close();
        } catch (Exception e) {
        }
        System.out.println("Respuesta obtenida");
        return line;
    }

    public void run() {

        try {
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String lineIn, lineOut;

            while ((lineIn = entrada.readLine()) != null) {
                System.out.println("MultiServerThread: Recibido " + lineIn);
                escritor.flush();
                
                if (lineIn.equals("FIN")) {
                    Multiservers.NoClients--;
                    break;
                    
                } else if (lineIn.equals("#")) {
                    escritor.println(Multiservers.NoClients);
                    escritor.flush();

                } else if (lineIn.equals("Actualizar")) {
                    String UpdateA = "";
                    String UpdateP = "";
                    String Update;
                    if (!ClientPeer.Archivos.isEmpty()) {
                        for (Map.Entry<String, Integer> entry : ClientPeer.Archivos.entrySet()) {
                            UpdateA = UpdateA + entry.getKey() + "/";
                            UpdateP = UpdateP + entry.getValue() + "/";
                        }
                        Update = UpdateA + UpdateP;
                        Update = Update.substring(0, Update.length() - 1);
                        System.out.println(Update);
                        escritor.println(Update);
                        escritor.flush();
                    } else {
                        escritor.println("Vacio");
                        escritor.flush();
                    }
                } else if (lineIn.startsWith("Descarga")) {

                    String[] parts = lineIn.split(" ");
                    String Destino = parts[1];
                    String Archivo = parts[2];
                    System.out.println("MultiServerThread: Obtencion de datos");
                    System.out.println("MultiServerThread: Iniciando peticion");
                    System.out.println("MultiServerThread: " + Destino +" "+Archivo);
                    this.EnviarArchivo(Destino, Archivo);
                    System.out.println("Acabando de enviar");
                } else {
                    escritor.println(lineIn);
                    escritor.flush();
                }
            }
            try {
                entrada.close();
                escritor.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("MultiServerThread : ERROR 3 -: " + e.toString());
                socket.close();
                System.exit(0);
            }
        } catch (IOException e) {
        } catch (Throwable ex) {
            Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
