/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProductoDetailComponent from '@/entities/producto/producto-details.vue';
import ProductoClass from '@/entities/producto/producto-details.component';
import ProductoService from '@/entities/producto/producto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Producto Management Detail Component', () => {
    let wrapper: Wrapper<ProductoClass>;
    let comp: ProductoClass;
    let productoServiceStub: SinonStubbedInstance<ProductoService>;

    beforeEach(() => {
      productoServiceStub = sinon.createStubInstance<ProductoService>(ProductoService);

      wrapper = shallowMount<ProductoClass>(ProductoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { productoService: () => productoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProducto = { id: 123 };
        productoServiceStub.find.resolves(foundProducto);

        // WHEN
        comp.retrieveProducto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.producto).toBe(foundProducto);
      });
    });
  });
});
