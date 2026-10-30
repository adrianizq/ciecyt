# Guion de prueba end-to-end — Modalidad Tesis (9004)

Documento verificado contra la base de datos local y contra la API en vivo del backend
(`http://localhost:8080`) con tokens reales de cada rol.

## 1. Arranque

Terminal 1 — backend:

```powershell
.\mvnw.cmd
```

Terminal 2 — frontend (Vite, puerto 9000):

```powershell
npm.cmd start
```

Notas de entorno:

- `npm.cmd` en lugar de `npm` (PowerShell bloquea `npm.ps1` por `ExecutionPolicy`).
- No hay `mvn` global: usar el wrapper `mvnw.cmd`.
- PostgreSQL local: servicio `postgresql-x64-17` en `5432`, base `ciecyt_db`.
- Si Spring Boot DevTools falla al reiniciar en Windows por el error
  `Existing directory 'C:\Users\1\AppData\Local\Temp\...' is not owned by ...`:
  `.\mvnw.cmd -Dspring-boot.run.jvmArguments=-Djava.io.tmpdir=E:\ciecyt_itp_dev\ciecyt\target\tmp`
- Si aparece `Port 8080 was already in use`, hay una instancia previa:
  `Get-NetTCPConnection -LocalPort 8080 -State Listen` y terminar ese PID.

## 2. Usuarios de prueba

| Rol | Usuario | Contraseña | Autoridades |
|---|---|---|---|
| Estudiante | `69008257` | `69008257` | `ROLE_ESTUDIANTE`, `ROLE_USER` |
| Estudiante | `1125183399` | `1125183399` | `ROLE_ESTUDIANTE`, `ROLE_USER` |
| Estudiante | `keliun` | `keliun` | `ROLE_ESTUDIANTE`, `ROLE_USER` |
| Asesor | `adrian` | `admin` | `ROLE_ASESOR`, `ROLE_JURADO` |
| Jurado | `juanfernandorevelo93` | `admin` | `ROLE_ASESOR`, `ROLE_JURADO` |
| CIECYT | `prueba_ciecyt` | `admin` | `ROLE_CIECYT`, `ROLE_USER` |
| CIECYT | `lorena` | `lorena` | `ROLE_CIECYT` |
| CIECYT | `marina` | `marina` | `ROLE_CIECYT` |
| Decano FTICS | `18128952` | `18128952` | `ROLE_ASESOR`, `ROLE_DECANO`, `ROLE_JURADO` |
| Administrador | `admin` | `admin` | `ROLE_ADMIN`, `ROLE_USER` |

Padrón de habilitados de la facultad `4753` (usado por CIECYT para designar):

| User id | Usuario | Habilitado como |
|---|---|---|
| `1951` | `adrian` | ASESOR |
| `53001` | `juanfernandorevelo93` | JURADO |
| `53454` | `18127437` | ASESOR |

Roles de la modalidad Tesis (`roles_modalidad`): `4451` Asesor (1), `4542` Jurado (1),
`9130` Estudiante (2), `9132` Co-asesor (1).

## 3. Proyecto de arranque

Creado y verificado en `PREINSCRITA`:

- `id=83942` — estudiante `69008257` — Facultad `4753` — Programa `29901` — Ciclo `2`

Alternativos en la BD: `83591` (`PREINSCRITA`), `83641` (`PREINSCRITA`, estudiante `keliun`),
`76891` (`EN_ELABORACION_PROPUESTA`, estudiante `1122724063`),
`76291` (`EN_REVISION_ASESOR`, estudiante `1083915046`),
`74792` (`SUSTENTACION_REALIZADA`).

## 4. Matriz de transiciones de Tesis (9004)

