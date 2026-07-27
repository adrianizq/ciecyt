/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TipoPreguntaDetailComponent from '@/entities/tipo-pregunta/tipo-pregunta-details.vue';
import TipoPreguntaClass from '@/entities/tipo-pregunta/tipo-pregunta-details.component';
import TipoPreguntaService from '@/entities/tipo-pregunta/tipo-pregunta.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TipoPregunta Management Detail Component', () => {
    let wrapper: Wrapper<TipoPreguntaClass>;
    let comp: TipoPreguntaClass;
    let tipoPreguntaServiceStub: SinonStubbedInstance<TipoPreguntaService>;

    beforeEach(() => {
      tipoPreguntaServiceStub = sinon.createStubInstance<TipoPreguntaService>(TipoPreguntaService);

      wrapper = shallowMount<TipoPreguntaClass>(TipoPreguntaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { tipoPreguntaService: () => tipoPreguntaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTipoPregunta = { id: 123 };
        tipoPreguntaServiceStub.find.resolves(foundTipoPregunta);

        // WHEN
        comp.retrieveTipoPregunta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tipoPregunta).toBe(foundTipoPregunta);
      });
    });
  });
});
