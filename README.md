# Analex1-2026

Calculadora de expresiones aritméticas implementada con un analizador léxico (`Analex`) basado en un autómata determinista y un parser descendente.

El proyecto permite:
- Evaluar expresiones con `+`, `-`, `*`, `/` y `%`
- Usar las palabras reservadas `div` y `mod`
- Trabajar con números enteros y reales
- Usar paréntesis
- Manejar variables/identificadores
- Asignar valores con `->`
- Ver la tabla de símbolos (`TSVAR`) en la interfaz

## Estructura

- `src/Lexer/Analex.java`: analizador léxico
- `src/Lexer/Parser.java`: parser y evaluación de expresiones
- `src/Lexer/Cinta.java`: cinta de entrada
- `src/Lexer/Token.java`: definición de tokens
- `src/Lexer/TSVAR.java`: tabla de símbolos / memoria de variables
- `src/UI/Calculadora.java`: interfaz gráfica principal

## Requisitos

- Java instalado
- Un entorno compatible con proyectos Ant / NetBeans

## Ejecución

### Desde NetBeans

Ejecuta el proyecto directamente.  
El punto de entrada configurado es `UI.Calculadora`.

### Desde consola

Si compilas manualmente, puedes ejecutar la interfaz principal con:

```bash
java UI.Calculadora
```

Si el proyecto ya fue compilado y tienes las clases en un directorio de salida, ajusta el classpath según corresponda.

## Sintaxis soportada

### Operadores

- Suma: `+`
- Resta: `-`
- Multiplicación: `*`
- División: `/` o `div`
- Módulo: `%` o `mod`

### Agrupación

- Paréntesis: `( ... )`

### Números

- Enteros: `123`
- Reales: `123.45`, `123.`, `.45`

### Variables

- Identificadores alfanuméricos
- No distingue mayúsculas/minúsculas para nombres de variables

### Asignación

La forma general es:

```text
expresion -> variable
```

Ejemplos:

```text
15 -> x1
60.45 -> Base
(Base - (200 mod 12) * -10.84) / x1 -> a1BC
```

## Ejemplos

```text
10 + 4 * 3 mod 5
```

```text
(5 + 3) * 2
```

```text
Base + x1
```

## Interfaz

La ventana `Calculadora` incluye:
- Un área para escribir la expresión
- Un área para mostrar el resultado
- Botones para insertar símbolos y números
- Un panel `TSVAR` que muestra las variables registradas como tabla

## Notas

- Si una expresión deja tokens pendientes, se mostrará un error.
- La tabla `TSVAR` se actualiza después de cada cálculo.
- La variable o identificador usado en una asignación queda almacenado en memoria para cálculos posteriores.

## Autoría

Proyecto académico de calculadora con analizador léxico y parser.
