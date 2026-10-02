import axios from 'axios';
import { Component, Watch } from 'vue-facing-decorator';
import { Vue, Inject } from 'vue-facing-decorator';
import AccountService from '@/account/account.service';
import { hideLoginModal } from '@/account/login.service';

@Component
export default class LoginForm extends Vue {
  @Inject
  private accountService: () => AccountService;

  @Watch('$route')
  onRouteChange(): void {
    hideLoginModal();
  }

  public authenticationError = null;
  public login = null;
  public password = null;
  public rememberMe: boolean = null;

  public doLogin(): void {
    const data = { username: this.login, password: this.password, rememberMe: this.rememberMe };
    axios
      .post('api/authenticate', data)
      .then(result => {
        const bearerToken = result.headers.authorization;
        if (bearerToken && bearerToken.slice(0, 7) === 'Bearer ') {
          const jwt = bearerToken.slice(7, bearerToken.length);
          if (this.rememberMe) {
            localStorage.setItem('jhi-authenticationToken', jwt);
          } else {
            sessionStorage.setItem('jhi-authenticationToken', jwt);
          }
        }
        this.authenticationError = false;
        hideLoginModal();
        this.accountService().retrieveAccount();
        window.location.href = '';
      })
      .catch(() => {
        this.authenticationError = true;
      });
  }
}
