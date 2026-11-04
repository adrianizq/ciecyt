<template>
  <div>
    <div class="mb-4">
      <h2 class="mb-1">Reportes administrativos</h2>
      <p class="text-muted mb-0">Exportaciones CSV de los datos principales del sistema.</p>
    </div>

    <div class="row g-3">
      <div class="col-md-6 col-xl-4">
        <div class="card h-100">
          <div class="card-body">
            <h3 class="card-title">Proyectos</h3>
            <p class="card-text small text-muted">Proyectos con estado, modalidad, programa, facultad y ciclo.</p>
            <button type="button" class="btn btn-primary btn-sm" :disabled="cargando === 'proyectos'" @click="exportarProyectos">
              <font-awesome-icon icon="file-alt" :spin="cargando === 'proyectos'" /> Descargar CSV
            </button>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card h-100">
          <div class="card-body">
            <h3 class="card-title">Usuarios</h3>
            <p class="card-text small text-muted">Usuarios, correo, estado y roles asignados.</p>
            <button type="button" class="btn btn-primary btn-sm" :disabled="cargando === 'usuarios'" @click="exportarUsuarios">
              <font-awesome-icon icon="file-alt" :spin="cargando === 'usuarios'" /> Descargar CSV
            </button>
          </div>
        </div>
      </div>
      <div class="col-md-6 col-xl-4">
        <div class="card h-100">
          <div class="card-body">
            <h3 class="card-title">Solicitudes y novedades</h3>
            <p class="card-text small text-muted">Solicitudes con proyecto, integrante, estado y observación.</p>
            <button type="button" class="btn btn-primary btn-sm" :disabled="cargando === 'solicitudes'" @click="exportarSolicitudes">
              <font-awesome-icon icon="file-alt" :spin="cargando === 'solicitudes'" /> Descargar CSV
            </button>
          </div>
        </div>
      </div>
    </div>

    <div v-if="mensaje" class="alert alert-info mt-3">{{ mensaje }}</div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import SolicitudService from '@/entities/solicitud/solicitud.service';
import UserManagementService from '@/admin/user-management/user-management.service';
import { IProyecto } from '@/shared/model/proyecto.model';
import { ISolicitud } from '@/shared/model/solicitud.model';

@Component
export default class Reportes extends Vue {
  @Inject
  private proyectoService: () => ProyectoService;

  @Inject
  private solicitudService: () => SolicitudService;

  @Inject({ from: 'userService' })
  private userService: () => UserManagementService;

  @Inject
  private alertService: () => AlertService;

  public cargando: string | null = null;
  public mensaje = '';

  public exportarProyectos(): void {
    this.cargando = 'proyectos';
    this.mensaje = '';
    this.proyectoService()
      .retrieveAllProyectosIntegrantes()
      .then(res => {
        const proyectos: IProyecto[] = res.data || [];
        const filas: any[][] = [
          ['ID', 'Título', 'Estado', 'Modalidad', 'Programa', 'Facultad ID', 'Ciclo', 'Estudiantes'],
          ...proyectos.map(proyecto => [
            proyecto.id,
            proyecto.titulo,
            proyecto.estado,
            proyecto.proyectoModalidadModalidad,
            proyecto.programa,
            proyecto.facultadId,
            proyecto.ciclo,
            (proyecto.listaIntegrantesProyecto || [])
              .filter(integrante => integrante.integranteProyectoRolesModalidadRol === 'Estudiante')
              .map(integrante => this.nombreIntegrante(integrante))
              .join(', '),
          ]),
        ];
        this.descargarCsv('proyectos.csv', filas);
        this.mensaje = `Se exportaron ${proyectos.length} proyectos.`;
        this.cargando = null;
      })
      .catch(err => {
        this.cargando = null;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public exportarUsuarios(): void {
    this.cargando = 'usuarios';
    this.mensaje = '';
    this.userService()
      .retrieveAll()
      .then(res => {
        const usuarios: any[] = res.data || [];
        const filas: any[][] = [
          ['ID', 'Login', 'Nombres', 'Apellidos', 'Correo', 'Activo', 'Roles'],
          ...usuarios.map(usuario => [
            usuario.id,
            usuario.login,
            usuario.firstName,
            usuario.lastName,
            usuario.email,
            usuario.activated ? 'Sí' : 'No',
            (usuario.authorities || []).join(', '),
          ]),
        ];
        this.descargarCsv('usuarios.csv', filas);
        this.mensaje = `Se exportaron ${usuarios.length} usuarios.`;
        this.cargando = null;
      })
      .catch(err => {
        this.cargando = null;
        this.alertService().showHttpError(this, err.response);
      });
  }

  public exportarSolicitudes(): void {
    this.cargando = 'solicitudes';
    this.mensaje = '';
    this.solicitudService()
      .retrieve({ page: 0, size: 1000, sort: ['fechaSolicitud,desc'] })
      .then(res => {
        const solicitudes: ISolicitud[] = res.data || [];
        const filas: any[][] = [
          ['ID', 'Fecha', 'Asunto', 'Proyecto', 'Integrante', 'Estado', 'Texto'],
          ...solicitudes.map(solicitud => [
            solicitud.id,
            solicitud.fechaSolicitud,
            solicitud.asunto,
            solicitud.proyectoTitulo,
            solicitud.solicitudIntegranteProyectoIntegrante || solicitud.usuarioLogin,
            solicitud.estado ? 'Resuelta' : 'Pendiente',
            solicitud.textoSolicitud,
          ]),
        ];
        this.descargarCsv('solicitudes.csv', filas);
        this.mensaje = `Se exportaron ${solicitudes.length} solicitudes.`;
        this.cargando = null;
      })
      .catch(err => {
        this.cargando = null;
        this.alertService().showHttpError(this, err.response);
      });
  }

  private nombreIntegrante(integrante: any): string {
    const nombre = `${integrante.integranteProyectoUserFirstName || ''} ${integrante.integranteProyectoUserLastName || ''}`.trim();
    return nombre || integrante.integranteProyectoUserLogin || '';
  }

  private descargarCsv(nombre: string, filas: any[][]): void {
    const contenido = filas
      .map(fila => fila.map(valor => `"${String(valor == null ? '' : valor).replace(/"/g, '""')}"`).join(';'))
      .join('\r\n');
    const blob = new Blob(['\uFEFF' + contenido], { type: 'text/csv;charset=utf-8;' });
    const url = URL.createObjectURL(blob);
    const enlace = document.createElement('a');
    enlace.href = url;
    enlace.download = nombre;
    document.body.appendChild(enlace);
    enlace.click();
    document.body.removeChild(enlace);
    URL.revokeObjectURL(url);
  }
}
</script>
