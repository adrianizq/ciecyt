import { mixins } from 'vue-facing-decorator';

import { Component, Inject } from 'vue-facing-decorator';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

@Component({})
export default class Rol extends mixins(AlertMixin) {
  //@Inject  private alertService: () => AlertService;

  private ROLES = ['ROLE_ADMIN', 'ROLE_USER', 'ROLE_ASESOR', 'ROLE_JURADO', 'ROLE_DOCENTE', 'ROLE_CIECYT', 'ROLE_ESTUDIANTE'];

  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;
}
