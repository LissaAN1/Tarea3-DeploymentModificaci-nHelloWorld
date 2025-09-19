import Demo.Response;

import java.net.InetAddress;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Client
{
    // Variables para mediciones de performance
    private static List<Long> latencies = new ArrayList<>();
    private static int totalMessages = 0;
    private static int successfulMessages = 0;
    private static int failedMessages = 0;
    private static long startTime = 0;
    
    
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
            System.out.println("- Cliente iniciado correctamente ");
            System.out.println("- Usuario: " + username);
            System.out.println("- Hostname: " + hostname);
            System.out.println("- Prefijo: " + clientPrefix);
            System.out.println("=====================================\n");
            System.out.println("Escriba su mensaje y presione ENTER.");
            System.out.println("Para salir escriba: 'exit'");
            System.out.println("Para ver estadisticas escriba: 'stats'");
            System.out.println("-------------------------------------");

            // Inicializar mediciones de performance
            startTime = System.currentTimeMillis();

            // conexion del cliente
            while(true) {
                System.out.print("\n Ingrese mensaje: ");
                message = scanner.nextLine().trim();

                if("exit".equals(message)) {
                    System.out.println("\n🔴 Cerrando cliente... ¡Hasta luego!");
                    showPerformanceStats();
                    break;
                }

                if("stats".equals(message)) {
                    showPerformanceStats();
                    continue;
                }


                // Anteponer prefijo al mensaje
                String fullMessage = clientPrefix + ": " + message;
                
                // Medir latencia
                long requestStartTime = System.currentTimeMillis();
                totalMessages++;


                try {
                    response = service.printString(fullMessage);
                    
                    // Calcular latencia
                    long requestEndTime = System.currentTimeMillis();
                    long latency = requestEndTime - requestStartTime;
                    latencies.add(latency);
                    successfulMessages++;

                    System.out.println("\nRespuesta del servidor:");
                    System.out.println("Estado: " + response.responseTime); // (0 = éxito, -1 = error).
                    System.out.println("Mensaje: " + response.value);
                    System.out.println("Latencia: " + latency + " ms");
                    System.out.println("-------------------------------------");
                } catch(Exception e) {
                    failedMessages++;
                    System.err.println("\nError al comunicarse con el servidor: " + e.getMessage());
                }
            }

            scanner.close();
        }
    }
    
    // Método para mostrar estadísticas de performance
    private static void showPerformanceStats() {
        long currentTime = System.currentTimeMillis();
        long totalTime = currentTime - startTime;
        
        System.out.println("\n ===== ESTADISTICAS DE PERFORMANCE =====");
        System.out.println("   Tiempo total de sesion: " + (totalTime / 1000.0) + " segundos");
        System.out.println("   Total de mensajes enviados: " + totalMessages);
        System.out.println("   Mensajes exitosos: " + successfulMessages);
        System.out.println("   Mensajes fallidos: " + failedMessages);
        
        // Calcular Missing Rate (Porcentaje de solicitudes perdidas)
        double missingRate = 0.0;
        if (totalMessages > 0) {
            missingRate = (double) failedMessages / totalMessages * 100.0;
        }
        System.out.println("   Missing Rate (solicitudes perdidas): " + String.format("%.2f", missingRate) + "%");
        
        // Calcular Throughput promedio (solicitudes/segundo)
        double avgThroughput = 0.0;
        if (totalTime > 0) {
            avgThroughput = (double) totalMessages / (totalTime / 1000.0);
        }
        System.out.println("   Throughput promedio: " + String.format("%.2f", avgThroughput) + " solicitudes/segundo");
        
        // Calcular estadísticas de Latencia (método básico)
        if (!latencies.isEmpty()) {
            long minLatency = latencies.get(0);
            long maxLatency = latencies.get(0);
            long totalLatency = 0;
            
            // Buscar mínimo y máximo
            for (long latency : latencies) {
                if (latency < minLatency) minLatency = latency;
                if (latency > maxLatency) maxLatency = latency;
                totalLatency += latency;
            }
            
            double avgLatency = (double) totalLatency / latencies.size();
            
            System.out.println("   Latencia promedio por solicitud: " + String.format("%.2f", avgLatency) + " ms");
            System.out.println("   Latencia maxima observada: " + maxLatency + " ms");
            System.out.println("   Latencia minima observada: " + minLatency + " ms");
        } else {
            System.out.println("   No hay datos de latencia disponibles");
        }
        
        System.out.println("==========================================\n");
    }

}
