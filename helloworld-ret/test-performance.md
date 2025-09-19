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
- **Qué mide**: Tiempo desde que se envía el mensaje hasta que llega la respuesta
- **Unidad**: Milisegundos (ms)
- **Valores típicos**: 1-100ms en red local

#### Throughput (Rendimiento)
- **Qué mide**: Cantidad de mensajes procesados por segundo
- **Unidad**: mensajes/segundo
- **Valores típicos**: 10-100 mensajes/segundo

#### Missing Rate (Tasa de Pérdida)
- **Qué mide**: Porcentaje de mensajes que fallan o se pierden
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

# Ver estadísticas
stats

# Salir
exit
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
