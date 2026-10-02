import { Component, Hook } from 'vue-facing-decorator';
import { Vue, Inject } from 'vue-facing-decorator';
import LoginService from '@/account/login.service';
import ActivateService from './activate.service';

@Component
export default class Activate extends Vue {
  @Inject
  private activateService: () => ActivateService;
  @Inject
  private loginService: () => LoginService;
  success = false;
  error = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.query.key) {
        vm.init(to.query.key);
      }
    });
  }

  public init(key: string): void {
    this.activateService()
      .activateAccount(key)
      .then(
        res => {
          this.success = true;
          this.error = false;
        },
        err => {
          this.error = true;
          this.success = false;
        }
      );
  }

  public openLogin(): void {
    this.loginService().openLogin((<any>this).$root);
  }
}
