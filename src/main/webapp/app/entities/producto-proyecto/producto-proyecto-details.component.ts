import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IProductoProyecto } from '@/shared/model/producto-proyecto.model';
import ProductoProyectoService from './producto-proyecto.service';

@Component
export default class ProductoProyectoDetails extends Vue {
  @Inject private productoProyectoService: () => ProductoProyectoService;
  public productoProyecto: IProductoProyecto = {};

  @Hook
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
    this.$router.push({ name: 'ProductoProyecto' });
  }
}
