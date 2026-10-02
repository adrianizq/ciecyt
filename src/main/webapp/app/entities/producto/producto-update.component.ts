import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import AlertService from '@/shared/alert/alert.service';
import { IProducto, Producto } from '@/shared/model/producto.model';
import ProductoService from './producto.service';

const validations: any = {
  producto: {
    producto: {},
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
export default class ProductoUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private productoService: () => ProductoService;
  public producto: IProducto = new Producto();
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productoId) {
        vm.retrieveProducto(to.params.productoId);
      }
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.producto.id) {
      this.productoService()
        .update(this.producto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.producto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.productoService()
        .create(this.producto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.producto.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveProducto(productoId): void {
    this.productoService()
      .find(productoId)
      .then(res => {
        this.producto = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
