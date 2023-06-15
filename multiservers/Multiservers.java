package multiservers;

import java.net.*; 
import java.io.*; 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Brando
 */
public class Multiservers extends Thread {

    static int NoClients = 0;
    static int NoRegistro = 1;

    public void run() {
        DB DataBase = new DB();
        try {
            ResultSet rs;
            rs = DataBase.runSql("select * from registros");
            if (rs != null) {
                int count = 0;
                while (rs.next()) {
                    count++;
                }
                NoRegistro = count + 1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Multiservers.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Declaramos una nueva instancia de la base de datos para determinar el numero de registros
        
        ServerSocket socketServidor = null;
        Socket socketCliente = null;
        
        try {
            socketServidor = new ServerSocket(12345);
        } catch (IOException e) {
            System.out.println("Error : " + e.toString());
            System.exit(0);
        }

        System.out.println(" **Tracker en red**");
        int enproceso = 1;
        while (enproceso == 1) {
            try {
                socketCliente = socketServidor.accept();
                MultiServerThread controlThread = new MultiServerThread(socketCliente);
                controlThread.start();
            } catch (IOException e) {
                System.out.println("Error : " + e.toString());
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
