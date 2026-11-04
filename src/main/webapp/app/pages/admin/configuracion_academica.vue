<template>
  <div>
    <div class="mb-4">
      <h2 class="mb-1">Configuración académica</h2>
      <p class="text-muted mb-0">
        Catálogos maestros que alimentan las propuestas, los proyectos y el flujo de opciones de grado.
      </p>
    </div>

    <div class="row g-3">
      <div v-for="tarjeta in tarjetas" :key="tarjeta.titulo" class="col-md-6 col-xl-4">
        <router-link
          :to="tarjeta.name ? { name: tarjeta.name } : tarjeta.path"
          class="text-decoration-none"
        >
          <div class="card h-100 modalidad-card" role="button" tabindex="0">
            <div class="card-body">
              <div class="card-icon" :style="{ backgroundColor: tarjeta.color }">
                <font-awesome-icon :icon="tarjeta.icono" />
              </div>
              <h3 class="card-title">{{ tarjeta.titulo }}</h3>
              <p class="card-text">{{ tarjeta.descripcion }}</p>
            </div>
          </div>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';

@Component
export default class ConfiguracionAcademica extends Vue {
  public tarjetas: any[] = [
    { name: 'Facultad', titulo: 'Facultades', descripcion: 'Facultades y su código institucional.', color: '#003366', icono: 'book' },
    { name: 'Programa', titulo: 'Programas', descripcion: 'Programas académicos por facultad y ciclo.', color: '#1565C0', icono: 'th-list' },
    { name: 'Modalidad', titulo: 'Modalidades', descripcion: 'Modalidades de opción de grado.', color: '#5D4037', icono: 'book' },
    { name: 'CicloView', titulo: 'Ciclos académicos', descripcion: 'Técnico, Tecnológico, Profesional y Posgrado.', color: '#2E7D32', icono: 'calendar' },
    { name: 'PeriodoView', titulo: 'Periodos académicos', descripcion: 'Periodos usados por la remisión del padrón.', color: '#00838F', icono: 'calendar' },
    { name: 'MetricasNegocioView', titulo: 'Métricas de negocio', descripcion: 'Usuarios, proyectos, estados y cargas de trabajo.', color: '#C62828', icono: 'th-list' },
    { name: 'ReportesAdminView', titulo: 'Reportes administrativos', descripcion: 'Exportaciones CSV de proyectos, usuarios y solicitudes.', color: '#455A64', icono: 'file-alt' },
    { name: 'FestivoView', titulo: 'Días no hábiles', descripcion: 'Festivos usados en el cálculo de plazos.', color: '#C62828', icono: 'calendar' },
    { name: 'Acuerdo', titulo: 'Acuerdos', descripcion: 'Acuerdos y normativa institucional.', color: '#AD1457', icono: 'file-alt' },
    { name: 'Formato', titulo: 'Formatos', descripcion: 'Formatos y documentos institucionales.', color: '#EF6C00', icono: 'file-alt' },
    { name: 'TipoPregunta', titulo: 'Tipos de pregunta', descripcion: 'Tipos usados en los formularios.', color: '#6A1B9A', icono: 'th-list' },
    { name: 'RolesModalidad', titulo: 'Roles de modalidad', descripcion: 'Roles, cantidad y autoridades por modalidad.', color: '#37474F', icono: 'users' },
    { name: 'Pregunta', titulo: 'Preguntas', descripcion: 'Preguntas y su relación con modalidades y roles.', color: '#00838F', icono: 'file-alt' },
    { name: 'LineaInvestigacion', titulo: 'Líneas de investigación', descripcion: 'Líneas y sublíneas de investigación.', color: '#C4A94D', icono: 'book' },
    { name: 'GrupoSemillero', titulo: 'Grupos y semilleros', descripcion: 'Grupos de investigación y semilleros.', color: '#C62828', icono: 'users' },
    { path: '/ciecyt/listado-ciecyt', titulo: 'Proyectos y propuestas', descripcion: 'Bandeja global para consultar y gestionar proyectos.', color: '#455A64', icono: 'th-list' },
    { name: 'InvestigacionTipo', titulo: 'Tipos de investigación', descripcion: 'Clasificación de la investigación.', color: '#795548', icono: 'book' },
    { name: 'CicloPropedeutico', titulo: 'Ciclo propedéutico', descripcion: 'Configuración del ciclo propedéutico.', color: '#546E7A', icono: 'calendar' },
  ];
}
</script>
