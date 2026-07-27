import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IMenu } from '@/shared/model/menu.model';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

import MenuService from './menu.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Menu extends mixins(AlertMixin) {
  //@Inject('alertService') private alertService: () => AlertService;
  @Inject('menuService') private menuService: () => MenuService;
  private removeId: number = null;
  private removeName: string = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public menus: IMenu[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }

  public mounted(): void {
    this.retrieveAllMenus();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllMenus();
  }

  public retrieveAllMenus(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.menuService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.menus = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IMenu): void {
    this.removeId = instance.id;
    this.removeName = instance.nombre;
  }

  public removeMenu(): void {
    this.menuService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.menu.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllMenus();
        this.closeDialog();
      });
  }

  public sort(): Array<any> {
    const result = [this.propOrder + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.propOrder !== 'id') {
      result.push('id');
    }
    return result;
  }

  public loadPage(page: number): void {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  public transition(): void {
    this.retrieveAllMenus();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }

  public setActive(menu, isActivated): void {
    menu.activo = isActivated;
    this.menuService()
      .update(menu)
      .then(() => {
        /*this.error = null;
        this.success = 'OK';
        this.loadAll();*/
      })
      .catch(() => {
        /*this.success = null;
        this.error = 'ERROR';*/
        menu.acivo = false;
      });
  }

  public setAlcance(menu, alcance): void {
    menu.esPublico = alcance;

    this.menuService()
      .update(menu)
      .then(() => {
        /*this.error = null;
        this.success = 'OK';
        this.loadAll();*/
      })
      .catch(() => {
        /*this.success = null;
        this.error = 'ERROR';*/
        menu.acivo = false;
      });
  }
}
