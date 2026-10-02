import { mixins } from 'vue-facing-decorator';

import { Component, Inject } from 'vue-facing-decorator';
import { ITipoPregunta } from '@/shared/model/tipo-pregunta.model';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

import TipoPreguntaService from './tipo-pregunta.service';

@Component({})
export default class TipoPregunta extends mixins(AlertMixin) {
  //@Inject  private alertService: () => AlertService;
  @Inject private tipoPreguntaService: () => TipoPreguntaService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public tipoPreguntas: ITipoPregunta[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public mounted(): void {
    this.retrieveAllTipoPreguntas();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllTipoPreguntas();
  }

  public retrieveAllTipoPreguntas(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.tipoPreguntaService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.tipoPreguntas = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: ITipoPregunta): void {
    this.removeId = instance.id;
  }

  public removeTipoPregunta(): void {
    this.tipoPreguntaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.tipoPregunta.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllTipoPreguntas();
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
    this.retrieveAllTipoPreguntas();
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
