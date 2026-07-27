import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPrograma } from '@/shared/model/programa.model';
import ProgramaService from './programa.service';

@Component
export default class ModalidadDetails extends Vue {
  @Inject('programaService') private programaService: () => ProgramaService;
  public programa: IPrograma = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.programaId) {
        vm.retrievePrograma(to.params.programaId);
      }
    });
  }

  public retrievePrograma(programaId) {
    this.programaService()
      .find(programaId)
      .then(res => {
        this.programa = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
