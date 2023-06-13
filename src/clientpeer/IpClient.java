package clientpeer;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Angel
 */
public class IpClient {

    public static String Obtener() {

        try {
            Enumeration<NetworkInterface> interfaces
                    = NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {

                NetworkInterface interfaz = interfaces.nextElement();
                Enumeration<InetAddress> direcciones = interfaz.getInetAddresses();

                while (direcciones.hasMoreElements()) {

                    InetAddress direccion = direcciones.nextElement();

                    if (direccion instanceof Inet4Address
                            && !direccion.isLoopbackAddress()) {
                        String ipv4 = direccion.toString();
                        if (ipv4.startsWith("/10.147.17")) {
                            ipv4 = ipv4.replace("/", "");
                            //System.out.println(ipv4);
                            return ipv4;
                        }
                    }
                }
            }

        } catch (SocketException e) {
            System.err.println("Error al optener IP " + e.getMessage());
        }
        return null;
    }

}
