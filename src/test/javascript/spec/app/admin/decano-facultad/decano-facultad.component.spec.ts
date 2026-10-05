import { shallowMount, createLocalVue, Wrapper, flushPromises } from '@/shared/test/test-utils';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import DecanoFacultadPage from '@/admin/decano-facultad/decano-facultad.vue';
import DecanoFacultadPageClass from '@/admin/decano-facultad/decano-facultad.component';
import DecanoFacultadService from '@/entities/decano-facultad/decano-facultad.service';

const localVue = createLocalVue();
const mockedAxios: any = axios;

const i18n = config.initI18N();
const store = config.initVueXStore();
localVue.component('font-awesome-icon', FontAwesomeIcon);
localVue.component('b-alert', {});
localVue.component('b-modal', {});

vi.mock('axios', () => ({
  default: {
    get: vi.fn(),
    post: vi.fn(),
    put: vi.fn(),
    delete: vi.fn(),
  },
}));

async function mountPage(): Promise<Wrapper<DecanoFacultadPageClass>> {
  return shallowMount<DecanoFacultadPageClass>(DecanoFacultadPage, {
    i18n,
    store,
    localVue,
    stubs: {
      'b-button': true,
      'b-pagination': true,
      'jhi-item-count': true,
      'asignar-decano-dialog': true,
      'cerrar-vigencia-dialog': true,
    },
    provide: {
      alertService: () => new AlertService(store),
      decanoFacultadService: () => new DecanoFacultadService(),
    },
  });
}

describe('DecanoFacultadPage', () => {
  let wrapper: Wrapper<DecanoFacultadPageClass>;
  let page: DecanoFacultadPageClass;

  beforeEach(async () => {
    mockedAxios.get.mockReset();
    mockedAxios.post.mockReset();
    mockedAxios.put.mockReset();
    mockedAxios.delete.mockReset();

    // Default mock so the auto-mounted loadAll() does not crash when the
    // individual tests override it.
    mockedAxios.get.mockReturnValue(Promise.resolve({ data: [] }));

    wrapper = await mountPage();
    await flushPromises();
    page = wrapper.vm;
  });

  describe('loadAll', () => {
    it('Should request the decano list endpoint on init', async () => {
      expect(mockedAxios.get).toHaveBeenCalledWith('api/decanos-facultad');
    });

    it('Should sort vigentes first, then by faculty, then by fecha desde desc', async () => {
      mockedAxios.get.mockReturnValueOnce(
        Promise.resolve({
          data: [
            {
              id: 3,
              cargo: 'Decano',
              fechaDesde: '2024-01-01',
              fechaHasta: '2025-01-01',
              facultad: { id: 2, facultad: 'B' },
              user: { login: 'b-old', firstName: 'B', lastName: 'old' },
            },
            {
              id: 1,
              cargo: 'Decano',
              fechaDesde: '2026-01-01',
              fechaHasta: null,
              facultad: { id: 1, facultad: 'A' },
              user: { login: 'a-new', firstName: 'A', lastName: 'new' },
            },
            {
              id: 2,
              cargo: 'Decano',
              fechaDesde: '2025-06-01',
              fechaHasta: null,
              facultad: { id: 1, facultad: 'A' },
              user: { login: 'a-old', firstName: 'A', lastName: 'old' },
            },
          ],
        })
      );
      page.loadAll();
      await flushPromises();

      // Order: vigente A-2026-01 (id 1), vigente A-2025-06 (id 2), historic B-2024-01 (id 3)
      const sortedIds = (page as any).asignaciones.map((x: any) => x.id);
      expect(sortedIds).toEqual([1, 2, 3]);
    });
  });

  describe('delete', () => {
    it('Should call delete service then reload', async () => {
      mockedAxios.delete.mockReturnValue(Promise.resolve({}));

      (page as any).removeId = {
        id: 7,
        user: { login: '60503' },
        facultad: { id: 1, facultad: 'FTICS' },
      };
      page.deleteDecanoFacultad();
      await flushPromises();

      expect(mockedAxios.delete).toHaveBeenCalledWith('api/decanos-facultad/7');
    });
  });

  describe('dialog wiring', () => {
    it('Should expose openAsignarDialog and openCerrarDialog', () => {
      expect(typeof (page as any).openAsignarDialog).toBe('function');
      expect(typeof (page as any).openCerrarDialog).toBe('function');
    });
  });
});