| # | Origen | Destino | Rol |
|---|---|---|---|
| 95200 | PREINSCRITA | EN_VALIDACION_DOCUMENTAL | ROLE_ESTUDIANTE |
| 95201 | EN_VALIDACION_DOCUMENTAL | OBSERVACIONES_DOCUMENTACION | ROLE_CIECYT |
| 95202 | EN_VALIDACION_DOCUMENTAL | HABILITADO | ROLE_CIECYT |
| 95203 | OBSERVACIONES_DOCUMENTACION | EN_VALIDACION_DOCUMENTAL | ROLE_ESTUDIANTE |
| 95204 | HABILITADO | EN_ELABORACION_PROPUESTA | ROLE_ESTUDIANTE |
| 95205 | HABILITADO | EN_REVISION_ASESOR | ROLE_ESTUDIANTE |
| 95206 | EN_ELABORACION_PROPUESTA | EN_VALIDACION_DOCUMENTAL | ROLE_ESTUDIANTE |
| 95207 | EN_REVISION_ASESOR | CORRECCIONES_ASESOR | ROLE_ASESOR |
| 95208 | EN_REVISION_ASESOR | APROBADA_POR_ASESOR | ROLE_ASESOR |
| 95209 | CORRECCIONES_ASESOR | EN_REVISION_ASESOR | ROLE_ESTUDIANTE |
| 95210 | APROBADA_POR_ASESOR | EN_REVISION_JURADO_PROPUESTA | `*` |
| 95211 | EN_REVISION_JURADO_PROPUESTA | CORRECCIONES_JURADO_PROPUESTA | ROLE_JURADO |
| 95212 | EN_REVISION_JURADO_PROPUESTA | NO_VIABLE | ROLE_JURADO |
| 95213 | EN_REVISION_JURADO_PROPUESTA | VIABLE | ROLE_JURADO |
| 95214 | CORRECCIONES_JURADO_PROPUESTA | EN_REVISION_JURADO_PROPUESTA | ROLE_ESTUDIANTE |
| 95215 | NO_VIABLE | CORRECCIONES_JURADO_PROPUESTA | ROLE_JURADO |
| 95216 | NO_VIABLE | FINALIZADO | ROLE_CIECYT |
| 95217 | VIABLE | EN_ELABORACION_PROYECTO | ROLE_ESTUDIANTE |
| 95218 | EN_ELABORACION_PROYECTO | EN_REVISION_JURADO_PROYECTO | ROLE_ESTUDIANTE |
| 95219 | EN_REVISION_JURADO_PROYECTO | CORRECCIONES_JURADO_PROYECTO | ROLE_JURADO |
| 95220 | EN_REVISION_JURADO_PROYECTO | LISTO_PARA_SUSTENTAR | ROLE_JURADO |
| 95221 | CORRECCIONES_JURADO_PROYECTO | EN_REVISION_JURADO_PROYECTO | ROLE_ESTUDIANTE |
| 95222 | LISTO_PARA_SUSTENTAR | SUSTENTACION_PROGRAMADA | ROLE_CIECYT |
| 95223 | SUSTENTACION_PROGRAMADA | SUSTENTACION_REALIZADA | ROLE_CIECYT |
| 95224 | SUSTENTACION_REALIZADA | EN_EVALUACION_SUSTENTACION | ROLE_JURADO |
| 95225 | EN_EVALUACION_SUSTENTACION | AJUSTES_SUSTENTACION | ROLE_JURADO |
| 95226 | EN_EVALUACION_SUSTENTACION | NOTA_DEFINITIVA | ROLE_JURADO |
| 95227 | AJUSTES_SUSTENTACION | EN_EVALUACION_SUSTENTACION | ROLE_ESTUDIANTE |
| 95228 | NOTA_DEFINITIVA | FINALIZADO | ROLE_CIECYT |
| 95229 | PREINSCRITA | EN_ELABORACION_PROPUESTA | ROLE_ESTUDIANTE |
| 95230 | EN_ELABORACION_PROPUESTA | EN_REVISION_ASESOR | ROLE_ESTUDIANTE |
| 95231 | EN_REVISION_ASESOR | EN_REVISION_JURADO_PROPUESTA | ROLE_ASESOR |

## 5. Guion de la ruta principal (por UI)

### Paso 1 — Estudiante crea la propuesta

- Ingresar como `69008257` / `69008257`.
- `Mis propuestas` → botón de nueva propuesta de Tesis.
- Wizard: `/propuesta/informacion-general` → `/propuesta/integrantes/:id` →
  `/propuesta/checklist/:id` → `/propuesta/adjuntar_propuesta/:id`.
- En Información General, elegir un asesor de la lista habilitada por la decanatura para la
  facultad del proyecto (`4753`). Se guarda como integrante con el cargo **Asesor**; el CIECYT
  hará la designación formal.
- En Integrantes: el creador queda fijo y puede agregar un segundo estudiante (Acuerdo 25, art. 7).
- Guardar. **El proyecto nace en `PREINSCRITA`.**

### Paso 2 — Estudiante radica la propuesta ante el CIECYT

`Mis propuestas` y `Mis proyectos` muestran **Radicar propuesta** en `PREINSCRITA`,
`EN_ELABORACION_PROPUESTA` y `OBSERVACIONES_DOCUMENTACION`; **Solicitar aval** en
`HABILITADO`; y **Reenviar al asesor** en `CORRECCIONES_ASESOR`. La pantalla
`/propuesta/enviar_propuesta/:id` decide la acción según el estado del proyecto:

