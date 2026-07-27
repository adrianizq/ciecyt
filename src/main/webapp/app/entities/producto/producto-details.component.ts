import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProducto } from '@/shared/model/producto.model';
import ProductoService from './producto.service';

@Component
export default class ProductoDetails extends Vue {
  @Inject('productoService') private productoService: () => ProductoService;
  public producto: IProducto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productoId) {
        vm.retrieveProducto(to.params.productoId);
      }
    });
  }

  public retrieveProducto(productoId) {
    this.productoService()
      .find(productoId)
      .then(res => {
        this.producto = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
