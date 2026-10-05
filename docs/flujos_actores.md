# Flujos de actores · CIECYT-ITP

Vista unificada por rol de las pantallas que verá cada actor cuando
inicie sesión. Cada paso es una ruta real (`vue-router`) o un endpoint
backend que la implementación actual expone. Las rutas con meta
`authorities: [ROLE_USER, ROLE_ESTUDIANTE]` (P0 #3) ya solo aceptan
estudiantes en los wizards; cualquier otro rol recibe 403 y es
redirigido al Home.

Convenciones:

- `[E]` = Estudiante · `[D]` = Decano · `[C]` = CIECYT · `[A]` = Asesor
- `[J]` = Jurado · `[U]` = Admin · `[*]` = todos los autenticados
- Los pasos vienen de `vue-router` con sus `name` exactos.

---

## 0. Landing (`/`, HomeComponent) — Acuerdo 25 art. 5

```
[* /account/login  ─►  Vuex  authenticated=true  ─►  / ]
   │
   ▼  dispatch('account/authorities')  →  this.$store.getters.account.authorities
   │
   ├──► esEstudiante → "Mis propuestas"
   ├──► esDecano     → Decanatura (padrón + remisión)
   ├──► esAsesor     → Asesoría (propuestas / proyectos / sustentaciones)
   ├──► esJurado     → Jurado (propuestas + sustentaciones)
   ├──► esCiecyt     → CIECYT (3 tarjetas)
   ├──► esAdmin      → Administración (3 tarjetas)
   └──► sin rol      → "Cuenta sin rol" (alerta neutra)

No autenticado  → alert-warning con enlace a login modal (HomeComponent:479)
```

---

## 1. [E] Estudiante — entradas desde el Home

### 1.1 Flow de creación de propuesta

```
Home ─► "Nueva propuesta" (PropuestasInvestigadorEditView)
        │  POST /api/proyectos  (estado = BORRADOR)
        │
        ▼ navegacion wizard segun modalidad (ver § 6)
```

### 1.2 Flow de consulta / continuación

```
Home ─► "Mis propuestas"             (ListadoProyectoView)
        │  GET /api/proyectos?proponenteId=yo
        │
        ▼  click en una propuesta  →  redirige a su paso actual
                                     (pageStep por estado)
        │  - SIN_INICIAR    → informacion_general_*
        │  - EN_REVISION    → retroalimentacion
        │  - APROBADA       → adjuntar_propuesta_*
        └  - DESARROLLO     → cronograma_*
```

### 1.3 Botones "Nueva propuesta" + 7 modalidades

En `home.vue` los `<component :is="modalidad.componente">` resuelven cada
uno a la ruta del wizard correspondiente. La card "Nueva propuesta"
siempre lleva al selector de modalidad
(`/propuesta-nueva/propuestas-investigador`); las 7 cards de modalidad
hacen bypass del selector y abren el `informacion-general-...` directo:

| Modalidad      | id | ruta del wizard                                  |
| -------------- | -- | ------------------------------------------------ |
| Tesis          | 9004 | `/propuesta/:proyectoId/informacion-general`    |
| Pasantía Tec.  | 9001 | `/propuesta-pasantia/:proyectoId/informacion-general-pasantia` |
| Pasantía Inv.  | 9002 | idem pasantia                                    |
| Pasantía Int.  | 9003 | idem pasantia                                    |
| Diplomado      | 9005 | `/propuesta-diplomado/:proyectoId/informacion-general-diplomado` |
| Publicación    | 9006 | `/propuesta-nueva/informacion-general-nueva/:proyectoId` |
| Especialización| 9007 | idem publicacion                                 |

---

## 2. [D] Decano — padrón y remisión (cap V Acuerdo 25 art. 8-9)

```
Home ─► "Padrón de docentes habilitados" (PadronHabilitados)
        │  GET /api/padrones-habilitados?facultadId=...
        │
        ▼  Buscar docente  →  activar/desactivar (rol + vigencia)
           /api/padrones-habilitados/{id}  (PUT)
        │  Modal "Historial"  → GET /api/padrones/historial/{docenteId}
        │
        ▼  "Remisión del padrón a CIECYT" (RemisionPadron)
           POST /api/remisiones-padron  (BORRADOR)
           snapshot server-side de los VIGENTES
           POST /api/remisiones-padron/{id}/enviar
           → estado ENVIADO, inmutable
           (regla: única ENVIADA por (facultad, periodo))
```

El decano **no** ve el padrón ni remisiones de otras facultades (403).
El decano **sí** designa asesor en pasantía/diplomado (9001/9002/9003/9005)
pero **no** puede designar en tesis (lo hace CIECYT).

---

## 3. [C] CIECYT — gestor global + designaciones (art. 9-10)

```
Home ─► "Listado general de propuestas" (PropuestaListadoCiecytView)
        │  GET /api/proyectos  (todas las facultades)
        │
        ▼  columna Continuidad  → botón Renovar / Plazos Acuerdo 25
                                  (shared/utils/continuidad-proyecto.ts)
                                  APLAZADO bloquea renovacion (R3)
        │
        ▼  "Asignar jurado" (AsignarJuradoView + AsignarIntegrantes.vue role='jurado')
           sólo el padrón HABILITADO/remitido de la facultad del proyecto
           marcador "(no vigente — ya designado)" si titular fue deshabilitado
           + checkbox "profesional externo" si CIECYT lo registró antes
           POST /api/proyectos/{id}/integrantes (rolModalidad = JURADO)
        │
        ▼  "Asignar asesor" (AsignarAsesorView + AsignarIntegrantes.vue role='asesor')
           Pádel idem, puede hacerlo en tesis (9004) y pasantía/diplomado.
```

Profesional externo (`/api/asesores-externos*`) — registrar/verificar
solo CIECYT o ADMIN (un decano recibe 403). El CIECYT lo marca como
verificado (idoneidad, fecha y login) y desde ahí aparece como
disponible en `asignar_asesor.vue`.

---

## 4. [A] Asesor — conceptuar y revisar

```
Home ─► "Propuestas a evaluar"     (PropuestaListadoAsesorView)
        │  GET /api/proyectos?asesorId=yo
        │
        ▼  click → PropuestaEvaluarView
           /viabilidad-propuesta/propuesta-evaluar/:proyectoId
           AsesorConcepto (Viable / Modificaciones / Rechazado)  (R2)
                                 ───────────────────────────────►
                                                               VIABILIDAD_VALOR_VIABLE_MODIFICACIONES
                                                               alias LEGACY 'PENDIENTE' para compat
                                                                       │
        ▼ "Proyectos a evaluar"  (ProyectoListadoAsesorView)
           GET /api/proyectos?asesorId=yo&estado=DESARROLLO
           click → ProyectoEvaluarView
                   /viabilidad-propuesta/proyecto-evaluar/:proyectoId
                   /viabilidad-propuesta/asesoria-evaluar/:proyectoId
                   /viabilidad-propuesta/asesoria-evaluar-proyecto/:proyectoId
        │
        ▼ "Sustentaciones"  (PropuestaListadoSustentacionView)
           Programacion agenda y registro de evaluacion final  (art. 14)
```

---

## 5. [J] Jurado — viabilidad + sustentación (art. 9-10, 14)

```
Home ─► "Propuestas a evaluar" (PropuestaListadoJuradoView)
        │  GET /api/proyectos?juradoId=yo
        ▼  click → propuesta-evaluar
           - Evalua con la rubrica institucional
           - Emite concepto (idem Asesor)
        ▼ "Sustentaciones"   (PropuestaListadoSustentacionView)
           - Programado  (fecha, hora, lugar)
           - En sesion   (rubro, observaciones, acta)
           - Cerrado     (registro de resultado)
```

Jurado solo opera donde fue **designado formalmente** por CIECYT. El
estudiante no elige jurado desde el wizard (step 3 de tesis dice
"la designacion la hace CIECYT" y no guarda nada).

---

## 6. [E] Estudiante — wizards por modalidad

### 6.1 Tesis (id 9004) — wizard 8 pasos

`/propuesta/...`

```
1. informacion-general/:id      → facultad, programa, titulo, ciclo
2. integrantes/:id               → autores (IntegranteProyecto: rol AUTOR)
3. cronograma/:id                → fases + fechas (R6 plazos Acuerdo 25)
4. estado_propuestas/:id         → selector estado (EN_REVISION, APROBADA, ...)
5. entidades/:id                 → entidades externas aliadas
6. resultados_esperados/:id      → impactos
7. presupuesto/:id               → rubros
8. impactos_esperados/:id        → impacto social / academico

adicionales (no siempre, scheduler condicional por estado):
- checklist/:id                  → Acuerdo 25 art. 4 Tabla 1 (R8)
- documento/{certificado, recibo-pago, record-academico, formato-inscripcion}/:id
                                  → guardado en /record_academico/ (gitignored)
- retroalimentacion/:id          → ver correcciones del asesor (ciclo revision)
- retroalimentacion-viabilidad/:id
                                  → ver concepto del jurado (Viable/Modificaciones/Rechazado)
- adjuntar_propuesta/:id         → subir PDF firmado (R10 si está aprobado, no APLAZADO)
- enviar_propuesta/:id           → POST /api/proyectos/{id}/enviar  → EN_REVISION
```

### 6.2 Pasantía (id 9001 / 9002 / 9003) — wizard 9 pasos

`/propuesta-pasantia/...`

```
1. informacion-general-pasantia/:id   → facultad, programa, ciclo (MODALIDAD 9001|9002|9003)
2. integrantes-pasantia/:id           → autores
3. informacion-empresa/:id            → datos de la empresa/entidad anfitriona
4. elementos-pasantia/:id             → objetivos, alcance
5. cronograma-pasantia/:id            → fases (R6)
6. retroalimentacion-pasantia/:id     → ciclo de revision con el tutor externo
7. retroalimentacion/:id              → correcciones internas (asesor)
8. retroalimentacion-viabilidad/:id   → concepto del jurado
9. adjuntar-propuesta-pasantia/:id
10. enviar-propuesta-pasantia/:id     → POST envio
```

**Diferencia con tesis**: el step de "elegir asesor" en la UI no guarda
Nada (la designacion la hace la Decanatura via padrón habilitado).

### 6.3 Diplomado (id 9005) — wizard 8 pasos

`/propuesta-diplomado/...`

```
1. informacion-general-diplomado/:id  → facultad, programa, ciclo
2. integrantes-diplomado/:id          → autores
3. elementos-diplomado/:id            → temas cubiertos
4. cronograma-diplomado/:id           → fases (R6)
5. retroalimentacion/:id              → correcciones asesor
6. retroalimentacion-viabilidad/:id
7. adjuntar-propuesta-diplomado/:id
8. enviar-propuesta-diplomado/:id     → POST envio
```

### 6.4 Publicación de Artículo (id 9006) / Especialización (id 9007)

`/propuesta-nueva/...`  (wizard nuevo compartido `informacion-general-nueva.vue`)

```
1. propuestas-investigador          → listado vivo del investigador
2. informacion-general-nueva/:id    → facultad, programa, titulo del articulo
3. integrantes-nueva/:id            → autores + categoria revista (A1/A2/B/C, R5)
4. asesor-nueva/:id                 → designar asesor del padron de la facultad
5. jurado-nueva/:id                 → "la designacion la hace CIECYT" (no guarda)
6. inscripcion-nueva/:id            → rubricar y enviar
```

### 6.5 Reglas transversales aplicables a los 4 wizards

- Todas las rutas tienen `meta: { authorities: ['ROLE_USER','ROLE_ESTUDIANTE'] }`
  (P0 #3 aplicado). Un Decano/Asesor/Jurado/CIECYT/Admin que entre directo
  a un step del wizard recibe 403 y es redirigido.
- Cada paso usa el `menu-lateral-wizard.vue` compartido (P0 / dedup-2)
  con `storeKey` por modalidad: `menuLateral`, `menuLateralPasantia`,
  `menuLateralDiplomado`, `menuLateralProyecto`. Los pasos previos quedan
  deshabilitados (`step-btn-disabled`) hasta que haya `:proyectoId` en la
  ruta.
- Los proyectos en estado `APLAZADO` (R3) bloquean el botón Renovar y
  muestran banner en `listado_ciecyt.vue` (R10).
- Los proyectos de Publicacion exigen al menos N integrantes segun
  `EnumCategoriaRevista` (R5):
    - A1, A2 → 1 integrante
    - B     → hasta 2
    - C     → hasta 3
  Validador en `pages/propuesta_nueva/integrantes_nueva.vue` (helper
  `integrantesPermitidosPorCategoriaRevista()`).
- `viabilidad.ts` cambia `VIABILIDAD_VALID..._LEGACY = 'PENDIENTE'` (R2),
  usado por informes viejos.

---

## 7. [U] Admin — soporte global

```
Home ─► "Listado académico"        (PropuestaListadoCiecytView)  - mismo que CIECYT
        └─ "Padrón de docentes"     (PadronHabilitados)         - sin filtro facultad
        └─ "Gestión de usuarios"    (/admin/user-management)    - CRUD JHipster

Puede habilitar/corregir padrones de CUALQUIER facultad (incluye ADMIN
en `puedeHabilitarDocentesEn`).
Puede ver remisiones globales de todas las facultades (GET 200).
```

---

## 8. Hermetismos ya fijados (backend, probados)

- `puedeHabilitarDocentesEn(facultad)` → admin o decano de la facultad.
- `puedeVerHabilitadosDe(facultad)` → CIECYT / decano de esa facultad / participante.
- `puedeDesignarIntegrantesDe(proyecto, rolModalidad)` → CIECYT todas; decano
  solo 9001/9002/9003/9005 con autoridad ASESOR/JURADO; participante
  solo tesis como ASESOR.
- `/users/asesores`|`/users/jurados` → solo CIECYT/ADMIN
- `/users/estudiantes` → abierto
- Una sola remisión ENVIADA por (facultad, periodo) — server-side immutable.
- Externo: registrar/verificar solo CIECYT/ADMIN; cambiar rol ASESOR↔JURADO
  resetea la verificación.

---

## 9. Estado del Acuerdo 25 con la implementación actual

| Id  | Tema                                | Implementado |
| --- | ----------------------------------- | ------------ |
| R1  | Especialización en dashboard        | ✅ dd8b064 (home.vue) |
| R2  | Cambio `VIABILIDAD_*`               | ✅ 368f0e8 |
| R3  | Estado `APLAZADO`                   | ✅ 368f0e8 |
| R4  | "Pasantía Investigativa Profesional"| ✅ 368f0e8 |
| R5  | Categoría revista A1/A2/B/C         | ✅ 368f0e8 |
| R6  | Plazos Acuerdo 25 (días hábiles)    | ✅ 368f0e8 |
| R7  | Impedimentos y novedades            | ⬜ pendiente |
| R8  | Matriz requisitos por modalidad     | ✅ 368f0e8 + 0aa6f30 |
| R9  | Conflictos de intereses             | ⬜ pendiente |
| R10 | Continuidad / APLAZADO              | ✅ 368f0e8 + 0aa6f30 |
| R11 | Acta de evaluacion / sustentación   | ⬜ pendiente (UI) |
| R12 | Repositorio / publicación           | ⬜ pendiente |
| P0#1 | Home por rol                        | ✅ dd8b064 |
| P0#3 | Guards de wizard para estudiante    | ✅ 0aa6f30 |

---

## 10. Pendientes que afectan los flujos

- Módulo "Impedimentos y novedades" — UI y modelos (R7).
- Validación de conflicto de intereses del jurado (R9).
- Acta de evaluación (R11) y repositorio final (R12).
- Páginas de tesis/propuesta `informacion_general_*.vue` aún duplicadas;
  dedup diferido (consolidar con `informacion-general-nueva.vue`).
- Sin `previousState` en `propuestas_nueva.vue` cuando cambia estado.

### 10.1 Bitácora de la sesión 2026-10-05 (consolidada)

1. **Reset admin + UI de credenciales** (commits `ae9e0e5`–`f293202`)
   - Backend: `POST /api/admin/users/{login}/reset-password` (bcrypt+force-change por defecto).
   - Frontend: botón **Resetear contraseña** en `/admin/user-management` con confirmReset + alertService verde/rojo.

2. **Cambio de canal olvidar contraseña** (commit `f293202`)
   - `/reset/request` y login form reescritos para guiar al operador al canal presencial/admin. Endpoint `/api/account/reset-password/init` se conserva como fallback.

3. **Asignación decano→facultad** (commits `d9141d7`, `df4136e`–`fc6896f`)
   - Backend: tabla `decano_facultad` (ya pre-existente). Frontend: `/admin/decano-facultad` con buscador de usuario, dropdown de facultad, dos modales (asignar / cerrar vigencia). 4 tests del guard y 2 IT backend cubren la asignación y el cierre.

4. **Home por rol + audit_ux** (commit `dd8b064` + auditoria-ux-por-rol.md)
   - Estudiante, Decano, CIECYT, Asesor, Jurado, Admin tienen sus 3/4 tarjetas con su propósito. "Sin rol" muestra alerta neutra.

5. **Wizards de estudiante** (commit `0aa6f30`)
   - 46 rutas de `/propuesta`, `/propuesta-pasantia`, `/propuesta-diplomado`, `/propuestas-investigador` cambiaron de `authorities: ['ROLE_USER']` a `['ROLE_USER','ROLE_ESTUDIANTE']`. Decano/Asesor/Jurado/CIECYT/Admin reciben 403 si entran directo.

6. **Forzar cambio de contraseña post-reset** (commits `ab6ff07`, `c4dbf78`, `c7ae622`)
   - Backend: `User.needsPasswordChange` (Liquibase changeset `20261005000001`) + creación admin / reset admin / reset por correo lo prenden, `change-password` y reset por correo confirmado lo bajan. Frontend: store getter `needsPasswordChange` + getter público en accountService + guard global en `main.ts router.beforeEach` que redirige a `/account/password` mientras esté prendido. 4 tests (frontend) + 5 IT backend verdes.

7. **Bugfixes UI puntuales** (commits `c7ae622`, `7bef839`, `d1fcf10`, `df4136e`, `fc6896f`)
   - `c7ae622`: tarjetas CIECYT → en cambio de `AsignarJuradoView` a `PropuestaListadoCiecytView` (sin `:proyectoId`).
   - `7bef839` + `d1fcf10`: decanura con admin sin facultad → dropdown de TODAS las facultades (`FacultyService.retrieve`).
   - `df4136e`: b-modal de historial solo se monta al abrir (`v-if="mostrarHistorial"`).
   - `fc6896f`: `AsignarDecanoDialog.form` inicializado como objeto (no null) para evitar `Cannot read properties of null (reading 'userLogin')`.

8. **CIECYT en modo lectura** (commit `f74ab91`)
   - `/decanura/padron-habilitados` y `/decanura/remision-padron` abiertos a CIECYT en modo solo-lectura, con banner info y acciones de edición deshabilitadas.

### 10.2 Cómo reproducir el ciclo una vez sembrada la BD

```
1. admin /admin/decano-facultad
   Asignar decano: 18128952 → FTICS (id 30001) → Aplicar

2. admin /admin/user-management/new
   crear login 1098765432 (nombre, rol ROLE_DOCENTE) → Save

3. admin /decanura/padron-habilitados       (role=ADMIN)
   dropdown: FTICS → Dar de alta login 1098765432 rol ASESOR → Habilitar
   dropdown: FTICS → rol JURADO → Habilitar

4. admin /decanura/remision-padron
   dropdown: FTICS, periodo 2026-2 → Armar borrador → Enviar

5. admin /admin/user-management/new     crear Ana Solano (ROLE_ESTUDIANTE)

6. resetearle contrasena a Ana → entra al wizard → tesis → Enviar

7. CIECYT (rosita) /ciecyt/listado-ciecyt
   click en la propuesta → columna Jurado: dropdown con 1098765432 → Aplicar
   columna Asesor: mismo dropdown

8. ana vuelve al Home → ve su propuesta con "Cambiar jurado".
```

### 10.3 Estado de la suite de tests al cierre

- 917/917 tests frontend verdes (vitest).
- 8 tests IT backend verdes (mvn test -Dskip.npm=true -Denforcer.skip=true).

HUD del cierre:

- Decano FTICS: 18128952 → puede remitir padrón y habilitar docentes.
- CIECYT: rosita → puede asignar jurado/asesor (y consultar padrón/remisión en modo lectura).
- Admin → única identidad que puede asignar decanos, crear usuarios, resetear contraseñas y operar todo el padrón.
