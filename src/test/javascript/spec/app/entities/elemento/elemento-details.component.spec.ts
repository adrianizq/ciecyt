/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ElementoDetailComponent from '@/entities/elemento/elemento-details.vue';
import ElementoClass from '@/entities/elemento/elemento-details.component';
import ElementoService from '@/entities/elemento/elemento.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Elemento Management Detail Component', () => {
    let wrapper: Wrapper<ElementoClass>;
    let comp: ElementoClass;
    let elementoServiceStub: SinonStubbedInstance<ElementoService>;

    beforeEach(() => {
      elementoServiceStub = sinon.createStubInstance<ElementoService>(ElementoService);

      wrapper = shallowMount<ElementoClass>(ElementoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { elementoService: () => elementoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundElemento = { id: 123 };
        elementoServiceStub.find.resolves(foundElemento);

        // WHEN
        comp.retrieveElemento(123);
        await comp.$nextTick();

        // THEN
        expect(comp.elemento).toBe(foundElemento);
      });
    });
  });
});
