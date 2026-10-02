import { useVuelidate } from '@vuelidate/core';
import { maxLength, minLength, required } from '@vuelidate/validators';
import axios from 'axios';
import { Component } from 'vue-facing-decorator';
import { Vue, Inject } from 'vue-facing-decorator';

const validations = {
  resetPassword: {
    currentPassword: {
      required,
    },
    newPassword: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(254),
    },
    confirmPassword: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(254),
    },
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
export default class ChangePassword extends Vue {
  success: string = null;
  error: string = null;
  doNotMatch: string = null;
  resetPassword: any = {
    currentPassword: null,
    newPassword: null,
    confirmPassword: null,
  };

  public changePassword(): void {
    if (this.resetPassword.newPassword !== this.resetPassword.confirmPassword) {
      this.error = null;
      this.success = null;
      this.doNotMatch = 'ERROR';
    } else {
      this.doNotMatch = null;
      axios
        .post('api/account/change-password', {
          currentPassword: this.resetPassword.currentPassword,
          newPassword: this.resetPassword.newPassword,
        })
        .then(() => {
          this.success = 'OK';
          this.error = null;
        })
        .catch(() => {
          this.success = null;
          this.error = 'ERROR';
        });
    }
  }

  public get account(): any {
    return this.$store.getters.account;
  }

  public get username(): string {
    return this.$store.getters.account ? this.$store.getters.account.login : '';
  }
}
