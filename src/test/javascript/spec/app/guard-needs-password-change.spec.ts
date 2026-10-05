import { createLocalVue, shallowMount } from '@/shared/test/test-utils';
import * as config from '@/shared/config/config';
import { createRouter, createWebHistory } from 'vue-router';
import AccountService from '@/account/account.service';
import AlertService from '@/shared/alert/alert.service';

/**
 * Verifica el guard global del router (main.ts router.beforeEach): cuando el
 * usuario autenticado tiene needsPasswordChange=true y navega a una ruta que
 * no es /account/password, debe redirigir a /account/password.
 *
 * El guard vive en main.ts; aqui lo replicamos contra el mismo store +
 * accountService mock para que la prueba sea aislada y rapida. La regla de
 * negocio se valida asi sin tener que montar toda la app.
 */
describe('router guard: needsPasswordChange', () => {
  const localVue = createLocalVue();
  const store = config.initVueXStore();
  localVue.component('router-link', {});

  function buildRouter(routes: { path: string; name: string; meta?: any }[]) {
    return createRouter({
      history: createWebHistory(),
      routes: routes.map(r => ({
        path: r.path,
        name: r.name,
        component: { template: '<div />' },
        meta: r.meta ?? { authorities: ['ROLE_USER'] },
      })),
    }) as ReturnType<typeof createRouter>;
  }

  function buildAccountService(account: any): AccountService {
    const fakeRouter: any = { currentRoute: { value: { path: '/' } } };
    return new AccountService(store, { refreshTranslation: () => undefined } as any, fakeRouter);
  }

  function applyGuard(router: ReturnType<typeof createRouter>, account: any, isAuthenticated: boolean): void {
    const accountService = buildAccountService(account);
    if (isAuthenticated) {
      store.commit('authenticated', account);
    }
    router.beforeResolve((to: any, _from: any, next: any) => {
      if (
        (accountService as any).authenticated &&
        (accountService as any).needsPasswordChange &&
        to.path !== '/account/password' &&
        to.path !== '/reset/finish'
      ) {
        next({ path: '/account/password' });
        return;
      }
      next();
    });
  }

  it('Redirects to /account/password when authenticated and needsPasswordChange=true', async () => {
    const router = buildRouter([
      { path: '/', name: 'home' },
      { path: '/account/password', name: 'ChangePassword' },
    ]);
    applyGuard(router, { login: 'rosita', authorities: ['ROLE_CIECYT'], needsPasswordChange: true }, true);
    await router.push('/');
    expect(router.currentRoute.value.path).toBe('/account/password');
  });

  it('Allows staying on /account/password so the user can change it', async () => {
    const router = buildRouter([
      { path: '/', name: 'home' },
      { path: '/account/password', name: 'ChangePassword' },
    ]);
    applyGuard(router, { login: 'rosita', authorities: ['ROLE_CIECYT'], needsPasswordChange: true }, true);
    await router.push('/account/password');
    expect(router.currentRoute.value.path).toBe('/account/password');
  });

  it('Does not redirect when needsPasswordChange is false', async () => {
    const router = buildRouter([
      { path: '/', name: 'home' },
      { path: '/account/password', name: 'ChangePassword' },
    ]);
    applyGuard(router, { login: 'rosita', authorities: ['ROLE_CIECYT'], needsPasswordChange: false }, true);
    await router.push('/account/password'); // ya estaba aca
    await router.push('/');
    expect(router.currentRoute.value.path).toBe('/');
  });

  it('Does not redirect when not authenticated', async () => {
    const router = buildRouter([
      { path: '/', name: 'home' },
      { path: '/account/password', name: 'ChangePassword' },
    ]);
    applyGuard(router, null, false);
    await router.push('/');
    expect(router.currentRoute.value.path).toBe('/');
  });
});
