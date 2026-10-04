import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import FacultadService from '../facultad/facultad.service';
import { IFacultad } from '@/shared/model/facultad.model';

import AlertService from '@/shared/alert/alert.service';
import { IPrograma, Programa } from '@/shared/model/programa.model';
import ProgramaService from './programa.service';

const validations: any = {
  programa: {
    programa: {},
    descripcion: {},
    codigoInterno: {},
    codigoSnies: {},
    creditos: {},
    ciclo: {},
    resolucion: {},
    titulo: {},
    duracionSemestres: {},
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
export default class ProgramaUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private programaService: () => ProgramaService;
  public programa: IPrograma = new Programa();

  @Inject private facultadService: () => FacultadService;

  public facultads: IFacultad[] = [];
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.programaId) {
        vm.retrievePrograma(to.params.programaId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.programa.id) {
      this.programaService()
        .update(this.programa)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.programa.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.programaService()
        .create(this.programa)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.programa.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    }
  }

  public retrievePrograma(programaId): void {
    this.programaService()
      .find(programaId)
      .then(res => {
        this.programa = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.facultadService()
      .retrieve()
      .then(res => {
        this.facultads = res.data;
      });
  }
}
