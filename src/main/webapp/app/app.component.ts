import { Vue } from 'vue-facing-decorator';
import { Component } from 'vue-facing-decorator';
import Ribbon from '@/core/ribbon/ribbon.vue';
import JhiFooter from '@/core/jhi-footer/jhi-footer.vue';
import JhiNavbar from '@/core/jhi-navbar/jhi-navbar.vue';
import JhiNavbar2 from '@/core/jhi-navbar2/jhi-navbar2.vue';
import LoginForm from '@/account/login-form/login-form.vue';
import { loginModalVisible } from '@/account/login.service';

@Component({
  components: {
    ribbon: Ribbon,
    'jhi-navbar': JhiNavbar,
    'jhi-navbar2': JhiNavbar2,
    'login-form': LoginForm,

    'jhi-footer': JhiFooter,
  },
  setup() {
    return { loginModalVisible };
  },
})
export default class App extends Vue {
  get dismissCountDown(): number {
    return this.$store.getters.dismissCountDown;
  }

  get alertType(): string {
    return this.$store.getters.alertType;
  }

  get textoAlerta(): string {
    const mensaje = this.$store.getters.alertMessage;
    if (mensaje === null || mensaje === undefined || mensaje === '') {
      return '';
    }
    if (typeof mensaje === 'object') {
      return mensaje.msg !== undefined ? String(mensaje.msg) : JSON.stringify(mensaje);
    }
    return String(mensaje);
  }

  countDownChanged(dismissCountDown: number): void {
    this.$store.commit('countDownChanged', dismissCountDown);
  }
}
