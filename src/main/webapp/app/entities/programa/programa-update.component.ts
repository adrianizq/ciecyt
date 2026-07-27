import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

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
      ciclo:{},
      resolucion: {},
      titulo: {},
      duracionSemestres: {},
  },
};

@Component({
  validations,
})
export default class ProgramaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('programaService') private programaService: () => ProgramaService;
  public programa: IPrograma = new Programa();


  @Inject('facultadService') private facultadService: () => FacultadService;

  public facultads: IFacultad[] = [];
  public isSaving = false;

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
        });
    } else {
      this.programaService()
        .create(this.programa)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.programa.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
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
