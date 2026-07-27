import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
import RolesModalidadService from './roles-modalidad.service';

@Component
export default class RolesModalidadDetails extends Vue {
  @Inject('rolesModalidadService') private rolesModalidadService: () => RolesModalidadService;
  public rolesModalidad: IRolesModalidad = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rolesModalidadId) {
        vm.retrieveRolesModalidad(to.params.rolesModalidadId);
      }
    });
  }

  public retrieveRolesModalidad(rolesModalidadId) {
    this.rolesModalidadService()
      .find(rolesModalidadId)
      .then(res => {
        this.rolesModalidad = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
