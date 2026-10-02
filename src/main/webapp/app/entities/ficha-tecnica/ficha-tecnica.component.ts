import { mixins } from 'vue-facing-decorator';

import { Component, Inject } from 'vue-facing-decorator';
import { IFichaTecnica } from '@/shared/model/ficha-tecnica.model';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

import FichaTecnicaService from './ficha-tecnica.service';

@Component({})
export default class FichaTecnica extends mixins(AlertMixin) {
  //@Inject  private alertService: () => AlertService;
  @Inject private fichaTecnicaService: () => FichaTecnicaService;
  private removeId: number = null;
  public itemsPerPage = 20;
  public queryCount: number = null;
  public page = 1;
  public previousPage = 1;
  public propOrder = 'id';
  public reverse = false;
  public totalItems = 0;
  public fichaTecnicas: IFichaTecnica[] = [];

  public isFetching = false;
  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public mounted(): void {
    this.retrieveAllFichaTecnicas();
  }

  public clear(): void {
    this.page = 1;
    this.retrieveAllFichaTecnicas();
  }

  public retrieveAllFichaTecnicas(): void {
    this.isFetching = true;

    const paginationQuery = {
      page: this.page - 1,
      size: this.itemsPerPage,
      sort: this.sort(),
    };
    this.fichaTecnicaService()
      .retrieve(paginationQuery)
      .then(
        res => {
          this.fichaTecnicas = res.data;
          this.totalItems = Number(res.headers['x-total-count']);
          this.queryCount = this.totalItems;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public prepareRemove(instance: IFichaTecnica): void {
    this.removeId = instance.id;
  }

  public removeFichaTecnica(): void {
    this.fichaTecnicaService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ciecytApp.fichaTecnica.deleted', { param: this.removeId });
        this.alertService().showAlert(message, 'danger');
        this.getAlertFromStore();

        this.removeId = null;
        this.retrieveAllFichaTecnicas();
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
    this.retrieveAllFichaTecnicas();
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
