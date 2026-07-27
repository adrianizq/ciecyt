/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProductoProyectoUpdateComponent from '@/entities/producto-proyecto/producto-proyecto-update.vue';
import ProductoProyectoClass from '@/entities/producto-proyecto/producto-proyecto-update.component';
import ProductoProyectoService from '@/entities/producto-proyecto/producto-proyecto.service';

import ProductoService from '@/entities/producto/producto.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProductoProyecto Management Update Component', () => {
    let wrapper: Wrapper<ProductoProyectoClass>;
    let comp: ProductoProyectoClass;
    let productoProyectoServiceStub: SinonStubbedInstance<ProductoProyectoService>;

    beforeEach(() => {
      productoProyectoServiceStub = sinon.createStubInstance<ProductoProyectoService>(ProductoProyectoService);

      wrapper = shallowMount<ProductoProyectoClass>(ProductoProyectoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          productoProyectoService: () => productoProyectoServiceStub,

          productoService: () => new ProductoService(),

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.productoProyecto = entity;
        productoProyectoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productoProyectoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.productoProyecto = entity;
        productoProyectoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(productoProyectoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
