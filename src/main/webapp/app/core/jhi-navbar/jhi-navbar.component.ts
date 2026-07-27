import { Component, Inject, Vue } from 'vue-property-decorator';
import { VERSION } from '@/constants';
import LoginService from '@/account/login.service';
import AccountService from '@/account/account.service';
import TranslationService from '@/locale/translation.service';

import MenuService from '@/entities/menu/menu.service';
import { IMenu, MenuBar } from '@/shared/model/menu.model';

@Component
export default class JhiNavbar extends Vue {
  @Inject('loginService')
  private loginService: () => LoginService;
  @Inject('translationService') private translationService: () => TranslationService;

  @Inject('accountService') private accountService: () => AccountService;

  @Inject('menuService')
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
        if (this.hasAnyAuthority('ROLE_ADMIN')) {
          this.roles += 'ADMIN,';
        }
        if (this.hasAnyAuthority('ROLE_CIECYT')) {
          this.roles += 'CIECYT,';
        }
        if (this.hasAnyAuthority('ROLE_JURADO')) {
          this.roles += 'JURADO,';
        }
        if (this.hasAnyAuthority('ROLE_ASESOR')) {
          this.roles += 'ASESOR,';
        }
        if (this.hasAnyAuthority('ROLE_VIABILIDAD')) {
          this.roles += 'VIABILIDAD,';
        }
        if (this.hasAnyAuthority('ROLE_ESTUDIANTE')) {
          this.roles += 'ESTUDIANTE,';
        }

        //this.roles = this.roles.trim();
        /*
         if(this.roles=""){
         this.roles="ANONYMOUS"
       }*/
        //this.roles=
        console.log(this.roles);
      });
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
