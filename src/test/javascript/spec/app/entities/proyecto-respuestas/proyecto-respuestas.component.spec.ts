/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProyectoRespuestasComponent from '@/entities/proyecto-respuestas/proyecto-respuestas.vue';
import ProyectoRespuestasClass from '@/entities/proyecto-respuestas/proyecto-respuestas.component';
import ProyectoRespuestasService from '@/entities/proyecto-respuestas/proyecto-respuestas.service';

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
  describe('ProyectoRespuestas Management Component', () => {
    let wrapper: Wrapper<ProyectoRespuestasClass>;
    let comp: ProyectoRespuestasClass;
    let proyectoRespuestasServiceStub: SinonStubbedInstance<ProyectoRespuestasService>;

    beforeEach(() => {
      proyectoRespuestasServiceStub = sinon.createStubInstance<ProyectoRespuestasService>(ProyectoRespuestasService);
      proyectoRespuestasServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProyectoRespuestasClass>(ProyectoRespuestasComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          proyectoRespuestasService: () => proyectoRespuestasServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      proyectoRespuestasServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProyectoRespuestass();
      await comp.$nextTick();

      // THEN
      expect(proyectoRespuestasServiceStub.retrieve.called).toBeTruthy();
      expect(comp.proyectoRespuestas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      proyectoRespuestasServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(proyectoRespuestasServiceStub.retrieve.called).toBeTruthy();
      expect(comp.proyectoRespuestas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      proyectoRespuestasServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(proyectoRespuestasServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      proyectoRespuestasServiceStub.retrieve.reset();
      proyectoRespuestasServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(proyectoRespuestasServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.proyectoRespuestas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      proyectoRespuestasServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeProyectoRespuestas();
      await comp.$nextTick();

      // THEN
      expect(proyectoRespuestasServiceStub.delete.called).toBeTruthy();
      expect(proyectoRespuestasServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
