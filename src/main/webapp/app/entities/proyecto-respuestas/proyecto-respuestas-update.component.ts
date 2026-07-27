import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import PreguntaService from '../pregunta/pregunta.service';
import { IPregunta } from '@/shared/model/pregunta.model';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IProyectoRespuestas, ProyectoRespuestas } from '@/shared/model/proyecto-respuestas.model';
import ProyectoRespuestasService from './proyecto-respuestas.service';

const validations: any = {
  proyectoRespuestas: {
    respuesta: {},
    observaciones: {},
    viable: {},
  },
};

@Component({
  validations,
})
export default class ProyectoRespuestasUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('proyectoRespuestasService') private proyectoRespuestasService: () => ProyectoRespuestasService;
  public proyectoRespuestas: IProyectoRespuestas = new ProyectoRespuestas();

  @Inject('preguntaService') private preguntaService: () => PreguntaService;

  public preguntas: IPregunta[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoRespuestasId) {
        vm.retrieveProyectoRespuestas(to.params.proyectoRespuestasId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.proyectoRespuestas.id) {
      this.proyectoRespuestasService()
        .update(this.proyectoRespuestas)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyectoRespuestas.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.proyectoRespuestasService()
        .create(this.proyectoRespuestas)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.proyectoRespuestas.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProyectoRespuestas(proyectoRespuestasId): void {
    this.proyectoRespuestasService()
      .find(proyectoRespuestasId)
      .then(res => {
        this.proyectoRespuestas = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.preguntaService()
      .retrieve()
      .then(res => {
        this.preguntas = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
