import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ElementoService from '../elemento/elemento.service';
import { IElemento } from '@/shared/model/elemento.model';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IElementoProyecto, ElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import ElementoProyectoService from './elemento-proyecto.service';

const validations: any = {
  elementoProyecto: {
    dato: {},
    elementoProyectoProyectoDescripcion: {},
  },
};

@Component({
  validations,
})
export default class ElementoProyectoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('elementoProyectoService') private elementoProyectoService: () => ElementoProyectoService;
  public elementoProyecto: IElementoProyecto = new ElementoProyecto();

  @Inject('elementoService') private elementoService: () => ElementoService;

  public elementos: IElemento[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.elementoProyectoId) {
        vm.retrieveElementoProyecto(to.params.elementoProyectoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.elementoProyecto.id) {
      this.elementoProyectoService()
        .update(this.elementoProyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.elementoProyecto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.elementoProyectoService()
        .create(this.elementoProyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.elementoProyecto.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveElementoProyecto(elementoProyectoId): void {
    this.elementoProyectoService()
      .find(elementoProyectoId)
      .then(res => {
        this.elementoProyecto = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.elementoService()
      .retrieve()
      .then(res => {
        this.elementos = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
