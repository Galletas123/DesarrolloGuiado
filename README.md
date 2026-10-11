# AppDummy v1 — G-APPDUMMY-V1
Álvaro González · 10832770

## Qué hace
AppDummy es una biblioteca digital hecha con Jetpack Compose y Material 3.
Empieza con una pantalla de bienvenida que pide un nombre de al menos 3 caracteres. Después muestra una rejilla de libros que se puede buscar por título y filtrar por autor. Cada libro se puede marcar como favorito o leído, y también se puede compartir con el selector del sistema. Además incluye una pantalla que gestiona el permiso de la cámara y muestra una interfaz distinta según su estado.

## Estructura
- "MainActivity.kt": Inicio de la aplicación. Decide qué pantalla se muestra según la variable "mostrarListado".
- "ui/screens/PantallaBienvenida.kt": Campo de nombre con validación y botón Entrar.
- "ui/screens/PantallaListado.kt": Buscador, filtros por autor, rejilla de libros y el composable "ItemLibro" (tarjeta con portada, favorito, leído y compartir).
- "ui/screens/PantallaGestionPermisos.kt": Solicitud del permiso de cámara y el composable "ColumnaEstado", que dibuja cada estado.
- "ui/models/LibroUI.kt": Clase de datos del libro y lista "libros" con los ejemplos.
- "ui/permissions/EstadoPermiso.kt": Clase sellada con los estados del permiso de cámara.
- "res/drawable/": Portadas de los libros

## Dependencias añadidas
| Librería |
| Coil 3 ("coil-compose") | Cargar las portadas con "AsyncImage" |
| Material Icons Extended | Iconos como "Book" y "CameraAlt" |

## Permisos declarados
| Permiso |
| "CAMERA" | Lo solicita la pantalla de gestión de permisos para demostrar el flujo de concesión y denegación |

## Decisiones propias
1. Botón Entrar sin navegación. "MainActivity" guarda "mostrarListado" con "rememberSaveable". Al pulsar Entrar pasa a "true" y se muestra la pantalla que esté sin comentar (ahora "PantallaListado()" y, cambiando la línea, "PantallaGestionPermisos()"). Es una solución simple que no necesita librería de navegación y mantiene el estado al rotar.
2. "LibroUI" y "EstadoPermiso" fuera de las pantallas. Cada una tiene su propio fichero ("models" y "permissions"). Así las pantallas solo se encargan de dibujar, y el modelo y los estados se pueden reutilizar o probar por separado.
3. Composable "ColumnaEstado" reutilizable. En vez de repetir la interfaz en cada rama del "when", un único composable recibe icono, título, descripción y botón opcional. Cada estado solo cambia sus parámetros.

## Limitaciones conocidas
- No se puede navegar entre pantallas. Para ver otra pantalla hay que cambiar a mano la línea sin comentar en "MainActivity". La navegación se añadirá más adelante.
- Al rotar el filtro de autor y los favoritos o leídos se pierden, porque en "PantallaListado" usan "remember" y no "rememberSaveable". Solo el nombre de la bienvenida se conserva.
- Los favoritos y leídos no se guardan de forma permanente: al cerrar la app vuelven al estado inicial.
