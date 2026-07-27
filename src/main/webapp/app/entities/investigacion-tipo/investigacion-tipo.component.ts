import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IInvestigacionTipo } from '@/shared/model/investigacion-tipo.model';
import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

import InvestigacionTipoService from './investigacion-tipo.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class InvestigacionTipo extends mixins(AlertMixin) {
  //@Inject('alertService') private alertService: () => AlertService;
  @Inject('investigacionTipoService') private investigacionTipoService: () => InvestigacionTipoService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public investigacionTips: IInvestigacionTipo[] = [];

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
    this.retrieveAllInvestigacionTips();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllInvestigacionTips();
  }

  public retrieveAllInvestigacionTips(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.investigacionTipoService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.investigacionTips = res.data;
          //console.log(this.investigacionTips);
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IInvestigacionTipo): void {
    this.removeId = instance.id;
  }

  public removeInvestigacionTipo(): void {
    this.investigacionTipoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.investigacionTipo.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllInvestigacionTips();
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
    this.retrieveAllInvestigacionTips();
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
