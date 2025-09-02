import java.io.*;

public class Server
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.server",extraArgs))
        {
            if(!extraArgs.isEmpty())
            {
                System.err.println("==============================================");
                System.err.println("[Argumentos no esperados]");
                System.err.println("  • Se recibieron argumentos extra:");
                int idx = 1;
                for(String v: extraArgs){
                    System.err.println("    " + (idx++) + ") " + v);
                }
                System.err.println("==============================================");
            }

            System.out.println("==============================================");
            System.out.println("[Servidor] Inicializando adapter 'Printer'...");
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Printer");
            com.zeroc.Ice.Object object = new PrinterI();

            System.out.println("[Servidor] Registrando objeto con id 'SimplePrinter'...");
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));

            adapter.activate();
            System.out.println("[Servidor] ✅ Adapter activado.");
            System.out.println("[Servidor] ⏳ Esperando solicitudes... (Ctrl+C para detener)");
            System.out.println("==============================================");

            communicator.waitForShutdown();

            // Nota: cuando se interrumpe, waitForShutdown() retorna.
            System.out.println("\n[Servidor] 🔻 Apagado solicitado. Saliendo...");
        }
    }

    public static void f(String m)
    {
        String str = null, output = "";

        InputStream s;
        BufferedReader r;

        try {
            Process p = Runtime.getRuntime().exec(m);

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((str = br.readLine()) != null)
                output += str + System.getProperty("line.separator");
            br.close();

            // Solo presentación (no cambia lógica/flujo del método)
            if (!output.isEmpty()) {
                System.out.println("----------- Salida comando -----------");
                System.out.println(output);
                System.out.println("--------------------------------------");
            }
        }
        catch(Exception ex) {
            // Mantengo el catch vacío para no cambiar la lógica original,
            // solo muestro un aviso visual sin interrumpir el flujo.
            System.err.println("[Aviso] Excepción ejecutando comando: " + ex.getMessage());
        }
    }

}
