import Demo.Response;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class PrinterI implements Demo.Printer
{
    public Response printString(String fullMessage, com.zeroc.Ice.Current current)
    {
        // Extraer el prefijo cliente y el mensaje
        String clientPrefix = "";
        String message = fullMessage;

        int colonIndex = fullMessage.indexOf(": ");
        if(colonIndex != -1) {
            clientPrefix = fullMessage.substring(0, colonIndex);
            message = fullMessage.substring(colonIndex + 2);
        }

        System.out.println("==============================================");
        System.out.println("[Solicitud recibida]");
        System.out.println("  • Cliente : " + (clientPrefix.isEmpty() ? "(desconocido)" : clientPrefix));
        System.out.println("  • Mensaje : " + message);
        System.out.println("----------------------------------------------");

        Response response = null;

        try {
            // Verificar si es un número entero positivo
            if(isPositiveInteger(message)) {
                response = handleFibonacci(Integer.parseInt(message), clientPrefix);
            }
            // Si inicia con "listifs"
            else if(message.startsWith("listifs")) {
                response = handleListInterfaces(clientPrefix);
            }
            // Si inicia con "listports"
            else if(message.startsWith("listports ")) {
                String ip = message.substring(10).trim();
                response = handleListPorts(ip, clientPrefix);
            }
            // Si inicia con "!"
            else if(message.startsWith("!")) {
                String command = message.substring(1).trim();
                response = handleCommand(command, clientPrefix);
            }
            // Mensaje normal
            else {
                System.out.println("[Mensaje normal]");
                System.out.println("  " + (clientPrefix.isEmpty() ? "" : (clientPrefix + ": ")) + message);
                response = new Response(0, "Echo: " + message);
            }
        } catch(Exception e) {
            System.err.println("[Error] Ocurrió un problema procesando el mensaje:");
            System.err.println("  Detalle: " + e.getMessage());
            response = new Response(-1, "Error: " + e.getMessage());
        }

        System.out.println("==============================================\n");
        return response;
    }

    // Verificar si es un número entero positivo
    private boolean isPositiveInteger(String str) {
        try {
            int num = Integer.parseInt(str);
            return num > 0;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    // Manejar serie de Fibonacci y factores primos
    private Response handleFibonacci(int n, String clientPrefix) {
        System.out.println("[Fibonacci]");
        System.out.println("  • Solicitante : " + (clientPrefix.isEmpty() ? "(desconocido)" : clientPrefix));
        System.out.println("  • Parámetro n : " + n);

        // Calcular y mostrar serie de Fibonacci
        System.out.print("  • Serie F(" + n + "): ");
        List<Long> fibSeries = new ArrayList<>();
        for(int i = 0; i <= n; i++) {
            long fib = fibonacci(i);
            fibSeries.add(fib);
            System.out.print(fib + (i < n ? " " : ""));
        }
        System.out.println();

        // Calcular factores primos del n-ésimo término de Fibonacci
        long fibN = fibonacci(n);
        List<Long> primeFactors = getPrimeFactors(fibN);

        System.out.println("  • F(" + n + ") = " + fibN);
        System.out.println("  • Factores primos únicos: " + primeFactors);
        System.out.println("----------------------------------------------");

        return new Response(0, "Factores primos de Fibonacci(" + n + "): " + primeFactors.toString());
    }

    // Calcular n-ésimo término de Fibonacci
    private long fibonacci(int n) {
        if(n <= 1) return n;
        long a = 0, b = 1, c;
        for(int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    // Obtener factores primos únicos de un número
    private List<Long> getPrimeFactors(long n) {
        Set<Long> uniqueFactors = new HashSet<>();

        // Factorizar por 2
        if(n % 2 == 0) {
            uniqueFactors.add(2L);
            while(n % 2 == 0) {
                n /= 2;
            }
        }

        // Factorizar por números impares
        for(long i = 3; i * i <= n; i += 2) {
            if(n % i == 0) {
                uniqueFactors.add(i);
                while(n % i == 0) {
                    n /= i;
                }
            }
        }

        // Si n es un primo mayor que 2
        if(n > 2) {
            uniqueFactors.add(n);
        }

        return new ArrayList<>(uniqueFactors);
    }

    // Manejar listado de interfaces de red
    private Response handleListInterfaces(String clientPrefix) {
        System.out.println("[Listar interfaces de red]");
        System.out.println("  • Solicitante : " + (clientPrefix.isEmpty() ? "(desconocido)" : clientPrefix));

        // PARA WINDOWS
        String result = executeCommand("ipconfig /all");

        // PARA LINUX
        // String result = executeCommand("ip addr show");

        System.out.println("  • Resultado:");
        System.out.println(result.isEmpty() ? "  (sin salida)" : result);
        System.out.println("----------------------------------------------");

        return new Response(0, "Interfaces de red:\n" + result);
    }

    // Manejar listado de puertos
    private Response handleListPorts(String ip, String clientPrefix) {
        System.out.println("[Listar puertos]");
        System.out.println("  • Solicitante : " + (clientPrefix.isEmpty() ? "(desconocido)" : clientPrefix));
        System.out.println("  • IP objetivo : " + ip);

        // Validar IP
        if(!isValidIP(ip)) {
            System.out.println("  • Validación: IP inválida");
            System.out.println("----------------------------------------------");
            return new Response(-1, "Dirección IP inválida: " + ip);
        }

        String result;
        // PARA WINDOWS
        result = executeCommand("netstat -an");
        if(result.contains("Error ejecutando comando") || result.trim().isEmpty()) {
            result = "Información de conexiones activas (netstat)";
        }

        // PARA LINUX
        // result = executeCommand("nmap -sT " + ip);

        System.out.println("  • Puertos abiertos / conexiones activas:");
        System.out.println(result.isEmpty() ? "  (sin salida)" : result);
        System.out.println("----------------------------------------------");

        return new Response(0, "Puertos abiertos en " + ip + ":\n" + result);
    }

    // Validar formato de IP
    private boolean isValidIP(String ip) {
        String ipPattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return Pattern.matches(ipPattern, ip);
    }

    // Manejar ejecución de comandos
    private Response handleCommand(String command, String clientPrefix) {
        System.out.println("[Ejecutar comando]");
        System.out.println("  • Solicitante : " + (clientPrefix.isEmpty() ? "(desconocido)" : clientPrefix));
        System.out.println("  • Comando     : " + command);

        String result = executeCommand(command);

        System.out.println("  • Resultado:");
        System.out.println(result.isEmpty() ? "  (sin salida)" : result);
        System.out.println("----------------------------------------------");

        return new Response(0, "Resultado de '" + command + "':\n" + result);
    }

    // Ejecutar comando del sistema
    private String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            // PARA WINDOWS
            Process process = Runtime.getRuntime().exec("cmd.exe /c " + command);

            // PARA LINUX
            // Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // También leer errores
            while((line = errorReader.readLine()) != null) {
                output.append("ERROR: ").append(line).append("\n");
            }

            process.waitFor();
            reader.close();
            errorReader.close();

        } catch(Exception e) {
            output.append("Error ejecutando comando: ").append(e.getMessage());
        }

        return output.toString();
    }
}
