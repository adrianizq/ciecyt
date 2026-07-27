/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ElementoProyectoUpdateComponent from '@/entities/elemento-proyecto/elemento-proyecto-update.vue';
import ElementoProyectoClass from '@/entities/elemento-proyecto/elemento-proyecto-update.component';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';

import ElementoService from '@/entities/elemento/elemento.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ElementoProyecto Management Update Component', () => {
    let wrapper: Wrapper<ElementoProyectoClass>;
    let comp: ElementoProyectoClass;
    let elementoProyectoServiceStub: SinonStubbedInstance<ElementoProyectoService>;

    beforeEach(() => {
      elementoProyectoServiceStub = sinon.createStubInstance<ElementoProyectoService>(ElementoProyectoService);

      wrapper = shallowMount<ElementoProyectoClass>(ElementoProyectoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          elementoProyectoService: () => elementoProyectoServiceStub,

          elementoService: () => new ElementoService(),

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.elementoProyecto = entity;
        elementoProyectoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(elementoProyectoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.elementoProyecto = entity;
        elementoProyectoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(elementoProyectoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
