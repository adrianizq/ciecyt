import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import UserManagementEdit from '@/admin/user-management/user-management-edit.vue';
import UserManagementEditClass from '@/admin/user-management/user-management-edit.component';
import UserManagementService from '@/admin/user-management/user-management.service';
import { createMemoryHistory, createRouter } from 'vue-router';

const localVue = createLocalVue();
const mockedAxios: any = axios;

const i18n = config.initI18N();
const store = config.initVueXStore();
localVue.component('font-awesome-icon', FontAwesomeIcon);
localVue.component('b-alert', {});

vi.mock('axios', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
  },
}));

describe('UserManagementEdit Component', () => {
  let wrapper: Wrapper<UserManagementEditClass>;
  let userManagementEdit: UserManagementEditClass;
  const userInfoServiceStub = {
    update: vi.fn(() => Promise.resolve({})),
    find: vi.fn(() => Promise.resolve({})),
  };

  beforeEach(() => {
    const router = createRouter({ history: createMemoryHistory(), routes: [] });
    wrapper = shallowMount<UserManagementEditClass>(UserManagementEdit, {
      store,
      router,
      i18n,
      localVue,
      provide: {
        alertService: () => new AlertService(store),
        userService: () => new UserManagementService(),
        userInfoService: () => userInfoServiceStub,
      },
    });
    userManagementEdit = wrapper.vm;
  });

  describe('init', () => {
    it('Should load user', async () => {
      // GIVEN
      mockedAxios.get.mockReturnValue(Promise.resolve({ data: { id: 123 } }));

      // WHEN
      userManagementEdit.init(123);
      await userManagementEdit.$nextTick();

      // THEN
      expect(mockedAxios.get).toHaveBeenCalledWith('api/users/' + 123);
    });
  });

  describe('initAuthorities', () => {
    it('Should load authorities', async () => {
      // GIVEN
      mockedAxios.get.mockReturnValue(Promise.resolve({ data: ['ROLE_ADMIN'] }));

      // WHEN
      userManagementEdit.initAuthorities();
      await userManagementEdit.$nextTick();

      // THEN
      expect(mockedAxios.get).toHaveBeenCalledWith(`api/users/authorities`);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing user', async () => {
      // GIVEN
      mockedAxios.put.mockReturnValue(
        Promise.resolve({ headers: { 'x-ciecytapp-alert': 'userManagement.updated', 'x-ciecytapp-params': '123' } })
      );
      userManagementEdit.userAccount = { id: 123, authorities: [] };

      // WHEN
      userManagementEdit.save();
      await userManagementEdit.$nextTick();

      // THEN
      expect(mockedAxios.put).toHaveBeenCalledWith(`api/users`, expect.objectContaining({ id: 123, authorities: ['ROLE_USER'] }));
      expect(userManagementEdit.isSaving).toEqual(false);
    });

    it('Should call create service on save for new user', async () => {
      // GIVEN
      mockedAxios.post.mockReturnValue(
        Promise.resolve({ headers: { 'x-ciecytapp-alert': 'userManagement.created', 'x-ciecytapp-params': '123' } })
      );
      userManagementEdit.userAccount = { authorities: [] };

      // WHEN
      userManagementEdit.save();
      await userManagementEdit.$nextTick();

      // THEN
      expect(mockedAxios.post).toHaveBeenCalledWith(`api/users`, expect.objectContaining({ authorities: ['ROLE_USER'] }));
      expect(userManagementEdit.isSaving).toEqual(false);
    });
  });
});
