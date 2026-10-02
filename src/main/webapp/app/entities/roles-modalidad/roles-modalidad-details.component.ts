import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
import RolesModalidadService from './roles-modalidad.service';

@Component
export default class RolesModalidadDetails extends Vue {
  @Inject private rolesModalidadService: () => RolesModalidadService;
  public rolesModalidad: IRolesModalidad = {};

  @Hook
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
