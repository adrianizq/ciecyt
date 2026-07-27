import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import ProductoService from '../producto/producto.service';
import { IProducto } from '@/shared/model/producto.model';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IProductoProyecto, ProductoProyecto } from '@/shared/model/producto-proyecto.model';
import ProductoProyectoService from './producto-proyecto.service';

const validations: any = {
  productoProyecto: {
    aplica: {},
    descripcion: {},
  },
};

@Component({
  validations,
})
export default class ProductoProyectoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('productoProyectoService') private productoProyectoService: () => ProductoProyectoService;
  public productoProyecto: IProductoProyecto = new ProductoProyecto();

  @Inject('productoService') private productoService: () => ProductoService;

  public productos: IProducto[] = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productoProyectoId) {
        vm.retrieveProductoProyecto(to.params.productoProyectoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.productoProyecto.id) {
      this.productoProyectoService()
        .update(this.productoProyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.productoProyecto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.productoProyectoService()
        .create(this.productoProyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.productoProyecto.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProductoProyecto(productoProyectoId): void {
    this.productoProyectoService()
      .find(productoProyectoId)
      .then(res => {
        this.productoProyecto = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productoService()
      .retrieve()
      .then(res => {
        this.productos = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
