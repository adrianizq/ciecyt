# Auditoria UX por rol · CIECYT-ITP frontend

Estado evaluado al cierre de los sprints 1-5 + Acuerdo 25. Recorrido por rol:
que ve el usuario al entrar, a donde llega, y donde la experiencia se rompe.

## Resumen ejecutivo

| Rol del Acuerdo | Rutas con acceso (router) | Que muestra el home al entrar | Coherencia |
|---|---:|---|---|
| ROLE_ESTUDIANTE | 11 | Tarjetas de modalidad | OK en intención, no en detalle |
| ROLE_ASESOR | 6 | Tarjetas de modalidad **+ su lista** | Roto: clic en tarjeta va a ruta de estudiante |
| ROLE_JURADO | 5 | Tarjetas de modalidad **+ lista de jurado** | Roto: clic en tarjeta va a ruta de estudiante |
| ROLE_CIECYT | 3 | Tarjetas de modalidad **+ su lista-ciecyt** | Roto: clic en tarjeta va a ruta de estudiante |
| ROLE_DECANO | 2 | Tarjetas de modalidad sin destino | Roto: clic va a `/forbidden` |
| ROLE_ADMIN | 31 (admin + academia) | Tarjetas de modalidad **+ pestañas administrativas JHipster** | Ruido: admin ve pestañas que no le atañen |

El problema de fondo: el componente `core/home/home.component.ts` muestra **siempre** las siete tarjetas de modalidad y luego decide la ruta en función de roles, pero ese switch cubre solamente 4 casos (estudiante, asesor, ciecyt, admin) **y trata a ROLE_JURADO como si fuera estudiante** (linea 66: `includes('ROLE_JURADO') || includes('ROLE_ESTUDIANTE')` -> la ruta de la tarjeta).

## 1. Que ve cada usuario en la barra superior (navbar)

El navbar superior consume `MenuService.allRoles(roles)` (backend, tabla `menu`) — son pestañas como Inicio, Administracion, Configuracion, Entitys, etc. Es un esquema generado por JHipster:

- Para **ROLE_ESTUDIANTE / ROLE_ASESOR / ROLE_JURADO / ROLE_CIECYT**: lo que ve esta heredado del backend y tipicamente es la barra JHipster por defecto ("Inicio · Entidades · Admin · Ajustes"). **Raro**: las entidades son 108 paginas CRUD que son utiles para admin pero irrelevantes para un estudiante o un decano. Las ven igualmente.
- Para **ROLE_DECANO**: probablemente enlaces a sus paginas especificas si el backend asi lo configuro. Si no, el navbar cae al default.
- Para **ROLE_ADMIN**: ademas de Inicio ve "Administracion" con submenu (User management, Metrics, Health, Logs, Audits, Configuration). **Confuso** porque un admin puro no tiene por que conocer las pestañas de gestion academica, y viceversa.

## 2. El home segun rol

`pages/estudiante/listado_estudiante.vue:1-50` y `core/home/home.component.ts:10-88`.

### 2.1. Estudiante (ROLE_ESTUDIANTE)

- Ve tarjetas con 7 modalidades (Tesis, Pasantía Tecnológica, Pasantía Investigativa, Pasantía Internacional, Publicación Artículo, Diplomado, Especialización). Al clic va a `/estudiante/listado-estudiante`.
- Esa pagina muestra "Mis propuestas" — sus propuestas según backend.
- Desde alli, si el proyecto esta `EN_ELABORACION_PROPUESTA`, lo va a `PropuestaEnviarPropuestaView` (estado /propuesta/enviar_propuesta.vue).
- **Pero** los wizards de propuesta (Tesis, Pasantía, Diplomado) viven en `/propuesta`, `/propuesta-pasantia`, `/propuesta-diplomado`, `/proyectos` — **accesibles solo con `ROLE_USER`, no con `ROLE_ESTUDIANTE`**. Es decir, el estudiante sin ROLE_USER queda bloqueado en cualquier wizard.
- **Tan pronto el estudiante hace clic en una tarjeta**, va a su lista; el sidebar que aparece a la izquierda es `menu-lateral_nueva` con 5 pasos: Propuesta Nueva · Integrantes · Asesor · Jurado · Inscripción. Es el wizard que corresponde al flujo "propuesta nueva" (no al wizard de la modalidad concreta que el estudiante acaba de elegir).

