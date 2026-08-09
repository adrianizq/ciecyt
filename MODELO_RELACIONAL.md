# Modelo Relacional — Base de Datos CIECYT

Diagrama del modelo relacional de la base de datos `ciecyt_db` generado a partir del esquema real de PostgreSQL (usuario `postgres`, host local).

- **Tablas:** 53
- **Relaciones:** 60 (45 con constraint `FOREIGN KEY` + 15 relaciones lógicas definidas en JPA sin constraint en la BD)
- **Fecha de generación:** 2026-08-08
- **Formato vectorial:** [SVG](MODELO_RELACIONAL.svg) · [PDF](MODELO_RELACIONAL.pdf)

## Imagen del modelo

![Modelo Relacional CIECYT](MODELO_RELACIONAL.png)

## Convenciones del diagrama

| Símbolo | Significado |
|---|---|
| `PK` | Columna que forma parte de la clave primaria (PK) |
| `}o--||` | Relación muchos-a-uno: la tabla de la izquierda referencia a la tabla de la derecha |
| Etiqueta de relación | Columna FK de la tabla hija que referencia a la tabla padre |

## Fuente (Mermaid)

El diagrama fue generado con Mermaid. Para regenerarlo o editarlo, copie el bloque `erDiagram` en cualquier visor de Mermaid (https://mermaid.live), y para exportarlo a SVG/PNG/PDF utilice `mmdc`:

```bash
npx @mermaid-js/mermaid-cli -i modelo_relacional.mmd -o MODELO_RELACIONAL.svg
npx @mermaid-js/mermaid-cli -i modelo_relacional.mmd -o MODELO_RELACIONAL.png -w 3831 -H 13996
```

```mermaid
erDiagram
    direction LR

    ER_acuerdo {
        bigint id PK
        varchar acuerdo
        varchar version
        varchar codigo
        date fecha
    }

    ER_adjunto_proyecto_fase {
        bigint id PK
        varchar nombre_adjunto
        date fecha_creacion
        date fecha_modificacion
        integer estado_adjunto
        varchar adjunto_proyecto_fase_fase_fase
        varchar nombre_archivo_original
        date fecha_inicio
        date fecha_fin
        bigint adjunto_proyecto_fase_fase_id
        varchar archivo_content_type
        bigint proyecto_fase_proyecto_id
        varchar archivo
        varchar proyecto_fase_proyecto_titulo
        varchar adjunto_proyecto_fase
    }

    ER_adjunto_retroalimentacion {
        bigint id PK
        varchar nombre_adjunto
        date fecha_creacion
        date fecha_modificacion
        integer estado_adjunto
        varchar adjunto_retroalimentacion
        varchar nombre_archivo_original
        date fecha_inicio
        date fecha_fin
        bigint adjunto_retroalimentacion_proyecto_id
        varchar archivo_content_type
        varchar authority
        varchar archivo
        bigint adjunto_retroalimentacion_fase_id
    }

    ER_categorizacion {
        bigint id PK
        varchar categoria
        varchar descripcion
        bigint categorizacion_proyecto_id
    }

    ER_ciclo {
        bigint id PK
        varchar ciclo
    }

    ER_ciclo_propedeutico {
        bigint id PK
        varchar ciclo
        bigint modalidad_id
        bigint ciclo_propedeutico_acuerdo_id
    }

    ER_cronograma {
        bigint id PK
        varchar actividad
        integer duracion
        date fecha_inicio
        date fecha_fin
        bigint cronograma_proyecto_id
        integer orden_vista
    }

    ER_cronograma_ciecyt {
        bigint id PK
        varchar titulo_cronograma
        date fecha_inicio
        date fecha_fin
        varchar observaciones
        bigint cronograma_ciecyt_modalidad_id
    }

    ER_cronograma_ciecyt_fases {
        bigint id PK
        date inicio_fase
        date fin_fase
        varchar texto_explicativo
        bigint cronograma_ciecyt_fases_cronograma_ciecyt_id
        bigint cronograma_ciecyt_fases_fases_id
    }

    ER_departamento {
        bigint id
        text codigo_dane
        text departamento
    }

    ER_elemento {
        bigint id PK
        varchar elemento
        text descripcion
        bigint elemento_fases_id
        bigint orden
    }

    ER_elemento_modalidad {
        bigint id PK
        bigint elemento_id
        bigint modalidad_id
    }

    ER_elemento_proyecto {
        bigint id PK
        text dato
        bigint elemento_proyecto_elemento_id
        bigint elemento_proyecto_proyecto_id
        text elemento_proyecto_proyecto_descripcion
        bigint elemento_fases_id
    }

    ER_entidad {
        bigint id PK
        varchar entidad
        varchar nit
    }

    ER_entidad_financiadora {
        bigint id PK
        double valor
        boolean aprobada
        bigint entidad_financiadora_entidad_id
        bigint entidad_financiadora_proyecto_id
    }

    ER_facultad {
        bigint id PK
        varchar codigo_facultad
        varchar facultad
    }

    ER_fases {
        bigint id PK
        text fase
        boolean notificable
    }

    ER_ficha_tecnica {
        bigint id PK
        varchar titulo_profesional
        varchar titulo_postgrado
        varchar experiencia
        bigint ficha_tecnica_user_id
    }

    ER_formato {
        bigint id PK
        varchar formato
        varchar version
        varchar codigo
        date fecha
    }

    ER_grupo_semillero {
        bigint id PK
        varchar nombre
        boolean tipo
    }

    ER_impactos_esperados {
        bigint id PK
        varchar impacto
        integer plazo
        varchar indicador
        varchar supuestos
        bigint impactos_esperado_proyecto_id
        integer orden_vista
    }

    ER_informacion_pasantia {
        integer duracion_horas
        text direccion
        text email
        boolean lunes
        boolean martes
        boolean miercoles
        boolean jueves
        boolean viernes
        boolean sabado
        boolean domingo
        boolean bono_alimenticio
        boolean apoyo_economico
        boolean auxilio_transporte
        boolean capacitacion
        text otro_apoyo
        text nombre_empresa
        text nit_empresa
        text direccion_empresa
        text sector_economico_empresa
        text representante_legal_empresa
        text asesor_empresa
        text cargo_asesor_empresa
        text email_asesor_empresa
        text municipio_empresa
        text telefono_contacto_empresa
        text email_empresa
        text departamento_empresa
        text identificacion_representante_legal
        text profesion_asesor_empresa
        text celular_asesor_empresa
        bigint id PK
        bigint informacion_pasantia_proyecto_id
        text convenio
    }

    ER_integrante_proyecto {
        bigint id PK
        varchar integrante
        varchar descripcion
        bigint integrante_proyecto_user_id
        bigint integrante_proyecto_proyecto_id
        bigint integrante_proyecto_roles_modalidad_id
    }

    ER_investigacion_tipo {
        bigint id PK
        text investigacion_tipo
        text investigacion_tipo_descripcion
        text tipo
        text tipo_descripcion
    }

    ER_jhi_authority {
        varchar name PK
    }

    ER_jhi_persistent_audit_event {
        bigint event_id PK
        varchar principal
        timestamp event_date
        varchar event_type
    }

    ER_jhi_persistent_audit_evt_data {
        bigint event_id PK
        varchar name PK
        varchar value
    }

    ER_jhi_user {
        bigint id PK
        varchar login
        varchar password_hash
        varchar first_name
        varchar last_name
        varchar email
        varchar image_url
        boolean activated
        varchar lang_key
        varchar activation_key
        varchar reset_key
        varchar created_by
        timestamp created_date
        timestamp reset_date
        varchar last_modified_by
        timestamp last_modified_date
    }

    ER_jhi_user_authority {
        bigint user_id PK
        varchar authority_name PK
    }

    ER_linea_investigacion {
        bigint id PK
        varchar linea
        varchar codigo_linea
        bigint linea_padre_id
        bigint linea_investigacion_programa_id
    }

    ER_menu {
        bigint id PK
        varchar nombre
        varchar url
        varchar icono
        boolean activo
        bigint menu_padre_id
        boolean es_publico
        integer orden
        text rol
    }

    ER_modalidad {
        bigint id PK
        varchar modalidad
        bigint modalidad_acuerdo_id
        boolean contiene_linea
    }

    ER_modalidad_ciclo {
        bigint id PK
        bigint modalidad_id
        bigint ciclo_id
    }

    ER_municipio {
        text region
        text codigo_dane_departamento
        text departamento
        text codigo_dane_municipio
        text municipio
        bigint id
    }

    ER_pregunta {
        bigint id PK
        varchar encabezado
        text descripcion
        text pregunta
        bigint pregunta_tipo_pregunta_id
        text pregunta_elemento
        bigint pregunta_elemento_id
        double puntaje
        double puntaje_maximo
        bigint pregunta_fase_id
        text pregunta_fase
        bigint orden
    }

    ER_pregunta_authority {
        bigint id
        bigint pregunta3_id PK
        varchar authority_name PK
    }

    ER_pregunta_modalidad {
        bigint pregunta_id PK
        bigint modalidad2_id PK
        bigint id
    }

    ER_presupuesto_valor {
        bigint id PK
        varchar descripcion
        varchar justificacion
        integer cantidad
        double valor_unitario
        double especie
        double dinero
        bigint presupuesto_valor_rubro_id
        bigint presupuesto_valor_proyecto_id
        bigint presupuesto_valor_entidad_id
        integer orden_vista
    }

    ER_producto {
        bigint id PK
        varchar producto
    }

    ER_producto_proyecto {
        bigint id PK
        boolean aplica
        varchar descripcion
        bigint producto_proyecto_producto_id
        bigint producto_proyecto_proyecto_id
    }

    ER_programa {
        bigint id PK
        text programa
        text descripcion
        text codigo_interno
        text codigo_snies
        integer creditos
        text ciclo
        text resolucion
        text titulo
        integer duracion_semestres
        bigint programa_facultad_id
    }

    ER_proyecto {
        bigint id PK
        varchar titulo
        varchar url
        varchar lugar_ejecucion
        varchar duracion
        date fecha_ini
        date fecha_fin
        double contrapartida_pesos
        double contrapartida_especie
        text palabras_clave
        varchar convocatoria
        bigint proyecto_linea_investigacion_id
        bigint proyecto_grupo_semillero_id
        bigint proyecto_modalidad_id
        bigint facultad_id
        bigint sub_linea_linea_investigacion_id
        text tipo
        text referencias
        bigint proyecto_programa_id
        text programa
        varchar departamento
        varchar municipio
        boolean viable
        boolean enviado
        timestamptz fecha_envio_propuesta
        timestamptz fecha_envio_proyecto
        double nota
        text conclusion
        text recomendaciones
        text viabilidad
        bigint proyecto_fase_id
        varchar fase
        boolean pre_enviado
        boolean sustentar
        text recomendaciones_jurado_proyecto
        text recomendaciones_asesor_propuesta
        text recomendaciones_asesor_proyecto
        text recomendaciones_jurado_sustentacion
        boolean proyecto_enviado
        bigint proyecto_ciclo_id
        date fecha_pre_envio_propuesta
        date fecha_viabilidad_propuesta
        date fecha_sustentacion_proyecto
    }

    ER_proyecto_fase {
        bigint id PK
        varchar titulo
        boolean cumplida
        date fecha_cumplimiento
        varchar observaciones
        bigint proyecto_fase_fases_id
        bigint proyecto_fase_proyecto_id
    }

    ER_proyecto_respuestas {
        bigint id PK
        text respuesta
        varchar observaciones
        boolean viable
        bigint proyecto_respuestas_pregunta_id
        bigint proyecto_respuestas_proyecto_id
        text proyecto_respuestas_pregunta_pregunta
        double puntaje
        boolean si_no
        text elemento
        bigint pregunta_tipo_pregunta_id
        text pregunta_tipo_pregunta_tipo_pregunta
        text encabezado
        text dato
        text respuesta_texto
        numeric respuesta_numero
        bigint fase_id
        varchar authority
        integer puntaje_maximo
    }

    ER_resultados_esperados {
        bigint id PK
        varchar resultado
        varchar indicador
        varchar beneficiario
        bigint resultados_esperados_proyecto_id
        integer orden_vista
    }

    ER_retroalimentacion {
        bigint id PK
        varchar titulo
        varchar retroalimentacion
        date fecha_retroalimentacion
        date estado_retroalimentacion
        integer estado_proyecto_fase
        bigint retroalimentacion_proyecto_fase_id
        bigint retroalimentacion_user_id
    }

    ER_rol_menu {
        bigint id PK
        boolean permitir_acceso
        boolean permitir_crear
        boolean permitir_editar
        boolean permitir_eliminar
        varchar auth_name
        bigint rol_menu_menu_id
    }

    ER_roles_modalidad {
        bigint id PK
        varchar rol
        integer cantidad
        boolean calificador
        bigint roles_modalidad_modalidad_id
        varchar roles_modalidad_authority_name
    }

    ER_rubro {
        bigint id PK
        varchar rubro
    }

    ER_solicitud {
        bigint id PK
        boolean estado
        varchar asunto
        varchar texto_solicitud
        date fecha_solicitud
        bigint solicitud_integrante_proyecto_id
    }

    ER_tipo_pregunta {
        bigint id PK
        varchar tipo_pregunta
        varchar tipo_dato
    }

    ER_user_info {
        varchar nuip
        varchar codigo_itp
        varchar genero
        varchar celular
        varchar telefono
        text foto
        text foto_content_type
        bigint id PK
    }

    ER_usuario {
        bigint id PK
        varchar usuario
        varchar descripcion
    }


    ER_categorizacion }o--|| ER_proyecto : categorizacion_proyecto_id
    ER_ciclo_propedeutico }o--|| ER_acuerdo : ciclo_propedeutico_acuerdo_id
    ER_ciclo_propedeutico }o--|| ER_modalidad : modalidad_id
    ER_cronograma }o--|| ER_proyecto : cronograma_proyecto_id
    ER_cronograma_ciecyt }o--|| ER_modalidad : cronograma_ciecyt_modalidad_id
    ER_cronograma_ciecyt_fases }o--|| ER_cronograma_ciecyt : cronograma_ciecyt_fases_cronograma_ciecyt_id
    ER_cronograma_ciecyt_fases }o--|| ER_fases : cronograma_ciecyt_fases_fases_id
    ER_elemento_proyecto }o--|| ER_elemento : elemento_proyecto_elemento_id
    ER_elemento_proyecto }o--|| ER_proyecto : elemento_proyecto_proyecto_id
    ER_entidad_financiadora }o--|| ER_entidad : entidad_financiadora_entidad_id
    ER_ficha_tecnica }o--|| ER_jhi_user : ficha_tecnica_user_id
    ER_impactos_esperados }o--|| ER_proyecto : impactos_esperado_proyecto_id
    ER_integrante_proyecto }o--|| ER_proyecto : integrante_proyecto_proyecto_id
    ER_integrante_proyecto }o--|| ER_roles_modalidad : integrante_proyecto_roles_modalidad_id
    ER_integrante_proyecto }o--|| ER_jhi_user : integrante_proyecto_user_id
    ER_jhi_persistent_audit_evt_data }o--|| ER_jhi_persistent_audit_event : event_id
    ER_jhi_user_authority }o--|| ER_jhi_authority : authority_name
    ER_jhi_user_authority }o--|| ER_jhi_user : user_id
    ER_linea_investigacion }o--|| ER_programa : linea_investigacion_programa_id
    ER_linea_investigacion }o--|| ER_linea_investigacion : linea_padre_id
    ER_menu }o--|| ER_menu : menu_padre_id
    ER_modalidad }o--|| ER_acuerdo : modalidad_acuerdo_id
    ER_modalidad_ciclo }o--|| ER_ciclo : ciclo_id
    ER_modalidad_ciclo }o--|| ER_modalidad : modalidad_id
    ER_pregunta }o--|| ER_tipo_pregunta : pregunta_tipo_pregunta_id
    ER_presupuesto_valor }o--|| ER_entidad : presupuesto_valor_entidad_id
    ER_presupuesto_valor }o--|| ER_proyecto : presupuesto_valor_proyecto_id
    ER_presupuesto_valor }o--|| ER_rubro : presupuesto_valor_rubro_id
    ER_producto_proyecto }o--|| ER_producto : producto_proyecto_producto_id
    ER_producto_proyecto }o--|| ER_proyecto : producto_proyecto_proyecto_id
    ER_proyecto }o--|| ER_facultad : facultad_id
    ER_proyecto }o--|| ER_ciclo : proyecto_ciclo_id
    ER_proyecto }o--|| ER_grupo_semillero : proyecto_grupo_semillero_id
    ER_proyecto }o--|| ER_linea_investigacion : proyecto_linea_investigacion_id
    ER_proyecto }o--|| ER_modalidad : proyecto_modalidad_id
    ER_proyecto_fase }o--|| ER_fases : proyecto_fase_fases_id
    ER_proyecto_fase }o--|| ER_proyecto : proyecto_fase_proyecto_id
    ER_proyecto_respuestas }o--|| ER_pregunta : proyecto_respuestas_pregunta_id
    ER_proyecto_respuestas }o--|| ER_proyecto : proyecto_respuestas_proyecto_id
    ER_resultados_esperados }o--|| ER_proyecto : resultados_esperados_proyecto_id
    ER_retroalimentacion }o--|| ER_proyecto_fase : retroalimentacion_proyecto_fase_id
    ER_retroalimentacion }o--|| ER_jhi_user : retroalimentacion_user_id
    ER_rol_menu }o--|| ER_menu : rol_menu_menu_id
    ER_roles_modalidad }o--|| ER_modalidad : roles_modalidad_modalidad_id
    ER_solicitud }o--|| ER_integrante_proyecto : solicitud_integrante_proyecto_id
    ER_adjunto_proyecto_fase }o--|| ER_fases : adjunto_proyecto_fase_fase_id
    ER_adjunto_retroalimentacion }o--|| ER_fases : adjunto_retroalimentacion_fase_id
    ER_adjunto_retroalimentacion }o--|| ER_proyecto : adjunto_retroalimentacion_proyecto_id
    ER_elemento }o--|| ER_fases : elemento_fases_id
    ER_elemento_modalidad }o--|| ER_elemento : elemento_id
    ER_elemento_proyecto }o--|| ER_fases : elemento_fases_id
    ER_entidad_financiadora }o--|| ER_proyecto : entidad_financiadora_proyecto_id
    ER_informacion_pasantia }o--|| ER_proyecto : informacion_pasantia_proyecto_id
    ER_pregunta }o--|| ER_elemento : pregunta_elemento_id
    ER_pregunta }o--|| ER_fases : pregunta_fase_id
    ER_programa }o--|| ER_facultad : programa_facultad_id
    ER_proyecto }o--|| ER_fases : proyecto_fase_id
    ER_proyecto }o--|| ER_programa : proyecto_programa_id
    ER_proyecto }o--|| ER_linea_investigacion : sub_linea_linea_investigacion_id
    ER_proyecto_respuestas }o--|| ER_fases : fase_id
```

## Notas

- **Relaciones sin constraint FK:** `adjunto_proyecto_fase.adjunto_proyecto_fase_fase_id`, `adjunto_retroalimentacion.adjunto_retroalimentacion_fase_id`, `adjunto_retroalimentacion.adjunto_retroalimentacion_proyecto_id`, `elemento.elemento_fases_id`, `elemento_modalidad.elemento_id`, `elemento_proyecto.elemento_fases_id`, `entidad_financiadora.entidad_financiadora_proyecto_id`, `informacion_pasantia.informacion_pasantia_proyecto_id`, `pregunta.pregunta_elemento_id`, `pregunta.pregunta_fase_id`, `programa.programa_facultad_id`, `proyecto.proyecto_fase_id`, `proyecto.proyecto_programa_id`, `proyecto.sub_linea_linea_investigacion_id`, `proyecto_respuestas.fase_id`.
- Las tablas de Liquibase (`databasechangelog`, `databasechangeloglock`) se excluyeron del diagrama.
- Las tablas del módulo de seguridad (`jhi_user`, `jhi_authority`, `jhi_user_authority`, `jhi_persistent_audit_event`, `jhi_persistent_audit_evt_data`) forman parte del esquema estándar de JHipster.
