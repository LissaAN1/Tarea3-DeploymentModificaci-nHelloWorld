# Configuraciones para Pruebas de Performance

## Configuración del Servidor (config.server)
```
#
# Configuración para servidor en máquina principal
#
Printer.Endpoints=tcp -p 9099
Ice.Default.Host=192.168.1.100
```

## Configuración del Cliente 1 (config.client)
```
#
# Configuración para cliente en máquina secundaria
#
Printer.Proxy=SimplePrinter:tcp -h 192.168.1.100 -p 9099
Ice.Default.Host=192.168.1.101
```

## Configuración del Cliente 2 (config.client)
```
#
# Configuración para cliente en máquina terciaria
#
Printer.Proxy=SimplePrinter:tcp -h 192.168.1.100 -p 9099
Ice.Default.Host=192.168.1.102
```

## Instrucciones de Configuración

### Paso 1: Identificar IPs de las Máquinas
```bash
# En cada máquina, ejecutar:
ipconfig  # Windows
# o
ip addr   # Linux
```

### Paso 2: Modificar Archivos de Configuración
1. **Servidor**: Cambiar `Ice.Default.Host` por la IP de la máquina servidor
2. **Clientes**: Cambiar `Printer.Proxy` para apuntar a la IP del servidor
3. **Puertos**: Usar puertos diferentes para cada pareja (9099, 9100, 9101, etc.)

### Paso 3: Ejecutar Pruebas
1. **Servidor**: Ejecutar primero en máquina principal
2. **Cliente 1**: Ejecutar en segunda máquina
3. **Cliente 2**: Ejecutar en tercera máquina
4. **Enviar mensajes simultáneamente** desde ambos clientes

### Paso 4: Medir Performance
- Cada cliente mostrará sus propias estadísticas
- Comparar latencia entre clientes
- Observar cómo afecta la carga múltiple al servidor

## Ejemplo de Resultados Esperados

### Cliente Individual
```
📊 ===== ESTADÍSTICAS DE PERFORMANCE =====
   ⏱ Tiempo total de sesión: 30.5 segundos
   📤 Total de mensajes enviados: 15
   ✅ Mensajes exitosos: 15
   ❌ Mensajes fallidos: 0
   📉 Missing Rate: 0.00%
   🚀 Throughput: 0.49 mensajes/segundo
   🕐 Latencia mínima: 12 ms
   🕐 Latencia máxima: 45 ms
   🕐 Latencia promedio: 23.5 ms
==========================================
```

### Cliente con Carga Múltiple
```
📊 ===== ESTADÍSTICAS DE PERFORMANCE =====
   ⏱ Tiempo total de sesión: 25.2 segundos
   📤 Total de mensajes enviados: 20
   ✅ Mensajes exitosos: 18
   ❌ Mensajes fallidos: 2
   📉 Missing Rate: 10.00%
   🚀 Throughput: 0.71 mensajes/segundo
   🕐 Latencia mínima: 25 ms
   🕐 Latencia máxima: 120 ms
   🕐 Latencia promedio: 67.3 ms
==========================================
```
