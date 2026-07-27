import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMenu } from '@/shared/model/menu.model';
import MenuService from './menu.service';

@Component
export default class MenuDetails extends Vue {
  @Inject('menuService') private menuService: () => MenuService;
  public menu: IMenu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menuId) {
        vm.retrieveMenu(to.params.menuId);
      }
    });
  }

  public retrieveMenu(menuId) {
    this.menuService()
      .find(menuId)
      .then(res => {
        this.menu = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