- Borrador u observada → `POST /solicitar-validacion-documental`
  (`PREINSCRITA` 95200, `EN_ELABORACION_PROPUESTA` 95206 u `OBSERVACIONES_DOCUMENTACION`
  95203 → `EN_VALIDACION_DOCUMENTAL`, `ROLE_ESTUDIANTE`).
- `HABILITADO` → `EN_REVISION_ASESOR` (95205, `ROLE_ESTUDIANTE`), solicitud de aval.
- `CORRECCIONES_ASESOR` → `EN_REVISION_ASESOR` (95209, `ROLE_ESTUDIANTE`).

Equivalente por API (radicación):

```powershell
$t = (Invoke-RestMethod -Uri 'http://localhost:8080/api/authenticate' -Method POST `
  -ContentType 'application/json' -Body '{"username":"69008257","password":"69008257"}').id_token
$h = @{ Authorization = "Bearer $t" }
Invoke-RestMethod -Uri 'http://localhost:8080/api/proyectos/83942/solicitar-validacion-documental' `
  -Method POST -Headers $h
```

### Paso 3 — CIECYT valida documentación y habilita

- Ingresar como `prueba_ciecyt` / `admin`.
- `Espacio para revisar las propuestas del Ciecyt` → proyecto → **Requisitos**.
- Aprobar cada requisito obligatorio y, en el panel **Validación documental**, usar
  **Habilitar propuesta** (`{aprobado:true}` → `HABILITADO`) u **Observar documentación**
  (`{aprobado:false, observacion}` → `OBSERVACIONES_DOCUMENTACION`).
- Equivalente por API:

```powershell
$h = @{ Authorization = "Bearer <token CIECYT>" }
Invoke-RestMethod -Uri 'http://localhost:8080/api/proyectos/83942/validar-documentacion' `
  -Method POST -ContentType 'application/json' -Headers $h -Body '{"aprobado":true}'
```

- Con el proyecto en `HABILITADO`, el estudiante entra a **Solicitar aval** y pasa a
  `EN_REVISION_ASESOR`; en ese momento `enviado=true` y aparece **Asignar jurado**.

### Paso 4 — CIECYT confirma asesor y designa jurado

- Ingresar como `prueba_ciecyt` / `admin`.
- `Espacio para revisar las propuestas del Ciecyt` → proyecto → **Asignar asesor** y
  **Jurado de sustentación**.
- Si el estudiante ya eligió asesor en Información General, aparece listado; el CIECYT puede
  confirmarlo o cambiarlo. Solo ofrece el padrón de habilitados de la facultad del proyecto (`4753`).
- Equivalente por API:

```powershell
$h = @{ Authorization = "Bearer <token CIECYT>" }
Invoke-RestMethod -Uri 'http://localhost:8080/api/integrante-proyectos' -Method POST `
  -ContentType 'application/json' -Headers $h `
  -Body '{"integranteProyectoProyectoId":83942,"integranteProyectoUserId":1951,"integranteProyectoRolesModalidadId":4451}'
Invoke-RestMethod -Uri 'http://localhost:8080/api/integrante-proyectos' -Method POST `
  -ContentType 'application/json' -Headers $h `
  -Body '{"integranteProyectoProyectoId":83942,"integranteProyectoUserId":53001,"integranteProyectoRolesModalidadId":4542}'
