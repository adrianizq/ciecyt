/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import GrupoSemilleroComponent from '@/entities/grupo-semillero/grupo-semillero.vue';
import GrupoSemilleroClass from '@/entities/grupo-semillero/grupo-semillero.component';
import GrupoSemilleroService from '@/entities/grupo-semillero/grupo-semillero.service';

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
  describe('GrupoSemillero Management Component', () => {
    let wrapper: Wrapper<GrupoSemilleroClass>;
    let comp: GrupoSemilleroClass;
    let grupoSemilleroServiceStub: SinonStubbedInstance<GrupoSemilleroService>;

    beforeEach(() => {
      grupoSemilleroServiceStub = sinon.createStubInstance<GrupoSemilleroService>(GrupoSemilleroService);
      grupoSemilleroServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GrupoSemilleroClass>(GrupoSemilleroComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          grupoSemilleroService: () => grupoSemilleroServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      grupoSemilleroServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGrupoSemilleros();
      await comp.$nextTick();

      // THEN
      expect(grupoSemilleroServiceStub.retrieve.called).toBeTruthy();
      expect(comp.grupoSemilleros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      grupoSemilleroServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(grupoSemilleroServiceStub.retrieve.called).toBeTruthy();
      expect(comp.grupoSemilleros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      grupoSemilleroServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(grupoSemilleroServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      grupoSemilleroServiceStub.retrieve.reset();
      grupoSemilleroServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(grupoSemilleroServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.grupoSemilleros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      grupoSemilleroServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeGrupoSemillero();
      await comp.$nextTick();

      // THEN
      expect(grupoSemilleroServiceStub.delete.called).toBeTruthy();
      expect(grupoSemilleroServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
