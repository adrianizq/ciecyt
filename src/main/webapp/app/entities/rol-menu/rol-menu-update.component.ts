import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import MenuService from '../menu/menu.service';
import { IMenu } from '@/shared/model/menu.model';

import AlertService from '@/shared/alert/alert.service';
import { IRolMenu, RolMenu } from '@/shared/model/rol-menu.model';
import RolMenuService from './rol-menu.service';

const validations: any = {
  rolMenu: {
    permitirAcceso: {},
    permitirCrear: {},
    permitirEditar: {},
    permitirEliminar: {},
    authName: {},
  },
};

@Component({
  validations,
})
export default class RolMenuUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('rolMenuService') private rolMenuService: () => RolMenuService;
  public rolMenu: IRolMenu = new RolMenu();

  @Inject('menuService') private menuService: () => MenuService;

  public menus: IMenu[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rolMenuId) {
        vm.retrieveRolMenu(to.params.rolMenuId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.rolMenu.id) {
      this.rolMenuService()
        .update(this.rolMenu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rolMenu.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.rolMenuService()
        .create(this.rolMenu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rolMenu.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveRolMenu(rolMenuId): void {
    this.rolMenuService()
      .find(rolMenuId)
      .then(res => {
        this.rolMenu = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.menuService()
      .retrieve()
      .then(res => {
        this.menus = res.data;
      });
  }
}
