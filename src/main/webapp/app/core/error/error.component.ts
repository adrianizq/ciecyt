import { Component, Hook } from 'vue-facing-decorator';
import { Vue, Inject } from 'vue-facing-decorator';
import LoginService from '@/account/login.service';

@Component
export default class Error extends Vue {
  @Inject
  private loginService: () => LoginService;
  errorMessage: string = null;
  error403 = false;
  error404 = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      let errorMessage = null;
      let error403 = false;
      let error404 = false;

      if (to.meta.errorMessage) {
        errorMessage = to.meta.errorMessage;
      }

      if (to.meta.error403) {
        error403 = to.meta.error403;
      }

      if (to.meta.error404) {
        error404 = to.meta.error404;
      }

      vm.init(errorMessage, error403, error404);
    });
  }

  public init(errorMessage: string = null, error403 = false, error404 = false) {
    this.errorMessage = errorMessage;
    this.error403 = error403;
    this.error404 = error404;

    // El titulo en la pestana debe comunicar el error, no quedar como
    // "CIECYT-ITP" generico. Asi el usuario sabe en cual ventana esta cuando
    // navega entre pestañas.
    if (error404) {
      document.title = this.$t('error.http.404') + ' | CIECYT-ITP';
    } else if (error403) {
      document.title = this.$t('error.http.403') + ' | CIECYT-ITP';
    }

    if (!this.$store.getters.authenticated && this.error403) {
      this.loginService().openLogin((<any>this).$root);
    }
  }
}
