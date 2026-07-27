/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PreguntaDetailComponent from '@/entities/pregunta/pregunta-details.vue';
import PreguntaClass from '@/entities/pregunta/pregunta-details.component';
import PreguntaService from '@/entities/pregunta/pregunta.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Pregunta Management Detail Component', () => {
    let wrapper: Wrapper<PreguntaClass>;
    let comp: PreguntaClass;
    let preguntaServiceStub: SinonStubbedInstance<PreguntaService>;

    beforeEach(() => {
      preguntaServiceStub = sinon.createStubInstance<PreguntaService>(PreguntaService);

      wrapper = shallowMount<PreguntaClass>(PreguntaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { preguntaService: () => preguntaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPregunta = { id: 123 };
        preguntaServiceStub.find.resolves(foundPregunta);

        // WHEN
        comp.retrievePregunta(123);
        await comp.$nextTick();

        // THEN
        expect(comp.pregunta).toBe(foundPregunta);
      });
    });
  });
});
