import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import ModalidadService from '../modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';

import AlertService from '@/shared/alert/alert.service';
import { IRolesModalidad, RolesModalidad } from '@/shared/model/roles-modalidad.model';
import RolesModalidadService from './roles-modalidad.service';
import UserManagementService from '@/admin/user-management/user-management.service';
import { IUser, User } from '@/shared/model/user.model';

const validations: any = {
  rolesModalidad: {
    rol: {},
    cantidad: {},
    calificador: {},
  },
};

@Component({
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class RolesModalidadUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private rolesModalidadService: () => RolesModalidadService;
  public rolesModalidad: IRolesModalidad = new RolesModalidad();

  @Inject private modalidadService: () => ModalidadService;
  @Inject({ from: 'userService' }) private userManagementService: () => UserManagementService;

  public modalidads: IModalidad[] = [];
  public authorities: any[] = [];
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rolesModalidadId) {
        vm.retrieveRolesModalidad(to.params.rolesModalidadId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.rolesModalidad.id) {
      this.rolesModalidadService()
        .update(this.rolesModalidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rolesModalidad.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.rolesModalidadService()
        .create(this.rolesModalidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.rolesModalidad.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    }
  }

  public retrieveRolesModalidad(rolesModalidadId): void {
    this.rolesModalidadService()
      .find(rolesModalidadId)
      .then(res => {
        this.rolesModalidad = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });

    this.userManagementService()
      .retrieveAuthorities()
      .then(_res => {
        this.authorities = _res.data;
      });
  }
}
