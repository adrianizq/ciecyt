/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CronogramaCiecytFasesComponent from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.vue';
import CronogramaCiecytFasesClass from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.component';
import CronogramaCiecytFasesService from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.service';

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
  describe('CronogramaCiecytFases Management Component', () => {
    let wrapper: Wrapper<CronogramaCiecytFasesClass>;
    let comp: CronogramaCiecytFasesClass;
    let cronogramaCiecytFasesServiceStub: SinonStubbedInstance<CronogramaCiecytFasesService>;

    beforeEach(() => {
      cronogramaCiecytFasesServiceStub = sinon.createStubInstance<CronogramaCiecytFasesService>(CronogramaCiecytFasesService);
      cronogramaCiecytFasesServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CronogramaCiecytFasesClass>(CronogramaCiecytFasesComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          cronogramaCiecytFasesService: () => cronogramaCiecytFasesServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      cronogramaCiecytFasesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCronogramaCiecytFasess();
      await comp.$nextTick();

      // THEN
      expect(cronogramaCiecytFasesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cronogramaCiecytFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      cronogramaCiecytFasesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(cronogramaCiecytFasesServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cronogramaCiecytFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      cronogramaCiecytFasesServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(cronogramaCiecytFasesServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      cronogramaCiecytFasesServiceStub.retrieve.reset();
      cronogramaCiecytFasesServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(cronogramaCiecytFasesServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.cronogramaCiecytFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      cronogramaCiecytFasesServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCronogramaCiecytFases();
      await comp.$nextTick();

      // THEN
      expect(cronogramaCiecytFasesServiceStub.delete.called).toBeTruthy();
      expect(cronogramaCiecytFasesServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
