/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import InformacionPasantiaComponent from '@/entities/informacion-pasantia/informacion-pasantia.vue';
import InformacionPasantiaClass from '@/entities/informacion-pasantia/informacion-pasantia.component';
import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';

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
  describe('InformacionPasantia Management Component', () => {
    let wrapper: Wrapper<InformacionPasantiaClass>;
    let comp: InformacionPasantiaClass;
    let informacionPasantiaServiceStub: SinonStubbedInstance<InformacionPasantiaService>;

    beforeEach(() => {
      informacionPasantiaServiceStub = sinon.createStubInstance<InformacionPasantiaService>(InformacionPasantiaService);
      informacionPasantiaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<InformacionPasantiaClass>(InformacionPasantiaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          informacionPasantiaService: () => informacionPasantiaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      informacionPasantiaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllInformacionPasantias();
      await comp.$nextTick();

      // THEN
      expect(informacionPasantiaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.informacionPasantias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      informacionPasantiaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeInformacionPasantia();
      await comp.$nextTick();

      // THEN
      expect(informacionPasantiaServiceStub.delete.called).toBeTruthy();
      expect(informacionPasantiaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
