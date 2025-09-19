# Guía de Pruebas de Performance

## Cómo Probar las Mediciones de Performance

### 1. Preparación del Servidor
```bash
# En una máquina (servidor)
cd helloworld-ret/server
./gradlew run
```

### 2. Preparación de Clientes
```bash
# En otras máquinas (clientes)
cd helloworld-ret/client
./gradlew run
```

### 3. Pruebas de Performance

#### Prueba Básica (Un Cliente)
1. Ejecuta el cliente
2. Envía varios mensajes de prueba:
   - `5` (número para Fibonacci)
   - `listifs` (listar interfaces)
   - `listports 192.168.1.1` (listar puertos)
   - `!dir` (comando del sistema)
3. Escribe `stats` para ver estadísticas
4. Escribe `exit` para salir

#### Prueba de Carga (Múltiples Clientes)
1. Ejecuta 2-3 clientes simultáneamente en diferentes máquinas
2. Cada cliente envía mensajes rápidamente
3. Observa cómo cambian las métricas:
   - **Latencia**: Aumenta con más carga
   - **Throughput**: Se mantiene o disminuye
   - **Missing Rate**: Puede aumentar con sobrecarga

### 4. Métricas que se Miden

#### Latencia (Tiempo de Respuesta)
- **Latencia por solicitud**: Se muestra en cada respuesta individual del servidor
- **Latencia promedio por solicitud**: Promedio de todas las latencias medidas
- **Latencia máxima observada**: La latencia más alta registrada
- **Latencia mínima observada**: La latencia más baja registrada
- **Unidad**: Milisegundos (ms)
- **Valores típicos**: 1-100ms en red local

#### Throughput (Rendimiento)
- **Throughput promedio**: Cantidad total de solicitudes por segundo durante toda la sesión
- **Throughput en carga alta**: Medición específica durante prueba de carga (10 mensajes rápidos)
- **Unidad**: solicitudes/segundo
- **Valores típicos**: 0.5-10 solicitudes/segundo

#### Missing Rate (Tasa de Pérdida)
- **Qué mide**: Porcentaje de solicitudes perdidas (fallidas)
- **Unidad**: Porcentaje (%)
- **Valores típicos**: 0-5% en condiciones normales

### 5. Interpretación de Resultados

#### Latencia Alta (>100ms)
- Posible congestión de red
- Servidor sobrecargado
- Problemas de conectividad

#### Throughput Bajo (<10 msg/s)
- Servidor lento
- Procesamiento complejo (Fibonacci grande)
- Limitaciones de red

#### Missing Rate Alto (>10%)
- Problemas de conectividad
- Servidor inestable
- Timeouts de red

### 6. Comandos de Prueba Sugeridos

```
# Pruebas básicas
5
10
listifs
listports 127.0.0.1
!echo "Hola mundo"
!dir

# Ver estadísticas completas
stats

# Prueba de carga (20 mensajes rápidos)
loadtest

# Salir
exit
```

### 6.1. Ejemplo de Respuesta Individual
```
 Ingrese mensaje: 5

Respuesta del servidor:
Estado: 0
Mensaje: Factores primos de Fibonacci(5): [5]
Latencia: 23 ms
-------------------------------------
```

### 7. Escenarios de Prueba

#### Escenario 1: Carga Normal
- 1 cliente, mensajes cada 2-3 segundos
- Esperar latencia <50ms, missing rate <1%

#### Escenario 2: Carga Alta
- 2-3 clientes simultáneos
- Mensajes rápidos (cada 1 segundo)
- Observar degradación de performance

#### Escenario 3: Prueba de Resistencia
- Enviar muchos mensajes seguidos
- Probar con números grandes de Fibonacci (ej: 20, 30)
- Verificar estabilidad del servidor

### 8. Ejemplos de Resultados Esperados

#### Cliente Individual
```
 ===== ESTADÍSTICAS DE PERFORMANCE =====
   Tiempo total de sesión: 30.5 segundos
   Total de mensajes enviados: 15
   Mensajes exitosos: 15
   Mensajes fallidos: 0
   Missing Rate (solicitudes perdidas): 0.00%
   Throughput promedio: 0.49 solicitudes/segundo

   Latencia promedio por solicitud: 23.5 ms
   Latencia máxima observada: 45 ms
   Latencia mínima observada: 12 ms
==========================================
```

#### Cliente con Prueba de Carga
```
===== RESULTADOS PRUEBA DE CARGA =====
Duración: 2.50 segundos
Throughput en carga alta: 4.00 solicitudes/segundo
Missing Rate en carga alta: 0.00%
Mensajes exitosos: 10
Mensajes fallidos: 0
=====================================
```

#### Cliente con Carga Múltiple
```
 ===== ESTADÍSTICAS DE PERFORMANCE =====
   Tiempo total de sesión: 25.2 segundos
   Total de mensajes enviados: 20
   Mensajes exitosos: 18
   Mensajes fallidos: 2
   Missing Rate (solicitudes perdidas): 10.00%
   Throughput promedio: 0.79 solicitudes/segundo

   Latencia promedio por solicitud: 67.3 ms
   Latencia máxima observada: 120 ms
   Latencia mínima observada: 25 ms
==========================================
```
