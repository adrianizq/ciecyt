/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AdjuntoProyectoFaseComponent from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.vue';
import AdjuntoProyectoFaseClass from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.component';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';

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
  describe('AdjuntoProyectoFase Management Component', () => {
    let wrapper: Wrapper<AdjuntoProyectoFaseClass>;
    let comp: AdjuntoProyectoFaseClass;
    let adjuntoProyectoFaseServiceStub: SinonStubbedInstance<AdjuntoProyectoFaseService>;

    beforeEach(() => {
      adjuntoProyectoFaseServiceStub = sinon.createStubInstance<AdjuntoProyectoFaseService>(AdjuntoProyectoFaseService);
      adjuntoProyectoFaseServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AdjuntoProyectoFaseClass>(AdjuntoProyectoFaseComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          adjuntoProyectoFaseService: () => adjuntoProyectoFaseServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      adjuntoProyectoFaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAdjuntoProyectoFases();
      await comp.$nextTick();

      // THEN
      expect(adjuntoProyectoFaseServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adjuntoProyectoFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      adjuntoProyectoFaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(adjuntoProyectoFaseServiceStub.retrieve.called).toBeTruthy();
      expect(comp.adjuntoProyectoFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      adjuntoProyectoFaseServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(adjuntoProyectoFaseServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      adjuntoProyectoFaseServiceStub.retrieve.reset();
      adjuntoProyectoFaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(adjuntoProyectoFaseServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.adjuntoProyectoFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      adjuntoProyectoFaseServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAdjuntoProyectoFase();
      await comp.$nextTick();

      // THEN
      expect(adjuntoProyectoFaseServiceStub.delete.called).toBeTruthy();
      expect(adjuntoProyectoFaseServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