### 2.2. Asesor (ROLE_ASESOR)

- Ve **las mismas siete tarjetas** que el estudiante.
- El switch en `home.component.ts:66` lo manda a `/viabilidad-propuesta/listado-asesor`. Pero las tarjetas **no llaman a la funcion de switch**, llaman a `modalidad.ruta`, que es `/estudiante/listado-estudiante`.
- Si el asesor hace clic en "Tesis" / "Pasantía" / etc., va a una ruta (`/estudiante/listado-estudiante`) con `meta.authorities: ['ROLE_ESTUDIANTE','ROLE_ADMIN']`. Como asesor no es ninguno, el router del frontend lo redirige a `/forbidden`.
- La pagina donde **sí** puede trabajar (`/viabilidad-propuesta/listado-asesor`) no aparece en el home — no hay forma visible de llegar a su lista.

### 2.3. Jurado (ROLE_JURADO)

- Ve las mismas 7 tarjetas.
- `home.component.ts:66` otra vez iguala JURADO a ESTUDIANTE: `if (this.autoridades.includes('ROLE_ESTUDIANTE') || this.autoridades.includes('ROLE_JURADO')) -> push modalidad.ruta`. Por tanto **mismo problema**: la unica via de acceso a su flujo real es adivinar la URL directa a `/viabilidad-propuesta/listado-jurado`.

### 2.4. CIECYT (ROLE_CIECYT)

- Ve las 7 tarjetas.
- Su switch (`/ciecyt/listado-ciecyt`) tambien requiere hardcodear el clic en una tarjeta: no hay enlace a `/ciecyt/asignar-jurado` ni a `/ciecyt/asignar-asesor` desde el home.

### 2.5. Decano (ROLE_DECANO)

- Ve las 7 tarjetas.
- **Ninguna de las rutas destino esta autorizada para el decano**. Cada clic lleva a `/forbidden`. El decano entra al sistema y **no tiene acceso a nada** desde la home — solo puede adivinar la URL `/decanura/padron-habilitados` o `/decanura/remision-padron`.
- **Esto es la brecha mas grave**: acorde al Acuerdo el decano tiene funciones importantes (art. 8 par. 1 y 2, art. 9 par. 2 — padron de habilitados y remision), y el frontend las esconde.

### 2.6. Administrador (ROLE_ADMIN)

- Ve tarjetas y tambien pestañas administrativas del navbar (Audits, Configuration, Health, Logs, Metrics, User management).
- Si hace clic en una tarjeta del home, va a `/estudiante/listado-estudiante` (permitido para ROLE_ADMIN).
- **Inversion de responsabilidades**: un admin del sistema termina mirando listas de propuestas como si fuera un estudiante. Las pestañas administrativas (Audits, Health) son utiles pero las tarjetas Academicas no son su tarea. Carece de una vista "admin del sistema" distinta y "admin academico" si quiere ambas.

## 3. Rutas wizard por modalidad

- `/propuesta/**` (22 rutas): wizard de tesis. Guard `['ROLE_USER']`. Accesible para cualquier usuario autenticado que tenga `ROLE_USER` por defecto (lo cual aplica a todos los roles generados por JHipster).
- `/propuesta-pasantia/**` (11 rutas): wizard de pasantia. Mismo guard.
- `/propuesta-diplomado/**` (9 rutas): wizard de diplomado. Mismo guard.
- `/proyectos/**` (6 rutas): proyecto (etapa final). Mismo guard.

**Conclusion**: los wizards tienen un guard generico que ignora completamente el rol. La `propuesta-nueva/**` (7 rutas) **sí** esta marcada con `['ROLE_USER','ROLE_ESTUDIANTE']`, asi que al menos bloquea admin puro. Los demas (los tres wizards grandes y el de proyecto) admiten a cualquier usuario autenticado, incluyendo jurado/decano/ciecyt si pudiera entrar la URL.

