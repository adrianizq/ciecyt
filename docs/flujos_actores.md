# Flujos de actores - Ciecyt ITP

Mapa de flujos por actor segun lo implementado hasta ahora, alineado con el Acuerdo 25.
Roles operativos: **Decano, CIECYT, Estudiante, Asesor, Jurado y Administrador**.

---

## 1. Decano (Decanatura) - dueno del padron

Remite la lista de docentes habilitados y corrige la vigencia. No designa en tesis (lo hace
CIECYT); si designa asesor en pasantia/diplomado.

```
  /decanura/padron                     /decanura/remision-padron
        |                                    |
  [elige facultad si tiene varias]     [elige periodo "2026-1"]   <- borradores repetibles
        |                                    |
  habilita/deshabilita docentes        crea BORRADOR  POST /api/remisiones-padron
  (rol ASESOR|JURADO, vigencia)          (snapshot server-side de los VIGENTES)
        |                                    |
  consulta historial por docente        ENVIA  POST /{id}/enviar
  (modal Historial)                           |
        |                              +-----+------------------------+
       |                               | ? ya hay ENVIADA del         |
  +----+----+                          |   mismo periodo+facultad?    |
  |         |                          +-----+------------------------+
  (ajusta)  (ver lista)                      | Si -> 400 error.periodoYaRemitido
  (tambien: designar asesor en               | No -> estado ENVIADO (queda inmutable)
   pasantia/diplomado: 9001/9002/9003/9005)  |
                                             |
                      CIECYT ve la remision (solo lectura)
```

Hermetismo: el decano **no** ve las remisiones ni el padron de otras facultades (403).

---

## 2. CIECYT - designa y recibe

Consulta todos los padrones y las remisiones ENVIADAS de la institucion; hace la designacion
formal de asesor/jurado en tesis; registra y verifica a los profesionales externos.

```
             +-------------------------------------------------+
             |  CIECYT (gestor global)                          |
             +-------------------------------------------------+
   |                      |                                |
[padron de cualquier       [recibe remision]               [designacion]
 facultad: consulta]      GET /api/remisiones-padron       asignar_asesor / asignar_jurado
   correccion NO          (solo ENVIADAS, todas            desde la lista HABILITADA
   modifica la lista      las facultades)                  de la facultad del proyecto
        |                      |                                  |
   historial por         +-----+-----+                      si el titular ya designado
   docente              | CIECYT o  |                       salio del padron: se anade
                        | ADMIN only|                       marcado "(no vigente -- ya
                        +-----------+                       designado)"
                                                                  |
        [profesional externo]  POST /api/asesores-externos        |
        registrar (rol ASESOR|JURADO, facultad)                   |
        verificar POST /{id}/verificar  (marca idoneidad,         |
        fecha y login del CIECYT)                                 |
              |                                                   |
              +---------------------------------------------+     |
              en designacion: casilla "profesional externo"  |     |
              ofrece SOLO los ya verificados de esa facultad |     |
                                                             v     v
                                       puede designar en TESIS (9004); el decano
                                       no puede (403); el estudiante tampoco
```

Hermetismo: cualquier otro rol al pedir `GET /remisiones-padron` -> 403. Registrar/verificar
externos: solo CIECYT/ADMIN (un decano recibe 403). Consultar externos de una facultad: igual
que el padron (`puedeVerHabilitadosDe`).

---

## 3. Estudiante (proponente) - flujo de propuesta

Crea la propuesta; elige su **asesor** solo en tesis y desde el padron de su facultad; **no**
elige jurado; en pasantia/diplomado tampoco elige asesor.

```
 Propuesta nueva (tesis)
     |
  1  Informacion general (facultad, programa) --> asesor = padron de LA FACULTAD
     |                                           (ASESOR habilitado; si no miembro -> 403)
  2  elegir asesor  [asesor_nueva]
     |
  3  jurado  [jurado_nueva]
     |        "La designacion la hace CIECYT"  -> Continuar (no guarda nada)
     |
  4  inscripcion -> elementos -> cronograma -> adjunto -> ENVIAR propuesta
     |
  Pasantia / Diplomado
     |
  Informacion general --> campo Asesor = aviso
                          "La decanatura designara al asesor"  (sin selector)
                          (integrante de equipo: si agrega companeros del directorio
                           de estudiantes)
```

