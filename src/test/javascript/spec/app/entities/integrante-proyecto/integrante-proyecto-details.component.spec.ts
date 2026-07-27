/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import IntegranteProyectoDetailComponent from '@/entities/integrante-proyecto/integrante-proyecto-details.vue';
import IntegranteProyectoClass from '@/entities/integrante-proyecto/integrante-proyecto-details.component';
import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('IntegranteProyecto Management Detail Component', () => {
    let wrapper: Wrapper<IntegranteProyectoClass>;
    let comp: IntegranteProyectoClass;
    let integranteProyectoServiceStub: SinonStubbedInstance<IntegranteProyectoService>;

    beforeEach(() => {
      integranteProyectoServiceStub = sinon.createStubInstance<IntegranteProyectoService>(IntegranteProyectoService);

      wrapper = shallowMount<IntegranteProyectoClass>(IntegranteProyectoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { integranteProyectoService: () => integranteProyectoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundIntegranteProyecto = { id: 123 };
        integranteProyectoServiceStub.find.resolves(foundIntegranteProyecto);

        // WHEN
        comp.retrieveIntegranteProyecto(123);
        await comp.$nextTick();

        // THEN
        expect(comp.integranteProyecto).toBe(foundIntegranteProyecto);
      });
    });
  });
});
