<template>
  <div class="home">
    <!-- Header -->
    <div class="ciecyt-header text-center">
      <h1>CIECYT-ITP</h1>
      <div class="accent-line mx-auto"></div>
      <h2>Sistema de Gestión de Trabajos de Grado</h2>
    </div>

    <!-- Authenticated: Dashboard por rol -->
    <div v-if="authenticated">

      <!-- Vista ESTUDIANTE primero (incluso con admin asignado) — su rol primario en este software. -->
      <div v-if="esEstudiante" class="row">
        <div class="col-12 mb-4">
          <div class="d-flex align-items-center justify-content-between">
            <div>
              <h2 class="mb-0" style="color:#1a2332; font-weight:600;">Mis propuestas</h2>
              <p class="text-muted mb-0" style="font-size:0.9rem;">
                Bienvenido, {{ username }}. Selecciona una opcion de las que aparecen a continuacion.
              </p>
            </div>
          </div>
        </div>

        <!-- Aviso de propuesta vigente: el Acuerdo 25 solo admite una por ciclo y programa. -->
        <div class="col-12" v-if="propuestasActivas.length > 0">
          <div class="alert alert-warning" role="alert">
            <strong>Ya tienes una propuesta en curso.</strong>
            El Acuerdo 25 permite una sola propuesta vigente por ciclo y programa. Continúa o
            modifícala en lugar de crear otra:
            <div class="mt-2">
              <router-link
                v-for="propuesta in propuestasActivas"
                :key="propuesta.id"
                :to="rutaEdicion(propuesta)"
                class="btn btn-sm btn-primary me-2 mb-1"
              >
                <font-awesome-icon icon="arrow-right" class="me-1" />
                {{ propuesta.titulo || ('Propuesta #' + propuesta.id) }}
              </router-link>
            </div>
          </div>
        </div>

        <!-- Solicita el listado-estudiante para ver propuestas en curso -->
        <div class="col-md-4 col-lg-4 mb-4">
          <router-link :to="{ name: 'ListadoProyectoView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ver mis propuestas en curso"
              @keydown.enter.prevent="$router.push({ name: 'ListadoProyectoView' })"
              @keydown.space.prevent="$router.push({ name: 'ListadoProyectoView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;">
                  <font-awesome-icon icon="list" />
                </div>
                <h3 class="card-title">Mis propuestas</h3>
                <p class="card-text">
                  Lista de propuestas y proyectos que ya iniciaste. Continúa con el paso donde quedaste.
                </p>
              </div>
            </div>
          </router-link>
        </div>

        <!-- Inicia una nueva propuesta. -->
        <div class="col-md-4 col-lg-4 mb-4">
          <router-link :to="{ name: 'PropuestaInformacionGenearalNuevaView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Iniciar nueva propuesta de grado"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaInformacionGenearalNuevaView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaInformacionGenearalNuevaView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#C4A94D;">
                  <font-awesome-icon icon="plus" />
                </div>
                <h3 class="card-title">Nueva propuesta</h3>
                <p class="card-text">
                  Inicia una propuesta. El sistema registrará el borrador (Acuerdo 25, art. 5).
                </p>
              </div>
            </div>
          </router-link>
        </div>

        <!-- Convocatorias abiertas para estudiantes (Acuerdo 25, art. 16 par. 2 y art. 19). -->
        <div class="col-md-4 col-lg-4 mb-4">
          <router-link :to="{ name: 'ConvocatoriasPasantiaView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ver convocatorias de pasantía"
              @keydown.enter.prevent="$router.push({ name: 'ConvocatoriasPasantiaView' })"
              @keydown.space.prevent="$router.push({ name: 'ConvocatoriasPasantiaView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#1565C0;">
                  <font-awesome-icon icon="book" />
                </div>
                <h3 class="card-title">Convocatorias de pasantía</h3>
                <p class="card-text">
                  Consulta las plazas abiertas y postúlate a una pasantía.
                </p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Vista DECANO: padron de habilitados + remision. Acuerdo 25 art. 8 par. 2 y art. 9 par. 2. -->
      <div v-else-if="esDecano" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">Decanatura</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Mantenimiento del padrón y remisión al CIECYT (Acuerdo 25, artículos 8 y 9).
          </p>
        </div>
        <div class="col-md-6 mb-4">
          <router-link
            :to="{ name: 'PadronHabilitados' }"
            class="text-decoration-none"
          >
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir al padrón de docentes habilitados"
              @keydown.enter.prevent="$router.push({ name: 'PadronHabilitados' })"
              @keydown.space.prevent="$router.push({ name: 'PadronHabilitados' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;">
                  <font-awesome-icon icon="users" />
                </div>
                <h3 class="card-title">Padrón de docentes habilitados</h3>
                <p class="card-text">
                  Da de alta y baja a los docentes que pueden ser asesores o jurados en la facultad.
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-6 mb-4">
          <router-link
            :to="{ name: 'RemisionPadron' }"
            class="text-decoration-none"
          >
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a la remisión del padrón"
              @keydown.enter.prevent="$router.push({ name: 'RemisionPadron' })"
              @keydown.space.prevent="$router.push({ name: 'RemisionPadron' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#5D4037;">
                  <font-awesome-icon icon="paper-plane" />
                </div>
                <h3 class="card-title">Remisión del padrón a CIECYT</h3>
                <p class="card-text">
                  Arma y envía la remisión del padrón por periodo académico. Una vez enviada queda
                  como constancia inmutable.
                </p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Vista ASESOR: mis propuestas a evaluar. Acuerdo 25 art. 8. -->
      <div v-else-if="esAsesor" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">Asesoría</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Propuestas y proyectos donde figura como asesor (Acuerdo 25, artículo 8).
          </p>
        </div>
        <div class="col-md-6 mb-4">
          <router-link
            :to="{ name: 'PropuestaListadoAsesorView' }"
            class="text-decoration-none"
          >
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a propuestas a evaluar como asesor"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoAsesorView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoAsesorView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;">
                  <font-awesome-icon icon="clipboard-list" />
                </div>
                <h3 class="card-title">Propuestas a evaluar</h3>
                <p class="card-text">
                  Lista de propuestas en las que aparece como asesor. Aqui emite correcciones y el
                  concepto final.
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-6 mb-4">
          <router-link
            :to="{ name: 'ProyectoListadoAsesorView' }"
            class="text-decoration-none"
          >
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a proyectos a evaluar como asesor"
              @keydown.enter.prevent="$router.push({ name: 'ProyectoListadoAsesorView' })"
              @keydown.space.prevent="$router.push({ name: 'ProyectoListadoAsesorView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#2E7D32;">
                  <font-awesome-icon icon="tasks" />
                </div>
                <h3 class="card-title">Proyectos a evaluar</h3>
                <p class="card-text">
                  Lista de proyectos donde figura como asesor. Revisa viabilidad técnica, contenido
                  del documento y cumplimiento del cronograma.
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-6 mb-4">
          <router-link
            :to="{ name: 'PropuestaListadoSustentacionView' }"
            class="text-decoration-none"
          >
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a Sustentaciones programadas"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoSustentacionView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoSustentacionView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#C4A94D;">
                  <font-awesome-icon icon="calendar" />
                </div>
                <h3 class="card-title">Sustentaciones</h3>
                <p class="card-text">
                  Programación y seguimiento de sustentaciones (Acuerdo 25, artículo 14).
                </p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Vista JURADO: mis evaluaciones pendientes y sustentaciones. Acuerdo 25 art. 9, 10. -->
      <div v-else-if="esJurado" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">Jurado</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Propuestas, proyectos y sustentaciones donde figura como jurado (Acuerdo 25, artículos 9 y 10).
          </p>
        </div>
        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoJuradoView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a propuestas asignadas como jurado"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoJuradoView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoJuradoView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;">
                  <font-awesome-icon icon="user-check" />
                </div>
                <h3 class="card-title">Propuestas a evaluar</h3>
                <p class="card-text">
                  Lista de propuestas en evaluación. Emite concepto de viabilidad con la rúbrica
                  institucional.
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoSustentacionView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a sustentaciones"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoSustentacionView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoSustentacionView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#C4A94D;">
                  <font-awesome-icon icon="calendar" />
                </div>
                <h3 class="card-title">Sustentaciones</h3>
                <p class="card-text">
                  Programación y registro de sustentaciones (art. 14).
                </p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Vista CIECYT: seguimiento y gestion integral del proceso de grado. -->
      <div v-else-if="esCiecyt" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">CIECYT</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Centro de Investigaciones. Validacion documental, designaciones, seguimiento, plazos y cierre del tramite.
          </p>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Ir a la bandeja general"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;"><font-awesome-icon icon="th-list" /></div>
                <h3 class="card-title">Bandeja general</h3>
                <p class="card-text">Todas las propuestas y proyectos del sistema en una sola vista.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'validar' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Proyectos por validar"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'validar' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'validar' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#2E7D32;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Validacion documental</h3>
                <p class="card-text">Requisitos, documentos y habilitacion de las propuestas radicadas.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'jurado' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Proyectos por asignar jurado"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'jurado' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'jurado' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#5D4037;"><font-awesome-icon icon="users" /></div>
                <h3 class="card-title">Asignar jurado</h3>
                <p class="card-text">Proyectos sin jurado designado. Plazo maximo de 7 dias habiles.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'asesor' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Proyectos por asignar asesor"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'asesor' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'asesor' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#2E7D32;"><font-awesome-icon icon="user" /></div>
                <h3 class="card-title">Asignar asesor</h3>
                <p class="card-text">Proyectos sin asesor designado, con candidatos del padron de la facultad.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'acto' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Proyectos por programar acto"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'acto' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'acto' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#C4A94D;"><font-awesome-icon icon="calendar" /></div>
                <h3 class="card-title">Programar actos</h3>
                <p class="card-text">Sustentaciones y socializaciones pendientes de programar o registrar.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PlazosCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Plazos y continuidad"
              @keydown.enter.prevent="$router.push({ name: 'PlazosCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'PlazosCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#C62828;"><font-awesome-icon icon="bell" /></div>
                <h3 class="card-title">Plazos y continuidad</h3>
                <p class="card-text">Vencimientos, actos programados y proyectos al limite de continuidad.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'FestivoView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Días no hábiles"
              @keydown.enter.prevent="$router.push({ name: 'FestivoView' })"
              @keydown.space.prevent="$router.push({ name: 'FestivoView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#C62828;"><font-awesome-icon icon="calendar" /></div>
                <h3 class="card-title">Días no hábiles</h3>
                <p class="card-text">Festivos y carga masiva CSV para el cálculo de plazos.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'ConvocatoriasPasantiaView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Convocatorias de pasantia"
              @keydown.enter.prevent="$router.push({ name: 'ConvocatoriasPasantiaView' })"
              @keydown.space.prevent="$router.push({ name: 'ConvocatoriasPasantiaView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#1565C0;"><font-awesome-icon icon="book" /></div>
                <h3 class="card-title">Convocatorias de pasantia</h3>
                <p class="card-text">Publicacion de plazas, postulaciones y seleccion de estudiantes.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'SolicitudesCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Solicitudes y novedades"
              @keydown.enter.prevent="$router.push({ name: 'SolicitudesCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'SolicitudesCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#6A1B9A;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Solicitudes y novedades</h3>
                <p class="card-text">Bandeja global de solicitudes pendientes y resueltas de los proyectos.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'AsesoresExternosCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Asesores externos"
              @keydown.enter.prevent="$router.push({ name: 'AsesoresExternosCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'AsesoresExternosCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#00838F;"><font-awesome-icon icon="users" /></div>
                <h3 class="card-title">Asesores externos</h3>
                <p class="card-text">Registro y verificacion de idoneidad de profesionales externos por facultad.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'publicacion' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Publicaciones pendientes"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'publicacion' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'publicacion' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#AD1457;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Publicacion de articulo</h3>
                <p class="card-text">Registro de DOI/URL y constancia de la modalidad de publicacion.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'certificacion' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Certificaciones pendientes"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'certificacion' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'certificacion' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#EF6C00;"><font-awesome-icon icon="user" /></div>
                <h3 class="card-title">Certificacion y cierre</h3>
                <p class="card-text">Diplomado y especializacion: certificacion de modulos y finalizacion.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView', query: { filtro: 'repositorio' } }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Repositorio pendiente"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'repositorio' } })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView', query: { filtro: 'repositorio' } })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#37474F;"><font-awesome-icon icon="book" /></div>
                <h3 class="card-title">Repositorio</h3>
                <p class="card-text">Carga del documento y derechos de autor (Acuerdo 025, art. 15).</p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Vista ESTUDIANTE: tarjetas por modalidad. -->

      <!-- Vista ADMIN o ROOT: gestion administrativa integral. -->
      <div v-else-if="esAdmin" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">Administración</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Acceso total: gestión académica, usuarios, catálogos, seguridad y monitoreo del sistema.
          </p>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Bandeja académica"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;"><font-awesome-icon icon="th-list" /></div>
                <h3 class="card-title">Bandeja académica</h3>
                <p class="card-text">Todas las propuestas y proyectos del sistema.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'JhiUser' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Gestión de usuarios"
              @keydown.enter.prevent="$router.push({ name: 'JhiUser' })"
              @keydown.space.prevent="$router.push({ name: 'JhiUser' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#6A1B9A;"><font-awesome-icon icon="user-cog" /></div>
                <h3 class="card-title">Usuarios y roles</h3>
                <p class="card-text">Crear usuarios, asignar autoridades, activar y resetear contraseñas.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'DecanoFacultadPage' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Decanos por facultad"
              @keydown.enter.prevent="$router.push({ name: 'DecanoFacultadPage' })"
              @keydown.space.prevent="$router.push({ name: 'DecanoFacultadPage' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#5D4037;"><font-awesome-icon icon="users" /></div>
                <h3 class="card-title">Decanos por facultad</h3>
                <p class="card-text">Asignar y cerrar vigencias de decanos.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PadronHabilitados' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Padrón de docentes"
              @keydown.enter.prevent="$router.push({ name: 'PadronHabilitados' })"
              @keydown.space.prevent="$router.push({ name: 'PadronHabilitados' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#C4A94D;"><font-awesome-icon icon="users" /></div>
                <h3 class="card-title">Docentes habilitados</h3>
                <p class="card-text">Habilitar docentes como asesor, jurado o ambos roles.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'RemisionPadron' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Remisión de padrón"
              @keydown.enter.prevent="$router.push({ name: 'RemisionPadron' })"
              @keydown.space.prevent="$router.push({ name: 'RemisionPadron' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#2E7D32;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Remisión de padrón</h3>
                <p class="card-text">Armar y enviar el padrón de la facultad al CIECYT.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'ProyectosDecanuraView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Proyectos de decanura"
              @keydown.enter.prevent="$router.push({ name: 'ProyectosDecanuraView' })"
              @keydown.space.prevent="$router.push({ name: 'ProyectosDecanuraView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#00838F;"><font-awesome-icon icon="th-list" /></div>
                <h3 class="card-title">Proyectos de decanura</h3>
                <p class="card-text">Designar asesor y jurado en las modalidades que corresponden.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'ConfiguracionAcademicaView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Configuración académica"
              @keydown.enter.prevent="$router.push({ name: 'ConfiguracionAcademicaView' })"
              @keydown.space.prevent="$router.push({ name: 'ConfiguracionAcademicaView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#1565C0;"><font-awesome-icon icon="book" /></div>
                <h3 class="card-title">Configuración académica</h3>
                <p class="card-text">Facultades, programas, modalidades, acuerdos, formatos y catálogos.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'RolesComponent' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Roles y permisos"
              @keydown.enter.prevent="$router.push({ name: 'RolesComponent' })"
              @keydown.space.prevent="$router.push({ name: 'RolesComponent' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#37474F;"><font-awesome-icon icon="user-cog" /></div>
                <h3 class="card-title">Roles y permisos</h3>
                <p class="card-text">Administrar roles y permisos por menú.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'Menu' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Menús y aplicaciones"
              @keydown.enter.prevent="$router.push({ name: 'Menu' })"
              @keydown.space.prevent="$router.push({ name: 'Menu' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#6A1B9A;"><font-awesome-icon icon="th-list" /></div>
                <h3 class="card-title">Menús y aplicaciones</h3>
                <p class="card-text">Administrar menús, URLs, iconos y visibilidad por rol.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'CicloView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Ciclos académicos"
              @keydown.enter.prevent="$router.push({ name: 'CicloView' })"
              @keydown.space.prevent="$router.push({ name: 'CicloView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#1565C0;"><font-awesome-icon icon="calendar" /></div>
                <h3 class="card-title">Ciclos académicos</h3>
                <p class="card-text">Técnico, Tecnológico, Profesional y Posgrado.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PeriodoView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Periodos académicos"
              @keydown.enter.prevent="$router.push({ name: 'PeriodoView' })"
              @keydown.space.prevent="$router.push({ name: 'PeriodoView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#00838F;"><font-awesome-icon icon="calendar" /></div>
                <h3 class="card-title">Periodos académicos</h3>
                <p class="card-text">Periodos usados por la remisión del padrón.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'JhiAuditsComponent' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Auditoría"
              @keydown.enter.prevent="$router.push({ name: 'JhiAuditsComponent' })"
              @keydown.space.prevent="$router.push({ name: 'JhiAuditsComponent' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#AD1457;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Auditoría</h3>
                <p class="card-text">Eventos de autenticación y cambios del sistema.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'JhiHealthComponent' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Salud del sistema"
              @keydown.enter.prevent="$router.push({ name: 'JhiHealthComponent' })"
              @keydown.space.prevent="$router.push({ name: 'JhiHealthComponent' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#2E7D32;"><font-awesome-icon icon="bell" /></div>
                <h3 class="card-title">Salud del sistema</h3>
                <p class="card-text">Estado de base de datos, disco, correo y servicios.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'JhiLogsComponent' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Logs"
              @keydown.enter.prevent="$router.push({ name: 'JhiLogsComponent' })"
              @keydown.space.prevent="$router.push({ name: 'JhiLogsComponent' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#EF6C00;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Logs</h3>
                <p class="card-text">Nivel de log y consulta de eventos de la aplicación.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'JhiMetricsComponent' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Métricas"
              @keydown.enter.prevent="$router.push({ name: 'JhiMetricsComponent' })"
              @keydown.space.prevent="$router.push({ name: 'JhiMetricsComponent' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#C62828;"><font-awesome-icon icon="th-list" /></div>
                <h3 class="card-title">Métricas</h3>
                <p class="card-text">Memoria, hilos, peticiones y métricas de la JVM.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'JhiConfigurationComponent' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Configuración de la aplicación"
              @keydown.enter.prevent="$router.push({ name: 'JhiConfigurationComponent' })"
              @keydown.space.prevent="$router.push({ name: 'JhiConfigurationComponent' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#455A64;"><font-awesome-icon icon="user-cog" /></div>
                <h3 class="card-title">Configuración de la app</h3>
                <p class="card-text">Propiedades activas de Spring y del sistema.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'ConvocatoriasPasantiaView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Convocatorias"
              @keydown.enter.prevent="$router.push({ name: 'ConvocatoriasPasantiaView' })"
              @keydown.space.prevent="$router.push({ name: 'ConvocatoriasPasantiaView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#1565C0;"><font-awesome-icon icon="book" /></div>
                <h3 class="card-title">Convocatorias de pasantía</h3>
                <p class="card-text">Publicación, postulaciones y selección.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'SolicitudesCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Solicitudes"
              @keydown.enter.prevent="$router.push({ name: 'SolicitudesCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'SolicitudesCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#6A1B9A;"><font-awesome-icon icon="file-alt" /></div>
                <h3 class="card-title">Solicitudes y novedades</h3>
                <p class="card-text">Consulta global y resolución de solicitudes.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PlazosCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Plazos"
              @keydown.enter.prevent="$router.push({ name: 'PlazosCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'PlazosCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#C62828;"><font-awesome-icon icon="bell" /></div>
                <h3 class="card-title">Plazos y continuidad</h3>
                <p class="card-text">Vencimientos, actos y continuidad.</p>
              </div>
            </div>
          </router-link>
        </div>

        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'AsesoresExternosCiecytView' }" class="text-decoration-none">
            <div class="card modalidad-card" role="button" tabindex="0" aria-label="Asesores externos"
              @keydown.enter.prevent="$router.push({ name: 'AsesoresExternosCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'AsesoresExternosCiecytView' })">
              <div class="card-body">
                <div class="card-icon" style="background-color:#00838F;"><font-awesome-icon icon="users" /></div>
                <h3 class="card-title">Asesores externos</h3>
                <p class="card-text">Registro y verificación de idoneidad.</p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Sin rol academico especifico ni admin: pagina neutra con instrucciones. -->
      <div v-else class="row">
        <div class="col-12">
          <div class="alert alert-info">
            <strong>Cuenta sin rol asignado.</strong>
            Si perteneces a la comunidad academica pero no ves las opciones que esperabas,
            contacta a CIECYT para que te asignen el rol correspondiente
            (Acuerdo 25, art. 5 y siguientes).
          </div>
        </div>
      </div>
    </div>

    <!-- Not authenticated -->
    <div v-if="!authenticated" class="text-center mt-5">
      <div class="alert alert-warning d-inline-block" style="border-radius:0.75rem;">
        <span v-text="$t('global.messages.info.authenticated.prefix')"></span>
        <a class="alert-link" v-on:click="openLogin()" v-text="$t('global.messages.info.authenticated.link')" style="cursor: pointer;"></a>
        <span v-html="$t('global.messages.info.authenticated.suffix')"></span>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts">
</script>
