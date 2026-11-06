import { Component, Inject, Vue } from 'vue-facing-decorator';
import LoginService from '@/account/login.service';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

/**
 * Estados que ya cierran la propuesta de un estudiante. Se usan aqui para distinguir
 * las propuestas vigentes de las que ya terminaron y no cuentan para la regla de una
 * sola propuesta activa por ciclo y programa (Tabla de estados, EnumEstadoProyecto).
 */
const ESTADOS_CERRADOS = ['FINALIZADO', 'NOTA_DEFINITIVA', 'NO_VIABLE'];

@Component
export default class Home extends Vue {
  @Inject
  private loginService: () => LoginService;

  @Inject
  private proyectoService: () => ProyectoService;

  /** Propuestas del estudiante que aun no terminan. Se ofrecen para continuar/modificar. */
  public propuestasActivas: IProyecto[] = [];

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public mounted(): void {
    if (this.authenticated && this.esEstudiante) {
      this.cargarPropuestasActivas();
    }
  }

  /**
   * Trae las propuestas en las que el usuario figura como estudiante y deja solo las que
   * aun no terminan. Sirve para advertir que el Acuerdo 25 admite una sola propuesta
   * vigente por ciclo y programa (una doble titulacion puede tener una en cada programa).
   */
  public cargarPropuestasActivas(): void {
    const userid = this.userid;
    if (!userid) {
      return;
    }
    const paginationQuery = { page: 0, size: 50, sort: ['id,desc'] };
    this.proyectoService()
      .retrieveProyectoIntegranteAuthority(userid, 'ROLE_ESTUDIANTE', paginationQuery)
      .then(res => {
        const proyectos: IProyecto[] = Array.isArray(res.data) ? res.data : [];
        this.propuestasActivas = proyectos.filter(p => !ESTADOS_CERRADOS.includes(p.estado));
      })
      .catch(() => {
        this.propuestasActivas = [];
      });
  }

  /**
   * Ruta del asistente para retomar una propuesta que aun no es viable. Se arma por
   * modalidad para reapuntar al mismo wizard con el que se inicio el borrador
   * (Acuerdo 25, art. 5).
   */
  public rutaEdicion(proyecto: IProyecto): string {
    const modalidad = proyecto.proyectoModalidadModalidad || '';
    if (modalidad === 'Tesis') {
      return '/propuesta/informacion-general/' + proyecto.id;
    }
    if (modalidad.includes('Pasantia')) {
      return '/propuesta-pasantia/informacion-general-pasantia/' + proyecto.id;
    }
    if (modalidad === 'Diplomado') {
      return '/propuesta-diplomado/informacion-general-diplomado/' + proyecto.id;
    }
    return '/propuesta-nueva/informacion-general-nueva/' + proyecto.id;
  }

  public get userid(): string {
    const account = this.$store?.getters?.account;
    return account ? account.id : '';
  }

  public get authenticated(): boolean {
    return Boolean(this.$store?.getters?.authenticated);
  }

  public get username(): string {
    const account = this.$store?.getters?.account;
    return account ? account.login ?? '' : '';
  }

  public get autoridades(): string[] {
    const authorities = this.$store?.getters?.account?.authorities;
    return Array.isArray(authorities) ? authorities : [];
  }

  public tieneRol(rol: string): boolean {
    return this.autoridades.includes(rol);
  }

  public get esDecano(): boolean {
    return this.tieneRol('ROLE_DECANO');
  }

  public get esAsesor(): boolean {
    // Un jurado puede ser designado asesor de un proyecto; el listado solo muestra los
    // proyectos en los que realmente figura con el rol Asesor.
    return this.tieneRol('ROLE_ASESOR') || this.tieneRol('ROLE_JURADO');
  }

  public get esJurado(): boolean {
    return this.tieneRol('ROLE_JURADO');
  }

  public get esCiecyt(): boolean {
    return this.tieneRol('ROLE_CIECYT');
  }

  public get esAdmin(): boolean {
    return this.tieneRol('ROLE_ADMIN');
  }

  public get esEstudiante(): boolean {
    return this.tieneRol('ROLE_ESTUDIANTE');
  }
}
