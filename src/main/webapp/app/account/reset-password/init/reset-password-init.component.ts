import { useVuelidate } from '@vuelidate/core';
import { email, maxLength, minLength, required } from '@vuelidate/validators';
import axios from 'axios';
import { EMAIL_NOT_FOUND_TYPE } from '@/constants';
import { Vue, Component } from 'vue-facing-decorator';

const validations = {
  resetAccount: {
    email: {
      required,
      minLength: minLength(5),
      maxLength: maxLength(254),
      email,
    },
  },
};

interface ResetAccount {
  email: string;
}

@Component({
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class ResetPasswordInit extends Vue {
  public success: boolean = null;
  public error: string = null;
  public errorEmailNotExists: string = null;
  public resetAccount: ResetAccount = {
    email: null,
  };

  public requestReset(): void {
    this.errorEmailNotExists = null;
    this.error = null;
    axios
      .post('api/account/reset-password/init', this.resetAccount.email, {
        headers: {
          'content-type': 'text/plain',
        },
      })
      .then(() => {
        this.success = true;
      })
      .catch(error => {
        this.success = null;
        if (error.response.status === 400 && error.response.data.type === EMAIL_NOT_FOUND_TYPE) {
          this.errorEmailNotExists = 'ERROR';
        } else {
          this.error = 'ERROR';
        }
      });
  }

  public get authenticated(): boolean {
    return Boolean(this.$store?.getters?.authenticated);
  }
}
