import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IFacultad, Facultad } from '@/shared/model/facultad.model';
import FacultadService from './facultad.service';

const validations: any = {
  facultad: {
    codigoFacultad: {},
    facultad: {},
  },
};

@Component({
  validations,
})
export default class FacultadUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('facultadService') private facultadService: () => FacultadService;
  public facultad: IFacultad = new Facultad();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.facultadId) {
        vm.retrieveFacultad(to.params.facultadId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.facultad.id) {
      this.facultadService()
        .update(this.facultad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.facultad.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.facultadService()
        .create(this.facultad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.facultad.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveFacultad(facultadId): void {
    this.facultadService()
      .find(facultadId)
      .then(res => {
        this.facultad = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
