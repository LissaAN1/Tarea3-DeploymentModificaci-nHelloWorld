# Tarea3-DeploymentModificaci-nHelloWorld


Este **Redmi** tiene como objetivo guiarte para ejecutar el código del proyecto **Helloworld** modificado para que el cliente y servidor puedan intercambiar mensajes, incluyendo funcionalidades adicionales. Asegúrate de seguir estos pasos para que puedas ejecutar el proyecto de manera local.

---

### **Requisitos Previos:**

1. **Instalar Java 11:**
   El proyecto requiere **Java 11** para ser ejecutado correctamente. Si aún no tienes **Java 11**, sigue estos pasos:

   * Descarga **Java 11** desde [OpenJDK](https://openjdk.java.net/projects/jdk/11/).
   * Instala **Java 11** siguiendo las instrucciones en el sitio oficial de **OpenJDK** o utilizando **AdoptOpenJDK**.
   * Verifica la instalación de **Java 11** con el siguiente comando:

   ```bash
   java -version
   ```

   La salida debe mostrar algo como esto:

   ```bash
   java version "11.x.x"
   Java(TM) SE Runtime Environment (build 11.x.x)
   Java HotSpot(TM) 64-Bit Server VM (build 11.x.x, mixed mode)
   ```

---

### **Estructura del Proyecto:**

Una vez que hayas recibido el archivo **`helloworld-ret.zip`**, descomprímelo y tendrás la siguiente estructura de archivos:

```
helloworld-ret/
├── client/                  # Carpeta del Cliente
│   ├── build/
│   ├── libs/
│   └── src/
├── server/                  # Carpeta del Servidor
│   ├── build/
│   ├── libs/
│   └── src/
└── .config/                 # Configuración de red (host y puerto)
```

---

### **Ejecución Local del Proyecto:**

1. **Ejecuta el servidor en tu máquina local**:
   Navega al directorio del **servidor** y ejecuta el siguiente comando:

   ```bash
   java -jar server/build/libs/server.jar
   ```

2. **Ejecuta el cliente en las otras máquinas**:
   En las otras máquinas (o en el mismo equipo para pruebas locales), navega al directorio del **cliente** y ejecuta:

   ```bash
   java -jar client/build/libs/client.jar
   ```

3. **Pruebas de funcionalidad**:

   * **Cliente**: En la consola, el cliente pedirá mensajes de forma continua hasta que se ingrese **`exit`**.
   * **Servidor**: El servidor procesará los mensajes y responderá con la funcionalidad correspondiente, ya sea calculando la serie de Fibonacci, mostrando las interfaces de red, los puertos abiertos, o ejecutando un comando del sistema.

4. **Repetir hasta que el mensaje sea `exit`**.

### **Mediciones de Atributos de Calidad (Performance)**

* **Latencia**: Puedes medir el tiempo de respuesta entre el envío del mensaje desde el cliente y la recepción de la respuesta en el cliente usando un cronómetro o una herramienta de monitoreo.

* **Rendimiento**: Puedes medir la cantidad de mensajes que el servidor puede procesar por segundo. Esto se puede hacer utilizando herramientas de carga como **Apache JMeter** o haciendo un análisis manual de cuántos mensajes pueden ser enviados por segundo.

---
