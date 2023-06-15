package multiservers;

import java.sql.ResultSet;
import java.util.Scanner;
        
    /**
 *
 * @
 */

public class Tracker {

    public static void main(String[] argumentos) throws Throwable {
        Multiservers controlThread = new Multiservers();
        ClientFullDuplex ClientTracker = new ClientFullDuplex();
        controlThread.start();
        String entradaTeclado;
        String sq = "select IP from peers";
        int proceso = 1;
        
        do {
            Scanner entradaEscaner = new Scanner(System.in);
            entradaTeclado = entradaEscaner.nextLine();
            
            if (entradaTeclado.equals("Actualizar")) {
                DB DataBase = new DB();
                ResultSet rs;
                rs = DataBase.runSql(sq);
                while (rs.next()) {
                    String resp;
                    String Ipresp = rs.getString(1);
                    resp = ClientTracker.Preguntar(Ipresp);
                    String[] parts = resp.split("/");
                    for (int i = 0; i < (parts.length / 2); i++) {
                        DataBase.exe("Update registros set Porcentaje = " + parts[(i + parts.length / 2)] + " where IP = '" + Ipresp + "' && Archivo = '" + parts[i] + "'");
                    }
                }
                DataBase.finalize();
            }

        } while (proceso == 1);
    }

}
