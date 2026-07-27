/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import InvestigacionTipoComponent from '@/entities/investigacion-tipo/investigacion-tipo.vue';
import InvestigacionTipoClass from '@/entities/investigacion-tipo/investigacion-tipo.component';
import InvestigacionTipoService from '@/entities/investigacion-tipo/investigacion-tipo.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('InvestigacionTipo Management Component', () => {
    let wrapper: Wrapper<InvestigacionTipoClass>;
    let comp: InvestigacionTipoClass;
    let investigacionTipoServiceStub: SinonStubbedInstance<InvestigacionTipoService>;

    beforeEach(() => {
      investigacionTipoServiceStub = sinon.createStubInstance<InvestigacionTipoService>(InvestigacionTipoService);
      investigacionTipoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<InvestigacionTipoClass>(InvestigacionTipoComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          investigacionTipoService: () => investigacionTipoServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      investigacionTipoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      //comp.retrieveAllInvestigacionTipos();
      await comp.$nextTick();

      // THEN
      expect(investigacionTipoServiceStub.retrieve.called).toBeTruthy();
      //expect(comp.investigacionTipos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      investigacionTipoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeInvestigacionTipo();
      await comp.$nextTick();

      // THEN
      expect(investigacionTipoServiceStub.delete.called).toBeTruthy();
      expect(investigacionTipoServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
