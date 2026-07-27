import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import RolesModalidadService from '../roles-modalidad/roles-modalidad.service';
import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';

import AlertService from '@/shared/alert/alert.service';
import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import IntegranteProyectoService from './integrante-proyecto.service';

const validations: any = {
  integranteProyecto: {
    integrante: {},
    descripcion: {},
  },
};

@Component({
  validations,
})
export default class IntegranteProyectoUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;
  public integranteProyecto: IIntegranteProyecto = new IntegranteProyecto();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];

  @Inject('rolesModalidadService') private rolesModalidadService: () => RolesModalidadService;

  public rolesModalidads: IRolesModalidad[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.integranteProyectoId) {
        vm.retrieveIntegranteProyecto(to.params.integranteProyectoId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.integranteProyecto.id) {
      this.integranteProyectoService()
        .update(this.integranteProyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.integranteProyecto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.integranteProyectoService()
        .create(this.integranteProyecto)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.integranteProyecto.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveIntegranteProyecto(integranteProyectoId): void {
    this.integranteProyectoService()
      .find(integranteProyectoId)
      .then(res => {
        this.integranteProyecto = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
    this.rolesModalidadService()
      .retrieve()
      .then(res => {
        this.rolesModalidads = res.data;
      });
  }
}
