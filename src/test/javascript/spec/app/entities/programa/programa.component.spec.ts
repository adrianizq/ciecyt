/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProgramaComponent from '@/entities/programa/programa.vue';
import ProgramaClass from '@/entities/programa/programa.component';
import ProgramaService from '@/entities/programa/programa.service';

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
  describe('Programa Management Component', () => {
    let wrapper: Wrapper<ProgramaClass>;
    let comp: ProgramaClass;
    let programaServiceStub: SinonStubbedInstance<ProgramaService>;

    beforeEach(() => {
      programaServiceStub = sinon.createStubInstance<ProgramaService>(ProgramaService);
      programaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProgramaClass>(ProgramaComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          programaService: () => programaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      programaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProgramas();
      await comp.$nextTick();

      // THEN
      expect(programaServiceStub.retrieve.called).toBeTruthy();
      //expect(comp.programas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      programaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePrograma();
      await comp.$nextTick();

      // THEN
      expect(programaServiceStub.delete.called).toBeTruthy();
      expect(programaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
