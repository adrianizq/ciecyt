# Despliegue CIECYT

## Requisitos

- JDK 17
- Node.js compatible con el proyecto
- PostgreSQL 17
- Variables de entorno definidas en el servidor (no en el repositorio)

## Variables de entorno obligatorias

`application-prod.yml` ya no contiene credenciales ni secretos. Antes de arrancar en producción defina:

```text
DB_URL=jdbc:postgresql://<host>:5432/ciecyt_db
DB_USER=<usuario>
DB_PASSWORD=<password>

MAIL_USER=<correo>
MAIL_PASSWORD=<password-de-aplicacion>
MAIL_FROM=Ciecyt ITP<noreply@itp.edu.co>
APP_BASE_URL=https://<dominio>

JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET=<secreto-base64-de-512-bits>
```

Opcionales para reglas de plazos:

```text
APP_PLAZOS_DIAS_JURADO=7
APP_PLAZOS_DIAS_EVALUACION=10
APP_PLAZOS_UMBRAL_AVISO=2
APP_PLAZOS_LIBERAR_AUTOMATICAMENTE=false
```

Para generar el secreto JWT:

```powershell
openssl rand -base64 64
```

## Carga masiva de días no hábiles

ADMIN y CIECYT pueden importar festivos desde `/admin/festivos`. El archivo debe ser CSV en UTF-8 con esta estructura:

```csv
fecha,descripcion,activo
2026-01-01,Año Nuevo,true
2026-01-12,Reyes Magos,true
```

- `fecha`: `AAAA-MM-DD` o `DD/MM/AAAA`.
- `descripcion`: obligatoria, máximo 255 caracteres.
- `activo`: opcional; se acepta `true/false`, `1/0` o `si/no` (por defecto `true`).
- Si la fecha ya existe, se actualiza su descripción y estado.
- Las filas inválidas se informan por número de línea sin detener el resto de la importación.
- La plantilla descargable está disponible desde la misma pantalla.

## Base de datos y Liquibase

- Liquibase se ejecuta automáticamente al arrancar con el perfil `prod`.
- No usar el contexto `faker` en producción.
- Antes de actualizar: ejecutar `scripts/backup-db.ps1`.
- Probar la restauración en una base desechable con `scripts/restore-db.ps1` antes del despliegue real.
- Verificar que el usuario de la aplicación tenga permisos de DDL durante la migración inicial.

## Build y arranque

```powershell
.\mvnw.cmd -Pprod clean package
java -Duser.timezone=America/Bogota -jar target/ciecyt-*.jar --spring.profiles.active=prod
```

Usar `America/Bogota` para que el job diario de plazos (07:00) y los días hábiles coincidan con la operación institucional.

## Tareas programadas

- `AsyncConfiguration` habilita `@Scheduling` y `@Async`.
- `PlazoNotificacionService` ejecuta la revisión diaria de plazos a las 07:00.
- El botón manual de revisión sigue disponible para CIECYT/ADMIN.
- Tras el despliegue, confirmar que el job se ejecutó sin errores en los logs.

## Verificación posterior

1. `GET /management/health` debe responder `UP`.
2. Iniciar sesión como `admin` y validar dashboard, menús y catálogos.
3. Revisar `/admin/periodos` y `/admin/festivos`.
4. Verificar `/admin/metricas_negocio` y la descarga de reportes CSV.
5. Ejecutar smoke tests locales:

```powershell
npm.cmd run smoke:admin
npm.cmd run smoke:ui
```

6. Revisar logs de correo y confirmar que las notificaciones salen con el remitente configurado.

## Reglas institucionales

- El Acuerdo 025 solo distingue Tesis de pregrado y Tesis de posgrado; no existe una modalidad de Tesis de maestría. En la base, 9004 es `Tesis`, 9002 es `Pasantia Investigativa` y ambas requieren jurado según el artículo 9.
- Cantidad de jurados: el acuerdo usa la expresión singular "el jurado"; hoy se configura 1 en `roles_modalidad`.
- Liberación automática de asesor/jurado: deshabilitada por defecto (`APP_PLAZOS_LIBERAR_AUTOMATICAMENTE=false`). Al vencer el plazo solo se notifica a CIECYT y la liberación queda como acción manual.
- Días de plazo para designación de jurado: 7 días hábiles, conforme al artículo 9, parágrafo 3.
- Días de evaluación de propuesta y documento final: 10 días hábiles, conforme a los artículos 10 y 13.
- Calendario oficial de periodos académicos y días no hábiles: cargar en `/admin/periodos` y `/admin/festivos`.
- Regla de continuidad 3+1 y requisitos por modalidad/ciclo: validar con la institución antes de la primera convocatoria real.

## Seguridad

- Rotar las credenciales de base de datos, SMTP y JWT que estaban versionadas en `application-prod.yml`.
- Entregar los secretos por gestor de secretos o variables de entorno del servidor.
- No versionar archivos `.env`, respaldos ni claves.
- Publicar la aplicación detrás de HTTPS.
