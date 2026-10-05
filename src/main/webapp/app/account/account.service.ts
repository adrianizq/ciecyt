import axios from 'axios';
import { Store } from 'vuex';
import type { Router } from 'vue-router';
import TranslationService from '@/locale/translation.service';

export default class AccountService {
  constructor(private store: Store<any>, private translationService: TranslationService, private router: Router) {
    this.init();
  }

  public init(): void {
    const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    if (!this.store.getters.account && !this.store.getters.logon && token) {
      this.retrieveAccount();
    }
    this.retrieveProfiles();
  }

  public retrieveProfiles(): void {
    axios.get('management/info').then(res => {
      if (res.data && res.data.activeProfiles) {
        this.store.commit('setRibbonOnProfiles', res.data['display-ribbon-on-profiles']);
        this.store.commit('setActiveProfiles', res.data['activeProfiles']);
      }
    });
  }

  public retrieveAccount(): void {
    this.store.commit('authenticate');
    axios
      .get('api/account')
      .then(response => {
        const account = response.data;
        if (account) {
          this.store.commit('authenticated', account);
          if (this.store.getters.currentLanguage !== account.langKey) {
            this.store.commit('currentLanguage', account.langKey);
          }
          if (sessionStorage.getItem('requested-url')) {
            this.router.replace(sessionStorage.getItem('requested-url')).catch(() => {});
            sessionStorage.removeItem('requested-url');
          }
        } else {
          this.store.commit('logout');
          this.volverALaPortada();
          sessionStorage.removeItem('requested-url');
        }
        this.translationService.refreshTranslation(this.store.getters.currentLanguage);
      })
      .catch(() => {
        this.store.commit('logout');
        this.avisarSesionExpirada();
        this.volverALaPortada();
      });
  }

  // El redirect silencioso anterior dejaba al usuario en la portada sin entender
  // que habia perdido el formulario que estaba diligenciando.
  private avisarSesionExpirada(): void {
    this.store.commit('setAlertType', 'warning');
    this.store.commit('setAlertMessage', 'Su sesión ha expirado. Vuelva a iniciar sesión para continuar.');
  }

  // vue-router rechaza la navegacion si la ruta destino ya es la actual; sin esta
  // guarda se genera un NavigationDuplicated sin manejar en cada carga con sesion caducada.
  private volverALaPortada(): void {
    if (this.router.currentRoute.value.path !== '/') {
      this.router.push('/');
    }
  }

  public hasAnyAuthority(authorities: any): boolean {
    if (typeof authorities === 'string') {
      authorities = [authorities];
    }
    if (!this.authenticated || !this.userAuthorities) {
      return false;
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.userAuthorities.includes(authorities[i])) {
        return true;
      }
    }

    return false;
  }

  public retrieveAccountP(): Promise<boolean> {
    return new Promise((resolve, reject) => {
      axios
        .get('api/account')
        .then(response => {
          this.store.commit('authenticate');
          const account = response.data;
          if (account) {
            this.store.commit('authenticated', account);
            if (this.store.getters.currentLanguage !== account.langKey) {
              this.store.commit('currentLanguage', account.langKey);
            }
            if (sessionStorage.getItem('requested-url')) {
              this.router.replace(sessionStorage.getItem('requested-url')).catch(() => {});
              sessionStorage.removeItem('requested-url');
            }
          } else {
            this.store.commit('logout');
            this.volverALaPortada();
            sessionStorage.removeItem('requested-url');
          }
          this.translationService.refreshTranslation(this.store.getters.currentLanguage);
          resolve(true);
        })
        .catch(() => {
          this.store.commit('logout');
          resolve(false);
        });
    });
  }

  public hasAnyAuthorityAndCheckAuth(authorities: any): Promise<boolean> {
    if (typeof authorities === 'string') {
      authorities = [authorities];
    }

    if (!this.authenticated || !this.userAuthorities) {
      const token = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
      if (!this.store.getters.account && !this.store.getters.logon && token) {
        return this.retrieveAccountP();
      } else {
        return new Promise(resolve => {
          resolve(false);
        });
      }
    }

    for (let i = 0; i < authorities.length; i++) {
      if (this.userAuthorities.includes(authorities[i])) {
        return new Promise(resolve => {
          resolve(true);
        });
      }
    }

    return new Promise(resolve => {
      resolve(false);
    });
  }

  public get accountIdentity(): any {
    return this.store.getters.account;
  }

  public get needsPasswordChange(): boolean {
    return Boolean((this.store.getters.account as any)?.needsPasswordChange);
  }

  public get authenticated(): boolean {
    return this.store.getters.authenticated;
  }

  public get userAuthorities(): any {
    return this.store.getters.account.authorities;
  }
}
