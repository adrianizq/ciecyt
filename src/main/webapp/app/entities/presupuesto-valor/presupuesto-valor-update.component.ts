import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import RubroService from '../rubro/rubro.service';
import { IRubro } from '@/shared/model/rubro.model';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import EntidadService from '../entidad/entidad.service';
import { IEntidad } from '@/shared/model/entidad.model';

import AlertService from '@/shared/alert/alert.service';
import { IPresupuestoValor, PresupuestoValor } from '@/shared/model/presupuesto-valor.model';
import PresupuestoValorService from './presupuesto-valor.service';

const validations: any = {
  presupuestoValor: {
    descripcion: {},
    justificacion: {},
    cantidad: {},
    valorUnitario: {},
    especie: {},
    dinero: {},
    ordenVista: {},
  },
};

@Component({
  validations,
})
export default class PresupuestoValorUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('presupuestoValorService') private presupuestoValorService: () => PresupuestoValorService;
  public presupuestoValor: IPresupuestoValor = new PresupuestoValor();

  @Inject('rubroService') private rubroService: () => RubroService;

  public rubros: IRubro[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];

  @Inject('entidadService') private entidadService: () => EntidadService;

  public entidads: IEntidad[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.presupuestoValorId) {
        vm.retrievePresupuestoValor(to.params.presupuestoValorId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.presupuestoValor.id) {
      this.presupuestoValorService()
        .update(this.presupuestoValor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.presupuestoValor.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.presupuestoValorService()
        .create(this.presupuestoValor)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.presupuestoValor.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrievePresupuestoValor(presupuestoValorId): void {
    this.presupuestoValorService()
      .find(presupuestoValorId)
      .then(res => {
        this.presupuestoValor = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.rubroService()
      .retrieve()
      .then(res => {
        this.rubros = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
    this.entidadService()
      .retrieve()
      .then(res => {
        this.entidads = res.data;
      });
  }
}
