import { shallowMount, createLocalVue, Wrapper, flushPromises } from '@/shared/test/test-utils';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ResetPasswordDialog from '@/admin/user-management/reset-password-dialog.vue';
import ResetPasswordDialogClass from '@/admin/user-management/reset-password-dialog.component';
import UserManagementService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();
const mockedAxios: any = axios;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

const i18n = config.initI18N();
const store = config.initVueXStore();
localVue.component('font-awesome-icon', FontAwesomeIcon);
localVue.component('b-alert', {});
localVue.component('b-modal', bModalStub);

vi.mock('axios', () => ({
  default: {
    post: vi.fn(),
  },
}));

describe('ResetPasswordDialog', () => {
  let wrapper: Wrapper<ResetPasswordDialogClass>;
  let dialog: ResetPasswordDialogClass;

  beforeEach(() => {
    mockedAxios.post.mockReset();
    wrapper = shallowMount<ResetPasswordDialogClass>(ResetPasswordDialog, {
      i18n,
      store,
      localVue,
      stubs: {
        bModal: bModalStub as any,
      },
      provide: {
        alertService: () => new AlertService(store),
        userService: () => new UserManagementService(),
      },
    });
    dialog = wrapper.vm;
  });

  it('Should post to the admin reset endpoint and mark success', async () => {
    mockedAxios.post.mockReturnValue(Promise.resolve({ headers: {}, data: {} }));

    dialog.open('jane');
    dialog.targetLogin = 'jane';
    dialog.newPassword = 'una-clave-nueva-2026';
    dialog.confirmPassword = 'una-clave-nueva-2026';
    dialog.confirmReset();
    await flushPromises();

    expect(mockedAxios.post).toHaveBeenCalledWith('api/admin/users/jane/reset-password', {
      newPassword: 'una-clave-nueva-2026',
    });
    expect(dialog.success).toBe(true);
    expect(dialog.errorMessage).toBe('');
  });

  it('Should surface a mismatch as a warning and not call the API', async () => {
    dialog.targetLogin = 'jane';
    dialog.newPassword = 'una-clave-nueva-2026';
    dialog.confirmPassword = 'OTRA-clave-2026';

    dialog.confirmReset();
    await flushPromises();

    expect(dialog.doNotMatch).toBe(true);
    expect(mockedAxios.post).not.toHaveBeenCalled();
  });

  it('Should show an explicit error message on backend failure', async () => {
    mockedAxios.post.mockReturnValue(Promise.reject({ response: { status: 404, data: { title: 'no existe' } } }));

    dialog.targetLogin = 'fantasma';
    dialog.newPassword = 'otra-clave-2026';
    dialog.confirmPassword = 'otra-clave-2026';
    dialog.confirmReset();
    await flushPromises();

    expect(dialog.success).toBe(false);
    expect(dialog.isSaving).toBe(false);
    expect(dialog.errorMessage.length).toBeGreaterThan(0);
  });

  it('Should not call the API when the new password is too short', async () => {
    dialog.targetLogin = 'jane';
    dialog.newPassword = 'abc';
    dialog.confirmPassword = 'abc';

    dialog.confirmReset();
    await flushPromises();

    expect(dialog.errorMessage.length).toBeGreaterThan(0);
    expect(mockedAxios.post).not.toHaveBeenCalled();
  });
});
