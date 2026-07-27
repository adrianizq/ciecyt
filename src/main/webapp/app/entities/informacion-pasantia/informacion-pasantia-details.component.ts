import { Component, Vue, Inject } from 'vue-property-decorator';

import { IInformacionPasantia } from '@/shared/model/informacion-pasantia.model';
import InformacionPasantiaService from './informacion-pasantia.service';

@Component
export default class InformacionPasantiaDetails extends Vue {
  @Inject('informacionPasantiaService') private informacionPasantiaService: () => InformacionPasantiaService;
  public informacionPasantia: IInformacionPasantia = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.informacionPasantiaId) {
        vm.retrieveInformacionPasantia(to.params.informacionPasantiaId);
      }
    });
  }

  public retrieveInformacionPasantia(informacionPasantiaId) {
    this.informacionPasantiaService()
      .find(informacionPasantiaId)
      .then(res => {
        this.informacionPasantia = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
