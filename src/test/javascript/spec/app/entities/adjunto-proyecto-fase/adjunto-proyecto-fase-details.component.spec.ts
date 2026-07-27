/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AdjuntoProyectoFaseDetailComponent from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase-details.vue';
import AdjuntoProyectoFaseClass from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase-details.component';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AdjuntoProyectoFase Management Detail Component', () => {
    let wrapper: Wrapper<AdjuntoProyectoFaseClass>;
    let comp: AdjuntoProyectoFaseClass;
    let adjuntoProyectoFaseServiceStub: SinonStubbedInstance<AdjuntoProyectoFaseService>;

    beforeEach(() => {
      adjuntoProyectoFaseServiceStub = sinon.createStubInstance<AdjuntoProyectoFaseService>(AdjuntoProyectoFaseService);

      wrapper = shallowMount<AdjuntoProyectoFaseClass>(AdjuntoProyectoFaseDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { adjuntoProyectoFaseService: () => adjuntoProyectoFaseServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAdjuntoProyectoFase = { id: 123 };
        adjuntoProyectoFaseServiceStub.find.resolves(foundAdjuntoProyectoFase);

        // WHEN
        comp.retrieveAdjuntoProyectoFase(123);
        await comp.$nextTick();

        // THEN
        expect(comp.adjuntoProyectoFase).toBe(foundAdjuntoProyectoFase);
      });
    });
  });
});
