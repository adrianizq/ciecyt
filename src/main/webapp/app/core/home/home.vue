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
          <router-link :to="{ name: 'PropuestasInvestigadorEditView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Iniciar nueva propuesta de grado"
              @keydown.enter.prevent="$router.push({ name: 'PropuestasInvestigadorEditView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestasInvestigadorEditView' })"
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

        <!-- Modality cards -->
        <div
          class="col-md-4 col-lg-4 mb-4"
          v-for="modalidad in modalidades"
          :key="modalidad.id"
        >
          <component
            :is="modalidad.componente"
            class="d-block text-decoration-none"
          >
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              :aria-label="modalidad.nombre + '. ' + modalidad.descripcion"
              @click="navegarModalidad(modalidad)"
              @keydown.enter.prevent="navegarModalidad(modalidad)"
              @keydown.space.prevent="navegarModalidad(modalidad)"
            >
              <div class="card-body">
                <div class="card-icon" :style="{ backgroundColor: modalidad.color }">
                  <font-awesome-icon :icon="modalidad.icono" />
                </div>
                <h3 class="card-title">{{ modalidad.nombre }}</h3>
                <p class="card-text">{{ modalidad.descripcion }}</p>
              </div>
            </div>
          </component>
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

      <!-- Vista CIECYT: vision general y designacion de jurado/asesor. Acuerdo 25 art. 9 par. 3 y art. 10. -->
      <div v-else-if="esCiecyt" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">CIECYT</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Centro de Investigaciones. Seguimiento general, designación de jurado y asesor.
          </p>
        </div>
        <div class="col-md-6 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir al listado general de propuestas"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;">
                  <font-awesome-icon icon="th-list" />
                </div>
                <h3 class="card-title">Listado general de propuestas</h3>
                <p class="card-text">
                  Todas las propuestas del sistema. Acá asigna jurado y asesor, gestiona continuidad
                  y plazos (Acuerdo 25, art. 5 y 9).
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-6 mb-4">
          <router-link :to="{ name: 'AsignarJuradoView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Asignar jurado a un proyecto"
              @keydown.enter.prevent="$router.push({ name: 'AsignarJuradoView' })"
              @keydown.space.prevent="$router.push({ name: 'AsignarJuradoView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#5D4037;">
                  <font-awesome-icon icon="users" />
                </div>
                <h3 class="card-title">Asignar jurado</h3>
                <p class="card-text">
                  Designación de jurado a un proyecto (Acuerdo 25, art. 9 par. 3 — plazo máximo 7
                  días hábiles).
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-6 mb-4">
          <router-link :to="{ name: 'AsignarAsesorView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Asignar asesor a un proyecto"
              @keydown.enter.prevent="$router.push({ name: 'AsignarAsesorView' })"
              @keydown.space.prevent="$router.push({ name: 'AsignarAsesorView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#2E7D32;">
                  <font-awesome-icon icon="user" />
                </div>
                <h3 class="card-title">Asignar asesor</h3>
                <p class="card-text">
                  Designación de asesor a un proyecto (Acuerdo 25, art. 8 par. 3).
                </p>
              </div>
            </div>
          </router-link>
        </div>
      </div>

      <!-- Vista ESTUDIANTE: tarjetas por modalidad. -->

      <!-- Vista ADMIN o ROOT: gestion administrativa con redireccion al listado general academico. -->
      <div v-else-if="esAdmin" class="row">
        <div class="col-12 mb-4">
          <h2 class="mb-0" style="color:#1a2332; font-weight:600;">Administración</h2>
          <p class="text-muted mb-0" style="font-size:0.9rem;">
            Acceso total: visión académica y herramientas del sistema.
          </p>
        </div>
        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PropuestaListadoCiecytView' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Listado general academico"
              @keydown.enter.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })"
              @keydown.space.prevent="$router.push({ name: 'PropuestaListadoCiecytView' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#003366;">
                  <font-awesome-icon icon="th-list" />
                </div>
                <h3 class="card-title">Listado académico</h3>
                <p class="card-text">
                  Todas las propuestas del sistema en una sola vista. Punto de entrada para gestión
                  académica.
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-4 mb-4">
          <router-link :to="{ name: 'PadronHabilitados' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Padrón de docentes habilitados"
              @keydown.enter.prevent="$router.push({ name: 'PadronHabilitados' })"
              @keydown.space.prevent="$router.push({ name: 'PadronHabilitados' })"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#C4A94D;">
                  <font-awesome-icon icon="users" />
                </div>
                <h3 class="card-title">Padrón de docentes</h3>
                <p class="card-text">
                  Pasa rol de decano: gestiona la lista de los que pueden ser asesores o jurados.
                </p>
              </div>
            </div>
          </router-link>
        </div>
        <div class="col-md-4 mb-4">
          <router-link :to="{ path: '/admin/user-management' }" class="text-decoration-none">
            <div
              class="card modalidad-card"
              role="button"
              tabindex="0"
              aria-label="Ir a gestion de usuarios"
              @keydown.enter.prevent="$router.push('/admin/user-management')"
              @keydown.space.prevent="$router.push('/admin/user-management')"
            >
              <div class="card-body">
                <div class="card-icon" style="background-color:#6A1B9A;">
                  <font-awesome-icon icon="user-cog" />
                </div>
                <h3 class="card-title">Gestión de usuarios</h3>
                <p class="card-text">
                  Crear, editar y asignar roles a usuarios; consultar permisos (solo admin del sistema).
                </p>
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
