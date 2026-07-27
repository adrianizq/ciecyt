import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProductoProyecto } from '@/shared/model/producto-proyecto.model';
import ProductoProyectoService from './producto-proyecto.service';

@Component
export default class ProductoProyectoDetails extends Vue {
  @Inject('productoProyectoService') private productoProyectoService: () => ProductoProyectoService;
  public productoProyecto: IProductoProyecto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productoProyectoId) {
        vm.retrieveProductoProyecto(to.params.productoProyectoId);
      }
    });
  }

  public retrieveProductoProyecto(productoProyectoId) {
    this.productoProyectoService()
      .find(productoProyectoId)
      .then(res => {
        this.productoProyecto = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
