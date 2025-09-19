# Tarea3-DeploymentModificaci-nHelloWorld


### **Requisitos Previos:**

1. **Instalar Java 11:**
   El proyecto requiere **Java 11** para ser ejecutado correctamente. Si aún no tienes **Java 11**, sigue estos pasos:

   * Descarga **Java 11** desde [OpenJDK](https://openjdk.java.net/projects/jdk/11/).
   * Instala **Java 11** siguiendo las instrucciones en el sitio oficial de **OpenJDK** o utilizando **AdoptOpenJDK**.

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
└── .config/                 
```

---

### **Ejecución Local del Proyecto:**

0. **Compilacion del proyecto**
  Compila el proyecto ejecutando el siguiente comando en la terminal:
   ```bash
   gradle build
   ``` 



1. **Ejecuta el servidor en tu máquina local**:
   Navega al directorio del **proyecto** y ejecuta el siguiente comando:

   ```bash
   java -jar server/build/libs/server.jar
   ```

2. **Ejecuta el cliente en las otras máquinas**:
   En las otras máquinas (o en el mismo equipo para pruebas locales), navega al directorio del **proyecto** y ejecuta:

   ```bash
   java -jar client/build/libs/client.jar
   ```

3. **Pruebas de funcionalidad**:
   - **(A)** Número entero positivo → Fibonacci y factores primos
   - **(B)** `listifs` → Interfaces lógicas del servidor
   - **(C)** `listports <IPv4>` → Puertos y servicios abiertos
   - **(D)** `!<comando>` → Ejecutar comando en el servidor
   - **(E)** Finalización del ciclo con `exit`

---


### **Mediciones de Atributos de Calidad (Performance)**



---
