import { createLocalVue, shallowMount, Wrapper } from '@/shared/test/test-utils';
import Home from '@/core/home/home.vue';
import HomeClass from '@/core/home/home.component';
import * as config from '@/shared/config/config';

const localVue = createLocalVue();
const store = config.initVueXStore();
const i18n = config.initI18N();
localVue.component('router-link', {});

describe('Home', () => {
  let home: HomeClass;
  let wrapper: Wrapper<HomeClass>;
  let loginService: { openLogin: ReturnType<typeof vi.fn> };

  const STATE_INICIAL = {
    dismissSecs: 0,
    dismissCountDown: 0,
    alertType: '',
    alertMessage: {},
    logon: false,
    userIdentity: null,
    authenticated: false,
    ribbonOnProfiles: '',
    activeProfiles: '',
    currentLanguage: localStorage.getItem('currentLanguage') || 'es',
    languages: { en: { name: 'English' }, es: { name: 'Español' } },
    menu_lateral: [],
    menu_lateral_pasantia: [],
    menu_lateral_diplomado: [],
    menu_lateral_proyecto: [],
    menu_lateral_listado: [],
    menu_lateral_ciecyt: [],
    menu_lateral_nueva: [],
  } as const;

  // Cada test obtiene su propio mock y su propio componente, de modo que los
  // commits al store de un test no contaminen al siguiente (Home.vue invoca varios
  // getters que leen `account` durante el render).
  beforeEach(() => {
    store.replaceState({ ...STATE_INICIAL });
    loginService = { openLogin: vi.fn() };
    wrapper = shallowMount<HomeClass>(Home, {
      i18n,
      store,
      localVue,
      provide: {
        loginService: () => loginService,
      },
    });
    home = wrapper.vm;
  });

  it('should not have user data set', () => {
    expect(home.authenticated).toBeFalsy();
    expect(home.username).toBe('');
  });

  it('should have user data set after authentication', () => {
    store.commit('authenticated', { login: 'test' });

    expect(home.authenticated).toBeTruthy();
    expect(home.username).toBe('test');
  });

  it('should use login service', () => {
    home.openLogin();

    expect(loginService.openLogin).toHaveBeenCalled();
  });
});
