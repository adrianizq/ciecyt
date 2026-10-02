import { mixins } from 'vue-facing-decorator';

import { Component, Inject } from 'vue-facing-decorator';
import { IProyecto } from '@/shared/model/proyecto.model';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

import ProyectoService from './proyecto.service';

@Component({})
export default class Proyecto extends mixins(AlertMixin) {
  //@Inject  private alertService: () => AlertService;
  @Inject private proyectoService: () => ProyectoService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public proyectos: IProyecto[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public mounted(): void {
    this.retrieveAllProyectos();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllProyectos();
  }

  public retrieveAllProyectos(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.proyectoService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.proyectos = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IProyecto): void {
    this.removeId = instance.id;
  }

  public removeProyecto(): void {
    this.proyectoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.proyecto.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllProyectos();
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
    this.retrieveAllProyectos();
  }

  public changeOrder(propOrder): void {
    this.propOrder = propOrder;
    this.reverse = !this.reverse;
    this.transition();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
