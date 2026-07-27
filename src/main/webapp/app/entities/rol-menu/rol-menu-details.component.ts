import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRolMenu } from '@/shared/model/rol-menu.model';
import RolMenuService from './rol-menu.service';

@Component
export default class RolMenuDetails extends Vue {
  @Inject('rolMenuService') private rolMenuService: () => RolMenuService;
  public rolMenu: IRolMenu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rolMenuId) {
        vm.retrieveRolMenu(to.params.rolMenuId);
      }
    });
  }

  public retrieveRolMenu(rolMenuId) {
    this.rolMenuService()
      .find(rolMenuId)
      .then(res => {
        this.rolMenu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
