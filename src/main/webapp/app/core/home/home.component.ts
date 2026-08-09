import Component from 'vue-class-component';
import { Inject, Vue } from 'vue-property-decorator';
import LoginService from '@/account/login.service';

@Component
export default class Home extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;

  public modalidades = [
    {
      id: 1,
      nombre: 'Tesis',
      descripcion: 'Investigación académica con desarrollo de prototipo o solución tecnológica.',
      icono: 'graduation-cap',
      color: '#003366',
      ruta: '/estudiante/listado-estudiante',
    },
    {
      id: 2,
      nombre: 'Pasantía Tecnológica',
      descripcion: 'Experiencia práctica en empresa con enfoque tecnológico.',
      icono: 'industry',
      color: '#2E7D32',
      ruta: '/estudiante/listado-estudiante',
    },
    {
      id: 3,
      nombre: 'Pasantía Investigativa',
      descripcion: 'Experiencia práctica en línea de investigación.',
      icono: 'flask',
      color: '#6A1B9A',
      ruta: '/estudiante/listado-estudiante',
    },
    {
      id: 4,
      nombre: 'Pasantía Internacional',
      descripcion: 'Experiencia práctica en institución internacional.',
      icono: 'globe',
      color: '#0277BD',
      ruta: '/estudiante/listado-estudiante',
    },
    {
      id: 5,
      nombre: 'Publicación de Artículo',
      descripcion: 'Redacción y publicación de artículo en revista indexada.',
      icono: 'newspaper',
      color: '#E65100',
      ruta: '/estudiante/listado-estudiante',
    },
    {
      id: 6,
      nombre: 'Diplomado',
      descripcion: 'Programa de profundización académica (sin flujo en sistema).',
      icono: 'award',
      color: '#5D4037',
      ruta: '/estudiante/listado-estudiante',
    },
  ];

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public navegarModalidad(modalidad: any): void {
    if (this.autoridades.includes('ROLE_ESTUDIANTE') || this.autoridades.includes('ROLE_JURADO')) {
      this.$router.push(modalidad.ruta);
    } else if (this.autoridades.includes('ROLE_ASESOR')) {
      this.$router.push('/viabilidad-propuesta/listado-asesor');
    } else if (this.autoridades.includes('ROLE_CIECYT')) {
      this.$router.push('/ciecyt/listado-ciecyt');
    } else if (this.autoridades.includes('ROLE_ADMIN')) {
      this.$router.push('/viabilidad-propuesta/listado-jurado');
    }
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }

  public get autoridades(): string[] {
    return this.$store.getters.account ? this.$store.getters.account.authorities : [];
  }
}
