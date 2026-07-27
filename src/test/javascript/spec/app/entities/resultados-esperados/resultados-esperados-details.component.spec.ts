/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ResultadosEsperadosDetailComponent from '@/entities/resultados-esperados/resultados-esperados-details.vue';
import ResultadosEsperadosClass from '@/entities/resultados-esperados/resultados-esperados-details.component';
import ResultadosEsperadosService from '@/entities/resultados-esperados/resultados-esperados.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ResultadosEsperados Management Detail Component', () => {
    let wrapper: Wrapper<ResultadosEsperadosClass>;
    let comp: ResultadosEsperadosClass;
    let resultadosEsperadosServiceStub: SinonStubbedInstance<ResultadosEsperadosService>;

    beforeEach(() => {
      resultadosEsperadosServiceStub = sinon.createStubInstance<ResultadosEsperadosService>(ResultadosEsperadosService);

      wrapper = shallowMount<ResultadosEsperadosClass>(ResultadosEsperadosDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { resultadosEsperadosService: () => resultadosEsperadosServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundResultadosEsperados = { id: 123 };
        resultadosEsperadosServiceStub.find.resolves(foundResultadosEsperados);

        // WHEN
        comp.retrieveResultadosEsperados(123);
        await comp.$nextTick();

        // THEN
        expect(comp.resultadosEsperados).toBe(foundResultadosEsperados);
      });
    });
  });
});
