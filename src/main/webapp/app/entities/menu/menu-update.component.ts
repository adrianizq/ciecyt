import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';
import { IMenu, Menu } from '@/shared/model/menu.model';
import MenuService from './menu.service';
import Toggle from '@/components/toggle/toggle.vue';

const validations: any = {
  menu: {
    nombre: {},
    url: {},
    icono: {},
    activo: {},
    esPublico: {},
    orden: {},
  },
};

@Component({
  validations,
  components: {
    Toggle,
  },
})
export default class MenuUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('menuService') private menuService: () => MenuService;
  public menu: IMenu = new Menu();

  public menus: IMenu[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.menuId) {
        vm.retrieveMenu(to.params.menuId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.menu.id) {
      this.menuService()
        .update(this.menu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.menu.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.menuService()
        .create(this.menu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.menu.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveMenu(menuId): void {
    this.menuService()
      .find(menuId)
      .then(res => {
        this.menu = res;
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

  public changeEsPublico(value) {
    this.menu.esPublico = !value;
  }

  public getAlcance() {
    const esPublico = this.menu.esPublico ? false : true;
    console.log('aqui!!!' + esPublico);
    return esPublico;
  }
}
