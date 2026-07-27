import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IElemento } from '@/shared/model/elemento.model';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';
import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';

import ElementoService from './elemento.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Elemento extends mixins(AlertMixin) {
  //@Inject('alertService') private alertService: () => AlertService;
  @Inject('elementoService') private elementoService: () => ElementoService;
  @Inject('fasesService') private fasesService: () => FasesService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public elementos: IElemento[] = [];
  public fases: IFases[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public searchFaseId: any;

  /////////recuperar datos desde la busqueda por codigo de licencia
  retrieveSearchFaseId() {
    //si la cadena es vacia recupera todas las licencias
    if (this.searchFaseId == null || this.searchFaseId.length == 0) {
      this.retrieveAllElementos();
    } else {
      const paginationQuery = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
      this.elementoService()
        .retrieveSearchFase(this.searchFaseId, paginationQuery)
        .then(
          res => {
            this.elementos = res.data;
            //console.log(this.licenses);
            this.totalItems = Number(res.headers['x-total-count']);
            this.queryCount = this.totalItems;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
            this.alertService().showHttpError(this, err.response);
          }
        );
    }
  }

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
    this.retrieveAllElementos();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllElementos();
  }

  public retrieveAllElementos(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.elementoService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.elementos = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );

    this.fasesService()
      .retrieve()
      .then(res => {
        this.fases = res.data;
      });
  }

  public prepareRemove(instance: IElemento): void {
    this.removeId = instance.id;
  }

  public removeElemento(): void {
    this.elementoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.elemento.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllElementos();
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
    this.retrieveAllElementos();
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
