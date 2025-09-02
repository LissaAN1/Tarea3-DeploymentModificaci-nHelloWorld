import Demo.Response;

import java.net.InetAddress;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            Response response = null;
            Demo.PrinterPrx service = Demo.PrinterPrx
                    .checkedCast(communicator.propertyToProxy("Printer.Proxy"));

            if(service == null)
            {
                throw new Error("Invalid proxy");
            }

            // Obtener username y hostname del cliente
            String username = System.getProperty("user.name");
            String hostname = "";
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch(Exception e) {
                hostname = "localhost";
            }
            String clientPrefix = username + "@" + hostname;

            Scanner scanner = new Scanner(System.in);
            String message = "";

            System.out.println("=====================================");
            System.out.println("   🚀 Cliente iniciado correctamente ");
            System.out.println("   👤 Usuario: " + username);
            System.out.println("   💻 Hostname: " + hostname);
            System.out.println("   📌 Prefijo: " + clientPrefix);
            System.out.println("=====================================\n");
            System.out.println("Escriba su mensaje y presione ENTER.");
            System.out.println("Para salir escriba: 'exit'");
            System.out.println("-------------------------------------");

            // conexion del cliente
            while(true) {
                System.out.print("\n👉 Ingrese mensaje: ");
                message = scanner.nextLine().trim();

                if("exit".equals(message)) {
                    System.out.println("\n🔴 Cerrando cliente... ¡Hasta luego!");
                    break;
                }

                // Anteponer prefijo al mensaje
                String fullMessage = clientPrefix + ": " + message;

                try {
                    response = service.printString(fullMessage);

                    System.out.println("\n✅ Respuesta del servidor:");
                    System.out.println("   ⏱ Estado: " + response.responseTime); // (0 = éxito, -1 = error).
                    System.out.println("   📩 Mensaje: " + response.value);
                    System.out.println("-------------------------------------");
                } catch(Exception e) {
                    System.err.println("\n⚠️ Error al comunicarse con el servidor: " + e.getMessage());
                }
            }

            scanner.close();
        }
    }
}
