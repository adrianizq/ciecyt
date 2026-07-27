/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProductoProyectoDetailComponent from '@/entities/producto-proyecto/producto-proyecto-details.vue';
import ProductoProyectoClass from '@/entities/producto-proyecto/producto-proyecto-details.component';
import ProductoProyectoService from '@/entities/producto-proyecto/producto-proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProductoProyecto Management Detail Component', () => {
    let wrapper: Wrapper<ProductoProyectoClass>;
    let comp: ProductoProyectoClass;
    let productoProyectoServiceStub: SinonStubbedInstance<ProductoProyectoService>;

    beforeEach(() => {
      productoProyectoServiceStub = sinon.createStubInstance<ProductoProyectoService>(ProductoProyectoService);

      wrapper = shallowMount<ProductoProyectoClass>(ProductoProyectoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { productoProyectoService: () => productoProyectoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProductoProyecto = { id: 123 };
        productoProyectoServiceStub.find.resolves(foundProductoProyecto);

        // WHEN
        comp.retrieveProductoProyecto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.productoProyecto).toBe(foundProductoProyecto);
      });
    });
  });
});
