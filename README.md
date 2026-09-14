![Duoc UC](https://www.duoc.cl/wp-content/uploads/2022/09/logo-0.png)

# 🧠 Semana 5 - Actividad Sumativa 2 - Desarrollo Orientado a Objetos II

## 👤 Autor del proyecto

* **Nombre completo:** Mauricio Francisco Valenzuela Fuentes
* **Carrera:** Analista Programador Computacional
* **Sede:** Online

---

## 📘 Descripción general del sistema

Este proyecto corresponde a la **Actividad Sumativa 2** de la asignatura **Desarrollo Orientado a Objetos II**.

Se trata de la quinta etapa de **SistemaSpeedFast**, una aplicación desarrollada en Java que representa el funcionamiento de la empresa **Speed Fast**, dedicada al reparto a domicilio.

En esta versión se incorpora programación concurrente con acceso a un recurso compartido. Varios repartidores trabajan de manera simultánea y retiran pedidos desde una misma zona de carga.

El objetivo principal es evitar que dos repartidores retiren el mismo pedido al mismo tiempo, utilizando mecanismos de sincronización para proteger el acceso a los datos compartidos.

El sistema permite:

* Crear pedidos con una dirección de entrega.
* Registrar pedidos inicialmente con estado `PENDIENTE`.
* Almacenar pedidos en una zona de carga compartida.
* Ejecutar tres repartidores de forma concurrente.
* Retirar pedidos de manera sincronizada.
* Evitar que un mismo pedido sea retirado por más de un repartidor.
* Cambiar el estado de un pedido a `EN_REPARTO`.
* Simular el tiempo de entrega.
* Cambiar finalmente el estado a `ENTREGADO`.
* Mostrar por consola la actividad realizada por cada repartidor.
* Esperar la finalización de todos los hilos antes de terminar el programa.

---

## 🧱 Estructura general del proyecto

```text
📁 SistemaSpeedFast_v5/
│
└── 📁 Semana 5/
    │
    └── 📁 SistemaSpeedFast_v5/
        │
        ├── 📁 src/
        │   ├── 📁 app/
        │   │   └── Main.java
        │   │
        │   ├── 📁 hilos/
        │   │   └── Repartidor.java
        │   │
        │   └── 📁 model/
        │       ├── EstadoPedido.java
        │       ├── Pedido.java
        │       └── ZonaDeCarga.java
        │
        ├── 📄 .gitignore
        ├── 📄 SistemaSpeedFast_v5.iml
        └── 📄 README.md
```

---

## 🧩 Organización por paquetes

El proyecto se encuentra organizado en tres paquetes principales:

### 1. `app`

Contiene la clase encargada de iniciar y coordinar la simulación.

#### `Main.java`

La clase `Main` realiza las siguientes tareas:

* Crea una instancia compartida de `ZonaDeCarga`.
* Crea cinco pedidos.
* Agrega los pedidos a la zona de carga.
* Crea tres repartidores.
* Utiliza un `ExecutorService` con tres hilos.
* Ejecuta los repartidores de forma concurrente.
* Espera que todos los repartidores terminen sus entregas.
* Muestra un mensaje cuando todos los pedidos han sido entregados.

Para ejecutar los repartidores se utiliza:

```java
ExecutorService executor =
        Executors.newFixedThreadPool(3);
```

Cada repartidor es enviado al `ExecutorService` mediante:

```java
executor.execute(repartidor);
```

Finalmente, el programa espera que todos los hilos terminen antes de finalizar la ejecución.

---

### 2. `model`

Contiene las clases relacionadas con los pedidos y el recurso compartido.

#### `EstadoPedido.java`

Enum que representa los posibles estados de un pedido:

```text
PENDIENTE
EN_REPARTO
ENTREGADO
```

Cada pedido comienza en estado `PENDIENTE`.

Cuando un repartidor retira el pedido desde la zona de carga, cambia a:

```text
EN_REPARTO
```

Después de finalizar la entrega, cambia a:

```text
ENTREGADO
```

El flujo de estados utilizado es:

```text
PENDIENTE → EN_REPARTO → ENTREGADO
```

---

#### `Pedido.java`

Representa un pedido dentro del sistema SpeedFast.

Contiene los atributos:

* `id`
* `direccionEntrega`
* `estado`

El estado se almacena utilizando el enum `EstadoPedido`.

El método:

```java
setEstado(String nuevoEstado)
```

permite actualizar el estado del pedido convirtiendo el valor recibido al tipo `EstadoPedido`.

La clase también incluye:

* Constructor.
* Getters.
* Setters.
* Método `toString()`.

---

#### `ZonaDeCarga.java`

Representa el recurso compartido entre todos los repartidores.

Internamente utiliza una lista de pedidos:

```java
List<Pedido>
```

Los métodos principales son:

```java
agregarPedido(Pedido pedido)
```

y:

```java
retirarPedido()
```

Ambos métodos utilizan la palabra clave:

```java
synchronized
```

De esta forma, solamente un hilo puede ejecutar cada método sincronizado sobre la misma instancia de `ZonaDeCarga` en un momento determinado.

Esto permite evitar que dos repartidores retiren simultáneamente el mismo pedido.

---

### 3. `hilos`

Contiene las clases que representan tareas ejecutadas concurrentemente.

#### `Repartidor.java`

La clase `Repartidor` implementa:

```java
Runnable
```

Cada repartidor posee:

* Un nombre.
* Una referencia a la misma `ZonaDeCarga`.

En el método `run()`, cada repartidor realiza el siguiente proceso:

```text
Retirar pedido
      ↓
Cambiar a EN_REPARTO
      ↓
Simular entrega
      ↓
Cambiar a ENTREGADO
```

La entrega se simula mediante:

```java
Thread.sleep(2000);
```

Cada repartidor continúa retirando pedidos mientras existan pedidos disponibles en la zona de carga.

Cuando ya no quedan pedidos, el hilo finaliza.

---

## 🔒 Sincronización del recurso compartido

El principal problema abordado en esta versión es el acceso simultáneo de varios repartidores a una misma zona de carga.

Los tres repartidores utilizan la misma instancia.

Sin sincronización, varios hilos podrían intentar modificar la lista de pedidos al mismo tiempo.

Para controlar este acceso, los métodos de `ZonaDeCarga` se encuentran sincronizados:

```java
public synchronized void agregarPedido(Pedido pedido)
```

```java
public synchronized Pedido retirarPedido()
```

Cuando un repartidor está ejecutando `retirarPedido()`, los demás deben esperar hasta que termine la operación.

Una vez retirado un pedido mediante:

```java
pedidos.remove(0);
```

el pedido deja de encontrarse en la zona de carga y no puede ser retirado nuevamente por otro repartidor.

De esta manera se evita el retiro duplicado de pedidos y se protege el recurso compartido frente a condiciones de carrera.

---

## ⚙️ Ejecución concurrente

El sistema utiliza tres repartidores:

```text
Juan
Camila
Pedro
```

Los tres son ejecutados utilizando un grupo de tres hilos:

```java
Executors.newFixedThreadPool(3)
```

Los repartidores trabajan simultáneamente, por lo que el orden de los mensajes mostrados en consola puede variar entre distintas ejecuciones.

Esta variación es parte del comportamiento esperado de una aplicación concurrente.

Por ejemplo, un repartidor puede terminar una entrega mientras otro comienza a retirar un nuevo pedido.

---

## ⚠️ Manejo de interrupciones

El proceso de entrega utiliza:

```java
Thread.sleep(2000);
```

Debido a que este método puede producir una excepción `InterruptedException`, se utiliza un bloque `try-catch`.

En caso de interrupción se restaura el estado del hilo mediante:

```java
Thread.currentThread().interrupt();
```

y se finaliza correctamente la ejecución del repartidor.

El `Main` también controla una posible interrupción mientras espera la finalización del `ExecutorService`.

---

## ♻️ Organización y mantenibilidad

El proyecto separa las principales responsabilidades del sistema entre distintas clases.

### `Pedido`

Mantiene la información y el estado de cada pedido.

### `EstadoPedido`

Define los estados válidos que puede tener un pedido.

### `ZonaDeCarga`

Administra el recurso compartido y controla el acceso concurrente a los pedidos.

### `Repartidor`

Representa una tarea concurrente encargada de retirar y entregar pedidos.

### `Main`

Configura los elementos necesarios, inicia los hilos y controla la finalización de la simulación.

Esta separación permite mantener cada responsabilidad en una clase específica y facilita la comprensión y modificación del código.

---

## ⚙️ Instrucciones para clonar y ejecutar el proyecto

1. Clona el repositorio desde GitHub:

```bash
git clone https://github.com/mauvalenzuelaf-oss/SistemaSpeedFast_v5.git
```

2. Abre **IntelliJ IDEA**.

3. Selecciona la opción `Open`.

4. Dentro del repositorio clonado, busca la siguiente carpeta:

```text
Semana 5
```

5. Selecciona `SistemaSpeedFast_v5` como proyecto.

6. Verifica que el código fuente se encuentre dentro de la carpeta `src`.

7. Confirma que dentro de `src` se encuentren los paquetes:

```text
app
hilos
model
```

8. Abre la clase principal:

```text
src/app/Main.java
```

9. Ejecuta el método `main()`.

---

## 🖥️ Ejemplo de funcionamiento

```text
=== SISTEMA SPEEDFAST - SEMANA 5 ===

[Zona de carga inicializada]

Pedido #1 agregado. Destino: Santiago Centro
Pedido #2 agregado. Destino: Providencia
Pedido #3 agregado. Destino: Ñuñoa
Pedido #4 agregado. Destino: Recoleta
Pedido #5 agregado. Destino: Las Condes

=== INICIO DE ENTREGAS ===

[Repartidor - Juan] Retirando pedido #1...
[Repartidor - Camila] Retirando pedido #2...
[Repartidor - Pedro] Retirando pedido #3...
[Repartidor - Pedro] Estado: EN_REPARTO
[Repartidor - Pedro] Entregando pedido #3...
[Repartidor - Camila] Estado: EN_REPARTO
[Repartidor - Juan] Estado: EN_REPARTO
[Repartidor - Juan] Entregando pedido #1...
[Repartidor - Camila] Entregando pedido #2...
[Repartidor - Pedro] Estado: ENTREGADO
[Repartidor - Pedro] Retirando pedido #4...
[Repartidor - Pedro] Estado: EN_REPARTO
[Repartidor - Pedro] Entregando pedido #4...
[Repartidor - Camila] Estado: ENTREGADO
[Repartidor - Juan] Estado: ENTREGADO
[Repartidor - Camila] Retirando pedido #5...
[Repartidor - Camila] Estado: EN_REPARTO
[Repartidor - Camila] Entregando pedido #5...
[Repartidor - Juan] Finalizó sus entregas.
[Repartidor - Pedro] Estado: ENTREGADO
[Repartidor - Pedro] Finalizó sus entregas.
[Repartidor - Camila] Estado: ENTREGADO
[Repartidor - Camila] Finalizó sus entregas.

Todos los pedidos han sido entregados correctamente
```

El orden de ejecución de los repartidores puede variar, ya que los tres trabajan de manera concurrente.

---

**Repositorio GitHub:** https://github.com/mauvalenzuelaf-oss/SistemaSpeedFast_v5

**Fecha:** 14/09/2026
