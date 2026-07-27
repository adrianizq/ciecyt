/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ImpactosEsperadosDetailComponent from '@/entities/impactos-esperados/impactos-esperados-details.vue';
import ImpactosEsperadosClass from '@/entities/impactos-esperados/impactos-esperados-details.component';
import ImpactosEsperadosService from '@/entities/impactos-esperados/impactos-esperados.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ImpactosEsperados Management Detail Component', () => {
    let wrapper: Wrapper<ImpactosEsperadosClass>;
    let comp: ImpactosEsperadosClass;
    let impactosEsperadosServiceStub: SinonStubbedInstance<ImpactosEsperadosService>;

    beforeEach(() => {
      impactosEsperadosServiceStub = sinon.createStubInstance<ImpactosEsperadosService>(ImpactosEsperadosService);

      wrapper = shallowMount<ImpactosEsperadosClass>(ImpactosEsperadosDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { impactosEsperadosService: () => impactosEsperadosServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundImpactosEsperados = { id: 123 };
        impactosEsperadosServiceStub.find.resolves(foundImpactosEsperados);

        // WHEN
        comp.retrieveImpactosEsperados(123);
        await comp.$nextTick();

        // THEN
        expect(comp.impactosEsperados).toBe(foundImpactosEsperados);
      });
    });
  });
});