## 4. Rutas del Acuerdo 25 que existen en el router

| Acuerdo 25 | Ruta | Autoridades |
|---|---|---|
| Evaluar propuestas (jurado) | `/viabilidad-propuesta/propuesta-evaluar/:id` | ROLE_JURADO/ADMIN ✓ |
| Evaluar proyectos (jurado) | `/viabilidad-propuesta/proyecto-evaluar/:id` | ROLE_JURADO/ADMIN ✓ |
| Evaluar sustentacion de proyectos (jurado) | `/viabilidad-propuesta/proyecto-evaluar-sustentacion/:id` | ROLE_JURADO/ASESOR/ADMIN ✓ |
| Evaluar sustentacion (jurado) | `/viabilidad-propuesta/listado-sustentacion` | ROLE_JURADO/ASESOR/ADMIN ✓ |
| Evaluar asesoria (asesor) | `/viabilidad-propuesta/asesoria-evaluar/:id` | ROLE_ASESOR/ADMIN ✓ |
| Evaluar asesoria proyecto (asesor) | `/viabilidad-propuesta/asesoria-evaluar-proyecto/:id` | ROLE_ASESOR/ADMIN ✓ |
| Designar jurado por CIECYT | `/ciecyt/asignar-jurado/:id` | ROLE_ADMIN/ROLE_CIECYT ✓ |
| Designar asesor por CIECYT | `/ciecyt/asignar-asesor/:id` | ROLE_ADMIN/ROLE_CIECYT ✓ |
| Listado general CIECYT | `/ciecyt/listado-ciecyt` | ROLE_ADMIN/ROLE_CIECYT ✓ |
| Padron de docentes habilitados (decano) | `/decanura/padron-habilitados` | ROLE_DECANO/ADMIN ✓ |
| Remision del padron (decano) | `/decanura/remision-padron` | ROLE_DECANO/ADMIN ✓ |
| Asignar asesores externos | NO EXISTE ruta — solo via API ✗ |
| Verificar idoneidad de externos | NO EXISTE ruta — solo via API ✗ |
| Aval de continuidad | NO EXISTE ✗ |
| Plazos automaticos | NO EXISTE ✗ |
| Impedimentos (consanguinidad) | NO EXISTE ✗ |
| Novedades sustanciales | NO EXISTE ✗ |

## 5. Resumen de las brechas detectadas (priorizadas)

| # | Brecha | Severidad | Quien la sufre |
|---|---|---|---|
| B1 | Decano entra y todo le manda a `/forbidden` | Alta | Decano |
| B2 | Asesor / Jurado / CIECYT ven tarjetas que llevan a `/forbidden` | Alta | Asesor, Jurado, CIECYT |
| B3 | Wizards de Tesis/Pasantia/Diplomado/Proyecto accesibles con ROLE_USER (deberian ser ROLE_ESTUDIANTE) | Media | Estudiantes sin ROLE_USER extra |
| B4 | El home trata ROLE_JURADO igual a ROLE_ESTUDIANTE (clic lleva a listado de estudiante) | Alta | Jurado |
| B5 | Las pestañas admin (Audits, Config, Health) aparecen para **todos** los roles | Media | Usuarios no-admin |
| B6 | No hay entrada "mis propuestas como CIECYT" / "mis asesorados" / etc. desde el home | Alta | Roles academicos no estudiante |
| B7 | El menu superior usa el sistema JHipster de menús pero algunos roles no tienen menu específico definido | Media | Decano |
| B8 | El listado de estudiante muestra propuestas de **todos** los usuarios (no solo del estudiante actual) si las backend devuelven todos | Alta | Estudiantes |
| B9 | El wizard de propuesta nueva (`/propuesta-nueva/**`) termina en `PropuestaAsesorNuevaEditView` que va al listado-estudiante, pero el estudiante termina ciego sin saber si el siguiente paso es correcto | Media | Estudiantes |
| B10 | El dashboard de admin muestra tarjetas de modalidades cuando un admin no se dedica a proponer tesis | Media | Admin |

## 6. Recomendaciones operativas (ordenadas por valor / costo)

