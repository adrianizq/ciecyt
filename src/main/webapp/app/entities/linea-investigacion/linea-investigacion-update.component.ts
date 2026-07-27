import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProgramaService from '../programa/programa.service';
import { IPrograma } from '@/shared/model/programa.model';

import AlertService from '@/shared/alert/alert.service';
import { ILineaInvestigacion, LineaInvestigacion } from '@/shared/model/linea-investigacion.model';
import LineaInvestigacionService from './linea-investigacion.service';

const validations: any = {
  lineaInvestigacion: {
    linea: {},
    codigoLinea: {},
  },
};

@Component({
  validations,
})
export default class LineaInvestigacionUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('lineaInvestigacionService') private lineaInvestigacionService: () => LineaInvestigacionService;
  public lineaInvestigacion: ILineaInvestigacion = new LineaInvestigacion();

  public lineaInvestigacions: ILineaInvestigacion[] = [];

  @Inject('programaService') private programaService: () => ProgramaService;

  public programas: IPrograma[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.lineaInvestigacionId) {
        vm.retrieveLineaInvestigacion(to.params.lineaInvestigacionId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.lineaInvestigacion.id) {
      this.lineaInvestigacionService()
        .update(this.lineaInvestigacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.lineaInvestigacion.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.lineaInvestigacionService()
        .create(this.lineaInvestigacion)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.lineaInvestigacion.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveLineaInvestigacion(lineaInvestigacionId): void {
    this.lineaInvestigacionService()
      .find(lineaInvestigacionId)
      .then(res => {
        this.lineaInvestigacion = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.lineaInvestigacionService()
      .retrieve()
      .then(res => {
        this.lineaInvestigacions = res.data;
      });
    this.programaService()
      .retrieve()
      .then(res => {
        this.programas = res.data;
      });
  }
}
