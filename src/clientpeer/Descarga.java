package clientpeer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Angel
 */
public class Descarga extends Thread {

    public void run() {
        ClientDesc Cliente = new ClientDesc();
        String Descarga = "Descarga";
        String Arch = Interfaz.Archiv; 
        Descarga = Descarga + " " + Interfaz.Ipp + " " + Interfaz.Archiv;
        //System.out.println(Descarga);
        String Resultado = null;
        if (!Interfaz.Archiv.equals("")) {
            try {
                Resultado = Cliente.Preguntar(Descarga, Interfaz.Direccion);
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Descarga.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
