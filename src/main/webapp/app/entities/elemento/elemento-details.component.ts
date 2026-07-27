import { Component, Vue, Inject } from 'vue-property-decorator';

import { IElemento } from '@/shared/model/elemento.model';
import ElementoService from './elemento.service';

@Component
export default class ElementoDetails extends Vue {
  @Inject('elementoService') private elementoService: () => ElementoService;
  public elemento: IElemento = {};
  public $t = null;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.elementoId) {
        vm.retrieveElemento(to.params.elementoId);
      }
    });
  }

  public retrieveElemento(elementoId) {
    this.elementoService()
      .find(elementoId)
      .then(res => {
        this.elemento = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
