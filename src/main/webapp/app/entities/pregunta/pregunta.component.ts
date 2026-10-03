import { mixins } from 'vue-facing-decorator';

import { Component, Inject } from 'vue-facing-decorator';
import { IPregunta } from '@/shared/model/pregunta.model';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';
import FasesService from '../fases/fases.service';
import { IFases } from '@/shared/model/fases.model';
import PreguntaService from './pregunta.service';

@Component({})
export default class Pregunta extends mixins(AlertMixin) {
  //@Inject  private alertService: () => AlertService;
  @Inject private preguntaService: () => PreguntaService;
  @Inject private fasesService: () => FasesService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public preguntas: IPregunta[] = [];
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
      this.retrieveAllPreguntas();
    } else {
      const paginationQuery = {
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort(),
      };
      this.preguntaService()
        .retrieveSearchFase(this.searchFaseId, paginationQuery)
        .then(
          res => {
            this.preguntas = res.data;
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

  public mounted(): void {
    this.retrieveAllPreguntas();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllPreguntas();
  }

  public retrieveAllPreguntas(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.preguntaService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.preguntas = res.data;
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

  public prepareRemove(instance: IPregunta): void {
    this.removeId = instance.id;
  }

  public removePregunta(): void {
    this.preguntaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.pregunta.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllPreguntas();
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
    this.retrieveAllPreguntas();
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