```

### Paso 5 — Asesor evalúa la propuesta

- Ingresar como `adrian` / `admin`.
- `Espacio para revisar las propuestas a cargo` → el proyecto → **Evaluar**.
- Guardar borrador = `CORRECCIONES_ASESOR`; Enviar = `EN_REVISION_JURADO_PROPUESTA`
  (para Tesis, que tiene jurado).

### Paso 6 — Jurado evalúa la viabilidad

- Ingresar como `juanfernandorevelo93` / `admin`.
- `Evaluar la viabilidad de las propuestas y los proyectos` → **Viable** / **No viable** /
  **Pendente con correcciones** → `VIABLE` / `NO_VIABLE` / `CORRECCIONES_JURADO_PROPUESTA`.

### Paso 7 — Estudiante elabora el proyecto

- `Mis propuestas` → con `VIABLE` aparece **Enviar proyecto** → `/proyectos/enviar-proyecto/:id`.
- Deja el proyecto en `EN_REVISION_JURADO_PROYECTO`.

### Paso 8 — Jurado evalúa el proyecto

- `Espacio para revisar los proyectos a cargo` → **Aprobado** → `LISTO_PARA_SUSTENTAR`.

### Paso 9 — CIECYT programa y registra el acto público

- `Espacio para revisar las propuestas del Ciecyt` → columna de acto público → **Programar**
  (requiere estado `LISTO_PARA_SUSTENTAR`) → **Registrar realizada**.

### Paso 10 — Jurado evalúa la sustentación

- `Evaluar la Sustentación de los Proyectos` → `EN_EVALUACION_SUSTENTACION` →
  `NOTA_DEFINITIVA` (o `AJUSTES_SUSTENTACION`).

### Paso 11 — CIECYT finaliza

- `NOTA_DEFINITIVA` → `FINALIZADO` (transition 95228, `ROLE_CIECYT`).

## 6. Ruta de radicación y validación documental (flujo principal)

Es la ruta que usa la pantalla `/propuesta/enviar_propuesta/:id`: el estudiante radica
(`PREINSCRITA`/`EN_ELABORACION_PROPUESTA`/`OBSERVACIONES_DOCUMENTACION → EN_VALIDACION_DOCUMENTAL`,
95200/95206/95203), el CIECYT valida y habilita, y luego el estudiante solicita el aval
(`HABILITADO → EN_REVISION_ASESOR`, 95205). Los requisitos generados para Tesis son 7:

1. Avance minimo del 75% del trabajo de grado (tipo CAMPO, se entrega un dato numérico)
2. Certificado de estudiante activo (DOCUMENTO)
3. Constancia de asistencia a sustentaciones (DOCUMENTO)
4. Formato de inscripcion de la opcion de grado (DOCUMENTO)
5. Propuesta de trabajo de grado (DOCUMENTO)
6. Recibo de pago de la opcion de grado (DOCUMENTO)
7. Record academico (DOCUMENTO)

Endpoints (ya usados por `/propuesta/enviar_propuesta/:id` y por el modal de requisitos del CIECYT):

```powershell
# Estudiante: PREINSCRITA -> EN_VALIDACION_DOCUMENTAL (genera los 7 requisitos)
POST /api/proyectos/{id}/solicitar-validacion-documental

# Estudiante: entrega cada requisito
POST /api/requisito-proyectos/{requisitoId}/entregar
  body: {"archivo":"<nombre archivo>"} o {"observacion":"80"} para el requisito CAMPO

# CIECYT: aprueba cada requisito
POST /api/requisito-proyectos/{requisitoId}/validar
  body: {"aprobado":true,"observacion":"ok"}

# CIECYT: habilita el proyecto (falla si queda un requisito obligatorio sin aprobar)
POST /api/proyectos/{id}/validar-documentacion
  body: {"aprobado":true,"observacion":"..."}
# o, si hay observaciones:
POST /api/proyectos/{id}/validar-documentacion
  body: {"aprobado":false,"observacion":"falta certificado"}   # -> OBSERVACIONES_DOCUMENTACION
```

Con el proyecto en `HABILITADO`, el estudiante solicita el aval del asesor y pasa a
`EN_REVISION_ASESOR` (95205).

## 7. Reglas de autorización que conviene conocer

- `POST /api/proyectos/{id}/cambiar-estado` exige que quien llama sea **participante** del
  proyecto (estudiante/asesor/jurado designado) o CIECYT/ADMIN. Un rol con la transición
  habilitada pero sin relación con el proyecto recibe `403 Forbidden`.
- Los estudiantes sin relación con el proyecto reciben
  `No tiene relacion con el proyecto {id}, no puede operarlo`.
- Un rechazo por salto no permitido o por rol devuelve `400` con
  `Transición de estado no permitida: {origen} -> {destino} para la modalidad 'Tesis'`.
- `POST /api/proyectos/{id}/validar-documentacion` devuelve `500` cuando hay requisitos
  obligatorios sin aprobar; el mensaje lista cuáles.
- `POST /api/proyectos/{id}/acto/programar` exige `LISTO_PARA_SUSTENTAR`
  (`LISTO_PARA_SOCIALIZAR` en modalidades sin jurado) y el body `{"fecha":"yyyy-MM-dd"}`.
- Repetir `cambiar-estado` al mismo estado es idempotente: devuelve `200` sin error.
- El frontend crea y actualiza por `POST/PUT /api/proyects` (endpoint legacy), no por
  `/api/proyectos`. Ambos delegan en el mismo servicio.
