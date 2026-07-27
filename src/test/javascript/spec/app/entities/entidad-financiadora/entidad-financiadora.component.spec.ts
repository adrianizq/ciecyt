/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EntidadFinanciadoraComponent from '@/entities/entidad-financiadora/entidad-financiadora.vue';
import EntidadFinanciadoraClass from '@/entities/entidad-financiadora/entidad-financiadora.component';
import EntidadFinanciadoraService from '@/entities/entidad-financiadora/entidad-financiadora.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-alert', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
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
  describe('EntidadFinanciadora Management Component', () => {
    let wrapper: Wrapper<EntidadFinanciadoraClass>;
    let comp: EntidadFinanciadoraClass;
    let entidadFinanciadoraServiceStub: SinonStubbedInstance<EntidadFinanciadoraService>;

    beforeEach(() => {
      entidadFinanciadoraServiceStub = sinon.createStubInstance<EntidadFinanciadoraService>(EntidadFinanciadoraService);
      entidadFinanciadoraServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EntidadFinanciadoraClass>(EntidadFinanciadoraComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          entidadFinanciadoraService: () => entidadFinanciadoraServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      entidadFinanciadoraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEntidadFinanciadoras();
      await comp.$nextTick();

      // THEN
      expect(entidadFinanciadoraServiceStub.retrieve.called).toBeTruthy();
      expect(comp.entidadFinanciadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      entidadFinanciadoraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(entidadFinanciadoraServiceStub.retrieve.called).toBeTruthy();
      expect(comp.entidadFinanciadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      entidadFinanciadoraServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(entidadFinanciadoraServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      entidadFinanciadoraServiceStub.retrieve.reset();
      entidadFinanciadoraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(entidadFinanciadoraServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.entidadFinanciadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      entidadFinanciadoraServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEntidadFinanciadora();
      await comp.$nextTick();

      // THEN
      expect(entidadFinanciadoraServiceStub.delete.called).toBeTruthy();
      expect(entidadFinanciadoraServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