Hermetismo: en modalidades 9001/9002/9003/9005 un participante que intente GUARDAR un asesor
-> 403 (lo hace la decanatura).

---

## 4. Asesor - tutor del proyecto

```
  Entra -> "Mis proyectos" (donde figura en integrante_proyecto, rol ASESOR)
     |
  Abre el proyecto -> fases / estados / retroalimentacion / adjuntos
     |
  (pendientes de auditoria: modulos de actas, impedimentos, plazos)
```

Solo puede operar proyectos donde esta registrado (orden de autorizacion: gestor global ->
decano -> participante/asesor con sus permisos por rol).

El asesor puede ser un **profesional externo** (sin vinculo laboral con la institucion) cuando
la lista habilitada no tiene disponibilidad: el CIECYT lo registra y verifica su idoneidad
(Acuerdo 25, art. 8 par. 1); se designa desde `asignar_asesor.vue` marcando la casilla
"profesional externo". Su designacion queda en `integrante_proyecto.integrante_proyecto_externo_id`
(sin cuenta de usuario).

---

## 5. Jurado - evaluacion

```
  Entra -> proyectos donde figura como JURADO (asignado por CIECYT)
     |
  Evalua (sustentacion, actas - modulos pendientes de implementar)
```

Importante: el jurado **ya no se asigna por directorio**; solo por designacion de CIECYT desde
la lista habilitada/remitida o, cuando no haya disponibilidad, un **jurado externo** verificado
por el CIECYT (Acuerdo 25, art. 9 par. 1) desde `asignar_jurado.vue`.

---

## 6. Administrador - soporte

```
  Gestion de usuarios y entidades (CRUD JHipster: /admin/*)
     |
  Puede habilitar/corregir padrones de CUALQUIER facultad
  (puedeHabilitarDocentesEn incluye a ADMIN)
     |
  Puede ver las remisiones globales de todas las facultades (200)
  (pendiente: entrada de ADMIN sin facultad en la pagina del padron)
```

---

## Reglas transversales ya fiadas (backend + probadas)

- `puedeHabilitarDocentesEn(facultad)` -> admin o decano de esa facultad.
- `puedeVerHabilitadosDe(facultad)` -> CIECYT, decano de esa facultad, o participante de un
  proyecto de esa facultad.
- `puedeDesignarIntegrantesDe(proyecto, rolModalidad)` -> CIECYT todas; decano solo
  9001/9002/9003/9005 con autoridad ASESOR/JURADO; participante solo tesis como ASESOR.
- `/users/asesores|jurados` -> solo CIECYT/ADMIN; `/users/estudiantes` -> abierto.
- Las remisiones se pueden crear/reenviar varias veces, pero **una** ENVIADA por
  (facultad, periodo) - el envio vuelve immutable el snapshot.
- **Profesional externo** (`/api/asesores-externos*`): registrar/verificar solo CIECYT/ADMIN;
  consultar segun `puedeVerHabilitadosDe`. Al registrar queda `idoneidadVerificada=false`; la
  verifica el CIECYT (POST /{id}/verificar) con fecha y login. Al designar, el backend exige:
  existe + verificada + misma facultad del proyecto + no convivir con un usuario institucional.
  Cambiarle el rol (ASESOR<->JURADO) resetea la verificacion.

---

## Pendientes que afectan estos flujos

- Modelo de asesor externo **operativo** por API y en las paginas de designacion; falta la
  pagina del CIECYT para registrar/verificar externos (hoy se hace por API).
- Aun sin resolver el "en caso de no haber disponibilidad": el sistema no impide registrar un
  externo si la lista tiene cupo; es una politica pendiente de confirmar.
- Auditorias del Acuerdo: estado inicial en `POST /api/proyectos`, validacion de
  `rol_requerido`, notificaciones, adjuntos, borrados en cascada.
- Modulos del Acuerdo: impedimentos, plazos, continuidad, novedades, repositorio, publicacion,
  plagio, actas, sustentacion virtual.
- Entrada de ADMIN sin facultad en `padron_habilitados.vue`.
- Decidir si CIECYT solo recibe la remision o tambien la aprueba/rechaza.
- Prueba en navegador de `remision_padron.vue`.
- Sincronizacion de `DATABASECHANGELOG` (no ejecutar Liquibase a ciegas; tablas del padron,
  remision y asesor externo aplicadas a mano en BD dev).