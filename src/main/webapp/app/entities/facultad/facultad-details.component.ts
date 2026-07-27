import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFacultad } from '@/shared/model/facultad.model';
import FacultadService from './facultad.service';

@Component
export default class FacultadDetails extends Vue {
  @Inject('facultadService') private facultadService: () => FacultadService;
  public facultad: IFacultad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.facultadId) {
        vm.retrieveFacultad(to.params.facultadId);
      }
    });
  }

  public retrieveFacultad(facultadId) {
    this.facultadService()
      .find(facultadId)
      .then(res => {
        this.facultad = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
