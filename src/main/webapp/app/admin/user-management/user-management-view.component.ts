import { Vue, Hook } from 'vue-facing-decorator';
import { Component, Inject } from 'vue-facing-decorator';
import UserManagementService from './user-management.service';
import UserInfoService from '@/entities/user-info/user-info.service';
import { formatDate as formatDateValue } from '@/shared/date/filters';

@Component
export default class JhiUserManagementView extends Vue {
  public formatDate(value: any): string {
    return formatDateValue(value);
  }

  @Inject({ from: 'userService' }) private userManagementService: () => UserManagementService;
  @Inject private userInfoService: () => UserInfoService;
  public user: any = null;
  public userInfo: any = null;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.userId) {
        vm.init(to.params.userId);
      }
    });
  }
  public async init(userId: number) {
    let res = await this.userManagementService()
      .get(userId)
      .then(res => {
        this.user = res.data;
      });

    res = await this.userInfoService()
      .find(this.user.id)
      .then(res => {
        this.userInfo = res;
      });
  }
}
