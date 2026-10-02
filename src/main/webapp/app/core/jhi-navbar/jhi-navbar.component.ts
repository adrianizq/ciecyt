import { Component, Inject, Vue } from 'vue-facing-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';

import MenuService from '@/entities/menu/menu.service';
import { IMenu, MenuBar } from '@/shared/model/menu.model';

@Component
export default class JhiNavbar extends Vue {
  @Inject
  private loginService: () => LoginService;
  @Inject private translationService: () => TranslationService;

  @Inject private accountService: () => AccountService;

  @Inject
  private menuService: () => MenuService;

  ///public version = VERSION ? 'v' + VERSION : '';
  public version = 'Software para la Gestión de los Trabajos de Grado';
  private currentLanguage = this.$store.getters.currentLanguage;
  private languages: any = this.$store.getters.languages;
  public menus: MenuBar[] = [];
  public menusTodos: MenuBar[] = [];
  public roles: any = '';

  /*beforeCreate() {
    
    
  }*/
  async created() {
    await this.menuService()
      //trae todos los menus pero se usa porque sino no encuntra las autoridades
      .all()
      .then(res => {
        this.menusTodos = res;
      });
    // Con hasAnyAuthorityAndCheckAuth se espera a que la cuenta esté cargada en el store
    // (evita que el menú quede incompleto por la carrera con la carga de /api/account).
    this.roles = await this.rolesDelUsuario();
    if (this.roles) {
      await this.menuService()
        //trae todos los menus pero se usa porque sino no encuntra las autoridades
        .allRoles(this.roles)
        .then(res => {
          this.menus = res;

          console.log(this.roles);
        });
    } else {
      await this.menuService()
        //trae todos los menus pero se usa porque sino no encuntra las autoridades
        .allRoles('ANONYMOUS')
        .then(res => {
          this.menus = res;

          //console.log(this.roles);
        });
    }

    this.translationService().refreshTranslation(this.currentLanguage);
  }

  public async rolesDelUsuario(): Promise<string> {
    const rolesNombres = ['ROLE_ADMIN', 'ROLE_CIECYT', 'ROLE_DECANO', 'ROLE_JURADO', 'ROLE_ASESOR', 'ROLE_ESTUDIANTE'];
    let roles = '';
    for (const rol of rolesNombres) {
      const autorizado = await this.accountService().hasAnyAuthorityAndCheckAuth(rol);
      if (autorizado) {
        roles += rol.replace('ROLE_', '') + ',';
      }
    }
    return roles;
  }

  public subIsActive(input) {
    const paths = Array.isArray(input) ? input : [input];
    return paths.some(path => {
      return this.$route.path.indexOf(path) === 0; // current path starts with this path string
    });
  }

  public changeLanguage(newLanguage: string): void {
    this.translationService().refreshTranslation(newLanguage);
  }

  public isUrl(url: string): boolean {
    if (url.indexOf('()') === -1) {
      return true;
    }

    return false;
  }

  public actionMenu(callback: string): void {
    callback = callback.replace('()', '');
    this[callback]();
  }

  public isActiveLanguage(key: string): boolean {
    return key === this.$store.getters.currentLanguage;
  }

  public logout(): void {
    localStorage.removeItem('jhi-authenticationToken');
    sessionStorage.removeItem('jhi-authenticationToken');
    this.$store.commit('logout');
    // this.$router.push('/');
    window.location.href = '';
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }

  public get authenticated(): boolean {
    return this.$store.getters.authenticated;
  }

  public hasAnyAuthority(authorities: any): boolean {
    return this.accountService().hasAnyAuthority(authorities);
  }

  public get swaggerEnabled(): boolean {
    return this.$store.getters.activeProfiles.indexOf('swagger') > -1;
  }

  public get inProduction(): boolean {
    return this.$store.getters.activeProfiles.indexOf('prod') > -1;
  }
}
