import { Component, Inject, Vue, Watch } from 'vue-facing-decorator';
import AccountService from '@/account/account.service';
import NotificacionService from '@/shared/alert/notificacion.service';
import { INotificacion } from '@/shared/model/notificacion.model';

@Component
export default class NotificacionBell extends Vue {
  @Inject
  private notificacionService: () => NotificacionService;

  @Inject
  private accountService: () => AccountService;

  public notificacionesNoLeidas: INotificacion[] = [];
  public cargandoNotificaciones = false;
  private intervaloNotificaciones: any = null;

  public mounted(): void {
    this.cargarNotificaciones();
    this.intervaloNotificaciones = setInterval(() => this.cargarNotificaciones(), 30000);
  }

  public beforeUnmount(): void {
    if (this.intervaloNotificaciones) {
      clearInterval(this.intervaloNotificaciones);
      this.intervaloNotificaciones = null;
    }
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  @Watch('authenticated')
  public alCambiarAutenticacion(autenticado: boolean): void {
    if (autenticado) {
      this.cargarNotificaciones();
    } else {
      this.notificacionesNoLeidas = [];
    }
  }

  public async cargarNotificaciones(): Promise<void> {
    if (!this.authenticated) {
      this.notificacionesNoLeidas = [];
      return;
    }
    if (this.cargandoNotificaciones) {
      return;
    }
    this.cargandoNotificaciones = true;
    try {
      this.notificacionesNoLeidas = await this.notificacionService().findNoLeidas();
    } catch {
      // La campana no debe romper la navegacion si el endpoint falla.
    } finally {
      this.cargandoNotificaciones = false;
    }
  }

  public async abrirNotificacion(notificacion: INotificacion): Promise<void> {
    if (notificacion.id != null) {
      try {
        await this.notificacionService().marcarLeida(notificacion.id);
      } catch {
        // Se navega igual: la notificacion se reintentara en la proxima carga.
      }
      this.notificacionesNoLeidas = this.notificacionesNoLeidas.filter(item => item.id !== notificacion.id);
    }
    const ruta = this.rutaListaSegunRol();
    if (this.$route.path !== ruta) {
      this.$router.push(ruta).catch(() => {});
    }
  }

  public async marcarTodasLeidas(): Promise<void> {
    const pendientes = this.notificacionesNoLeidas.slice();
    await Promise.all(
      pendientes.map(notificacion =>
        notificacion.id != null
          ? this.notificacionService()
              .marcarLeida(notificacion.id)
              .catch(() => {})
          : Promise.resolve()
      )
    );
    this.notificacionesNoLeidas = [];
  }

  public formatearFecha(fecha?: Date): string {
    if (!fecha) {
      return '';
    }
    const valor = new Date(fecha);
    if (isNaN(valor.getTime())) {
      return '';
    }
    return valor.toLocaleString('es-CO', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  private rutaListaSegunRol(): string {
    if (this.accountService().hasAnyAuthority(['ROLE_CIECYT', 'ROLE_ADMIN'])) {
      return '/ciecyt/listado-ciecyt';
    }
    if (this.accountService().hasAnyAuthority('ROLE_ASESOR')) {
      return '/viabilidad-propuesta/listado-asesor';
    }
    if (this.accountService().hasAnyAuthority('ROLE_JURADO')) {
      return '/viabilidad-propuesta/listado-jurado';
    }
    if (this.accountService().hasAnyAuthority('ROLE_ESTUDIANTE')) {
      return '/estudiante/listado-estudiante';
    }
    return '/';
  }
}
