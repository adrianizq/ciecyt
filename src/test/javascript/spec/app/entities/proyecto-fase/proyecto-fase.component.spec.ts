/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProyectoFaseComponent from '@/entities/proyecto-fase/proyecto-fase.vue';
import ProyectoFaseClass from '@/entities/proyecto-fase/proyecto-fase.component';
import ProyectoFaseService from '@/entities/proyecto-fase/proyecto-fase.service';

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
  describe('ProyectoFase Management Component', () => {
    let wrapper: Wrapper<ProyectoFaseClass>;
    let comp: ProyectoFaseClass;
    let proyectoFaseServiceStub: SinonStubbedInstance<ProyectoFaseService>;

    beforeEach(() => {
      proyectoFaseServiceStub = sinon.createStubInstance<ProyectoFaseService>(ProyectoFaseService);
      proyectoFaseServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProyectoFaseClass>(ProyectoFaseComponent, {
        store,
        i18n,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          alertService: () => new AlertService(store),
          proyectoFaseService: () => proyectoFaseServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      proyectoFaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProyectoFases();
      await comp.$nextTick();

      // THEN
      expect(proyectoFaseServiceStub.retrieve.called).toBeTruthy();
      expect(comp.proyectoFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      proyectoFaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(proyectoFaseServiceStub.retrieve.called).toBeTruthy();
      expect(comp.proyectoFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      proyectoFaseServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(proyectoFaseServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      proyectoFaseServiceStub.retrieve.reset();
      proyectoFaseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(proyectoFaseServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.proyectoFases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      proyectoFaseServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeProyectoFase();
      await comp.$nextTick();

      // THEN
      expect(proyectoFaseServiceStub.delete.called).toBeTruthy();
      expect(proyectoFaseServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
