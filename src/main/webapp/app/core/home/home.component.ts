import { Component, Inject, Vue } from 'vue-facing-decorator';
import LoginService from '@/account/login.service';

@Component
export default class Home extends Vue {
  @Inject
  private loginService: () => LoginService;

  /**
   * 7 modalidades de grado del Acuerdo 25, art. 4 y Tabla 1. Cada una
   * apunta al wizard correspondiente; el estudiante lo abre directamente. La
   * ruta del Acuerdo 25 art. 5 obliga a entregar documentos especificos
   * (configurados en shared/config/requisitos-modalidad.ts) según la modalidad.
   */
  public modalidades = [
    {
      id: 1,
      nombre: 'Tesis',
      descripcion:
        'Trabajo individual o colectivo que explora un tema bajo un nuevo enfoque o demuestra una hipótesis. Aplica a tecnólogo, profesional y posgrado (Acuerdo 25 art. 4 Tabla 1).',
      icono: 'graduation-cap',
      color: '#003366',
      ruta: '/propuesta/informacion-general/:proyectoId',
      componente: 'router-link',
    },
    {
      id: 2,
      nombre: 'Pasantía Tecnológica',
      descripcion:
        'Actividades prácticas en empresas o instituciones con enfoque tecnológico. Aplica solo a técnico profesional y tecnólogo (Acuerdo 25 art. 4).',
      icono: 'industry',
      color: '#2E7D32',
      ruta: '/propuesta-pasantia/informacion-general-pasantia',
      componente: 'router-link',
    },
    {
      id: 3,
      nombre: 'Pasantía Investigativa Profesional',
      descripcion: 'Pasantia con enfoque de investigación. Aplica solo al ciclo profesional (Acuerdo 25 art. 4).',
      icono: 'flask',
      color: '#6A1B9A',
      ruta: '/propuesta-pasantia/informacion-general-pasantia',
      componente: 'router-link',
    },
    {
      id: 4,
      nombre: 'Pasantía Internacional',
      descripcion: 'Pasantia con validacion en institucion anfitriona fuera del pais. Solo ciclo profesional (Acuerdo 25 art. 4).',
      icono: 'globe',
      color: '#0277BD',
      ruta: '/propuesta-pasantia/informacion-general-pasantia',
      componente: 'router-link',
    },
    {
      id: 5,
      nombre: 'Publicación de Artículo',
      descripcion:
        'Autor o coautor de artículo aceptado en revista indexada (Publindex u homologo). Tecnologo y profesional (Acuerdo 25 art. 4).',
      icono: 'newspaper',
      color: '#E65100',
      ruta: '/propuesta-nueva/propuestas-investigador',
      componente: 'router-link',
    },
    {
      id: 6,
      nombre: 'Diplomado de Profundización',
      descripcion: 'Programa de profundización académica como opción de grado. Solo técnico profesional y tecnólogo (Acuerdo 25 art. 4).',
      icono: 'award',
      color: '#5D4037',
      ruta: '/propuesta-diplomado/informacion-general-diplomado',
      componente: 'router-link',
    },
    {
      id: 7,
      nombre: 'Especialización',
      descripcion:
        'Primer semestre de una especialización de la institución cursado como opción de grado. Solo ciclo profesional (Acuerdo 25 art. 37).',
      icono: 'user-graduate',
      color: '#4527A0',
      ruta: '/propuesta-nueva/propuestas-investigador',
      componente: 'router-link',
    },
  ];

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public navegarModalidad(modalidad: any): void {
    // El componente padre del home (template) ya solo muestra las tarjetas
    // a los estudiantes. Los demas roles tienen su propio panel con enlaces
    // directos, por lo que esta funcion solo se invoca desde estudiantes.
    if (!modalidad.ruta) {
      return;
    }
    this.$router.push(modalidad.ruta);
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
    return this.tieneRol('ROLE_ASESOR');
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
