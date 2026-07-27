import { Component, Vue, Inject } from 'vue-property-decorator';

import { IElementoProyecto } from '@/shared/model/elemento-proyecto.model';
import ElementoProyectoService from './elemento-proyecto.service';

@Component
export default class ElementoProyectoDetails extends Vue {
  @Inject('elementoProyectoService') private elementoProyectoService: () => ElementoProyectoService;
  public elementoProyecto: IElementoProyecto = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.elementoProyectoId) {
        vm.retrieveElementoProyecto(to.params.elementoProyectoId);
      }
    });
  }

  public retrieveElementoProyecto(elementoProyectoId) {
    this.elementoProyectoService()
      .find(elementoProyectoId)
      .then(res => {
        this.elementoProyecto = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
