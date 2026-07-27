/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ElementoProyectoDetailComponent from '@/entities/elemento-proyecto/elemento-proyecto-details.vue';
import ElementoProyectoClass from '@/entities/elemento-proyecto/elemento-proyecto-details.component';
import ElementoProyectoService from '@/entities/elemento-proyecto/elemento-proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ElementoProyecto Management Detail Component', () => {
    let wrapper: Wrapper<ElementoProyectoClass>;
    let comp: ElementoProyectoClass;
    let elementoProyectoServiceStub: SinonStubbedInstance<ElementoProyectoService>;

    beforeEach(() => {
      elementoProyectoServiceStub = sinon.createStubInstance<ElementoProyectoService>(ElementoProyectoService);

      wrapper = shallowMount<ElementoProyectoClass>(ElementoProyectoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { elementoProyectoService: () => elementoProyectoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundElementoProyecto = { id: 123 };
        elementoProyectoServiceStub.find.resolves(foundElementoProyecto);

        // WHEN
        comp.retrieveElementoProyecto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.elementoProyecto).toBe(foundElementoProyecto);
      });
    });
  });
});
