import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
//import AlertService from '@/shared/alert/alert.service';
import AlertMixin from '@/shared/alert/alert.mixin';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Rol extends mixins(AlertMixin) {
  //@Inject('alertService') private alertService: () => AlertService;

  private ROLES = [
    'ROLE_ADMIN',
    'ROLE_USER',
    'ROLE_ASESOR',
    'ROLE_JURADO',
    'ROLE_DOCENTE',
    'ROLE_CIECYT',
    'ROLE_ESTUDIANTE',
    'ROLE_VIABILIDAD',
  ];

  public dismissCountDown: number = this.$store.getters.dismissCountDown;
  public dismissSecs: number = this.$store.getters.dismissSecs;
  public alertType: string = this.$store.getters.alertType;
  public alertMessage: any = this.$store.getters.alertMessage;

  public getAlertFromStore() {
    this.dismissCountDown = this.$store.getters.dismissCountDown;
    this.dismissSecs = this.$store.getters.dismissSecs;
    this.alertType = this.$store.getters.alertType;
    this.alertMessage = this.$store.getters.alertMessage;
  }

  public countDownChanged(dismissCountDown: number) {
    this.alertService().countDownChanged(dismissCountDown);
    this.getAlertFromStore();
  }
}
