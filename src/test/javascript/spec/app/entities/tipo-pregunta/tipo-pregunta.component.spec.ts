/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import TipoPreguntaComponent from '@/entities/tipo-pregunta/tipo-pregunta.vue';
import TipoPreguntaClass from '@/entities/tipo-pregunta/tipo-pregunta.component';
import TipoPreguntaService from '@/entities/tipo-pregunta/tipo-pregunta.service';

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
  describe('TipoPregunta Management Component', () => {
    let wrapper: Wrapper<TipoPreguntaClass>;
    let comp: TipoPreguntaClass;
    let tipoPreguntaServiceStub: SinonStubbedInstance<TipoPreguntaService>;

    beforeEach(() => {
      tipoPreguntaServiceStub = sinon.createStubInstance<TipoPreguntaService>(TipoPreguntaService);
      tipoPreguntaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TipoPreguntaClass>(TipoPreguntaComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          tipoPreguntaService: () => tipoPreguntaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      tipoPreguntaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTipoPreguntas();
      await comp.$nextTick();

      // THEN
      expect(tipoPreguntaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tipoPreguntas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      tipoPreguntaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(tipoPreguntaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.tipoPreguntas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      tipoPreguntaServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(tipoPreguntaServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      tipoPreguntaServiceStub.retrieve.reset();
      tipoPreguntaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(tipoPreguntaServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.tipoPreguntas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      tipoPreguntaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTipoPregunta();
      await comp.$nextTick();

      // THEN
      expect(tipoPreguntaServiceStub.delete.called).toBeTruthy();
      expect(tipoPreguntaServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
