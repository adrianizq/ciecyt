import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

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
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class RolMenuUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private rolMenuService: () => RolMenuService;
  public rolMenu: IRolMenu = new RolMenu();

  @Inject private menuService: () => MenuService;

  public menus: IMenu[] = [];
  public isSaving = false;

  @Hook
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
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.rolMenuService()
        .create(this.rolMenu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rolMenu.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
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