### P0 (imprescindibles, en orden)
1. **Reescribir el switch del home** para que cada rol aterrice en su pantalla natural. Las 7 tarjetas de modalidad solo se muestran a `ROLE_ESTUDIANTE`. Los demas roles ven su propio panel.
2. **Anadir un subpanel contextual por rol** en el home:
   - Asesor: lista de propuestas a evaluar como Asesor (link a `/viabilidad-propuesta/listado-asesor`).
   - Jurado: lista de propuestas y proyectos a evaluar (link a `/viabilidad-propuesta/listado-jurado`).
   - CIECYT: tableros con `asignar-jurado` / `asignar-asesor` y listado general.
   - Decano: acceso directo al `padron de habilitados` y `remision del padron` (las dos paginas que ya existen).
   - Admin: combinacion de metricas del sistema y opciones academicas si es tambien admin academico.
3. **Arreglar el guard de los wizards `propuesta`, `propuesta-pasantia`, `propuesta-diplomado`, `proyectos`** para que sean explicitamente `['ROLE_USER', 'ROLE_ESTUDIANTE']` (no ROLE_USER solo). Esto evita que el CIECYT / Decano / Asesor / Jurado navegue estas URLs por accidente.

### P1 (importantes)
4. **Anadir breadcrumbs / breadcrumb-component** al header-tabs del app para que el usuario sepa donde esta (especialmente dentro de un wizard).
5. **Diferenciar las pestanas del navbar** segun rol: para roles academicos no-estudiante, ocultar "Administracion" y "Configuracion" (JHipster admin tabs).
6. **Verificar la consulta `listado-estudiante`**: que filtra por usuario autenticado (`user.id`) y no devuelve todo el sistema. Hoy parece asumirlo.

### P2 (nice-to-have)
7. **Dashboard de metricas academicas por rol** ("X propuestas pendientes", "Y sustentaciones programadas esta semana").
8. **Anadir una pagina de "inbox" / notificaciones** para que un asesor vea retroalimentaciones recientes o un jurado vea lo que tiene pendiente.
9. **Mejorar el menu superior** usando datos que ya tiene `shared/config/config.ts` (los items de menu_lateral* y menu_lateral_ciecyt) — dejarlos disponibles por rol y consumirlos desde el componente navbar.

## 7. Que NO tocar (cuesta mas de lo que aporta)

- Reescribir `entities/*` (108 rutas CRUD generadas por JHipster): necesarias para admin de base de datos pero no aportan al proceso academico del Acuerdo. Mantener como esta hasta que el sistema deje JHipster.
- Unificar los wizards duplicados (3 wizards casi identicos por modalidad). Es trabajo grande entre sprints anteriores; ya hay un composable compartido y hay planes para extraer. **No es ruptura funcional** — solo es reforma interna.
- Reorganizar los laterales: ya refactorizamos durante los sprints UX. Concentricidad no es problema urgente.

## 8. Recomendacion al equipo

Antes de que un estudiante, decano o jurado entre a produccion al sistema, hay que:

1. Bloquear las URLs de wizard con `['ROLE_USER', 'ROLE_ESTUDIANTE']`.
2. Reemplazar el home para que cada rol llegue a su panel.
3. Probar manualmente cada rol con un F5 desde deep-links.

Esas brechas son mas urgentes que el resto de las R9, R11, R12 del Acuerdo. Si quieres concuerdo con el plan y me dices cual P0/P1 arrancar primero, lo hago en este orden:

- **P0 #1 primero** (home por rol) — bajo riesgo, alto valor visible, ~30 min.
- **P0 #3 segundo** (guards por rol en wizards) — ~15 min, 4 archivos.
- **P0 #4 segundo** (CIECYT home con asignaciones) — ~20 min.
- **P1 #3** (filtro listado-estudiante por usuario) — depende del backend; alta complejidad.

> Mientras tanto, los home actuales **no rompen permisos** (los guards detienen al rol equivocado), pero **confunde a los usuarios** que terminan adivinando URLs. Por eso esta auditoria importa mas de lo que parece: la primera impresion del usuario define si adopta el sistema o abandona.
