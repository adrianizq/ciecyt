import { email, maxLength, minLength, required } from 'vuelidate/lib/validators';
import { Component, Inject, Vue } from 'vue-property-decorator';
import UserManagementService from './user-management.service';
import { IUser, User } from '@/shared/model/user.model';
import UserInfoService from '@/entities/user-info/user-info.service';
import { IUserInfo, UserInfo } from '@/shared/model/user-info.model';
import AlertService from '@/shared/alert/alert.service';

function loginValidator(value) {
  if (typeof value === 'undefined' || value === null || value === '') {
    return true;
  }
  return /^[_.@A-Za-z0-9-]*$/.test(value);
}

const validations: any = {
  userAccount: {
    login: {
      required,
      minLength: minLength(1),
      maxLength: maxLength(254),
      loginValidator,
    },
    firstName: {
      maxLength: maxLength(50),
    },
    lastName: {
      maxLength: maxLength(50),
    },
    email: {
      required,
      email,
      minLength: minLength(5),
      maxLength: maxLength(254),
    },
  },
  userInfo: {
    nuip: {
      required,
      minLength: minLength(6),
      maxLength: maxLength(10),
    },
  },
};

@Component({
  validations,
})
export default class JhiUserManagementEdit extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('userService') private userManagementService: () => UserManagementService;
  @Inject('userInfoService') private userInfoService: () => UserInfoService;

  public userAccount: IUser;
  public isSaving = false;
  public authorities: any[] = [];
  // public userInfos: IUserInfo[] = [];
  public userInfo: IUserInfo = new UserInfo();
  public userId: any;
  public languages: any = this.$store.getters.languages;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initAuthorities();
      if (to.params.userId) {
        vm.init(to.params.userId);
      }
    });
  }

  public constructor() {
    super();
    this.userAccount = new User();
    this.userAccount.authorities = [];
  }

  public initAuthorities() {
    this.userManagementService()
      .retrieveAuthorities()
      .then(_res => {
        this.authorities = _res.data;

        var index = this.authorities.indexOf('ROLE_USER');
        if (index > -1) {
          this.authorities.splice(index, 1);
        }

        var index = this.authorities.indexOf('ROLE_ANONYMOUS');
        if (index > -1) {
          this.authorities.splice(index, 1);
        }

        var index = this.authorities.indexOf('ROLE_DOCENTE');
        if (index > -1) {
          this.authorities.splice(index, 1);
        }

        var index = this.authorities.indexOf('ROLE_VIABILIDAD');
        if (index > -1) {
          this.authorities.splice(index, 1);
        }

        var index = this.authorities.indexOf('ROLE_ASESOR');
        if (index > -1) {
          this.authorities.splice(index, 1);
        }
        // array = [2, 9]
        console.log(this.authorities);
      });
  }

  public async init(userId: number) {
    let res = await this.userManagementService()
      .get(userId)
      .then(res => {
        this.userAccount = res.data;
      });
    res = await this.userInfoService()
      .find(this.userAccount.id)
      .then(res => {
        this.userInfo = res;
      });
  }

  public previousState(): void {
    (<any>this).$router.go(-1);
  }

  public save(): void {
    this.isSaving = true;
    this.userAccount.userInfo = this.userInfo;
    this.userAccount.authorities.push('ROLE_USER');
    if (this.userAccount.authorities.includes('ROLE_JURADO')) {
      this.userAccount.authorities.push('ROLE_VIABILIDAD');
      this.userAccount.authorities.push('ROLE_ASESOR');
    }

    if (this.userAccount.id) {
      this.userManagementService()
        .update(this.userAccount)
        .then(res => {
          this.returnToList();
          this.alertService().showAlert(this.getMessageFromHeader(res), 'info');
        });
    } else {
      this.userManagementService()
        .create(this.userAccount)
        .then(res => {
          this.returnToList();
          this.alertService().showAlert(this.getMessageFromHeader(res), 'success');
        });
    }
  }

  private returnToList(): void {
    this.isSaving = false;
    (<any>this).$router.go(-1);
  }

  private getMessageFromHeader(res: any): any {
    return this.$t(res.headers['x-ciecytapp-alert'], { param: res.headers['x-ciecytapp-params'] });
  }
}
