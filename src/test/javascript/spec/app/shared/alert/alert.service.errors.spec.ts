import { createLocalVue, Wrapper, shallowMount } from '@/shared/test/test-utils';
import * as config from '@/shared/config/config';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
const store = config.initVueXStore();

describe('Alert service error mapping (Sprint 4)', () => {
  let alertService: AlertService;

  beforeEach(() => {
    alertService = new AlertService(store);
  });

  function lastMessage() {
    return store.getters.alertMessage;
  }

  it('maps 401 (Axios) to friendly message', () => {
    alertService.showHttpError(null, { response: { status: 401, data: {} } });
    expect(lastMessage()).toMatch(/sesi[oó]n ha expirado/i);
  });

  it('maps 403 (Axios) to friendly message', () => {
    alertService.showHttpError(null, { response: { status: 403, data: {} } });
    expect(lastMessage()).toMatch(/permisos/i);
  });

  it('maps 404 (Axios) to friendly message', () => {
    alertService.showHttpError(null, { response: { status: 404, data: {} } });
    expect(lastMessage()).toMatch(/no est[áa] disponible/i);
  });

  it('maps 413 (Axios) to file size error', () => {
    alertService.showHttpError(null, { response: { status: 413, data: {} } });
    expect(lastMessage()).toMatch(/super[ae] el tama[ñn]o m[áa]ximo/i);
  });

  it('maps 422 (Axios) to validation error', () => {
    alertService.showHttpError(null, { response: { status: 422, data: {} } });
    expect(lastMessage()).toMatch(/datos enviados no son v[áa]lidos/i);
  });

  it('maps 500 (Axios) to server error', () => {
    alertService.showHttpError(null, { response: { status: 500, data: {} } });
    expect(lastMessage()).toMatch(/servidor/i);
  });

  it('maps network failure (status 0) to connection error', () => {
    alertService.showHttpError(null, { response: { status: 0, data: {} } });
    expect(lastMessage()).toMatch(/comunicar.*servidor/i);
  });

  it('uses backend detail when present and status is 4xx', () => {
    alertService.showHttpError(null, { response: { status: 400, data: { detail: 'Campo X es invalido' } } });
    expect(lastMessage()).toMatch(/Campo X es invalido/);
  });

  it('falls back to generic message when nothing is known', () => {
    alertService.showHttpError(null, { response: { status: 418, data: {} } });
    expect(typeof lastMessage()).toBe('string');
    expect(lastMessage().length).toBeGreaterThan(0);
  });
});
