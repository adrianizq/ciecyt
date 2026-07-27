/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AdjuntoRetroalimentacionDetailComponent from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion-details.vue';
import AdjuntoRetroalimentacionClass from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion-details.component';
import AdjuntoRetroalimentacionService from '@/entities/adjunto-retroalimentacion/adjunto-retroalimentacion.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('AdjuntoRetroalimentacion Management Detail Component', () => {
    let wrapper: Wrapper<AdjuntoRetroalimentacionClass>;
    let comp: AdjuntoRetroalimentacionClass;
    let adjuntoRetroalimentacionServiceStub: SinonStubbedInstance<AdjuntoRetroalimentacionService>;

    beforeEach(() => {
      adjuntoRetroalimentacionServiceStub = sinon.createStubInstance<AdjuntoRetroalimentacionService>(AdjuntoRetroalimentacionService);

      wrapper = shallowMount<AdjuntoRetroalimentacionClass>(AdjuntoRetroalimentacionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { adjuntoRetroalimentacionService: () => adjuntoRetroalimentacionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAdjuntoRetroalimentacion = { id: 123 };
        adjuntoRetroalimentacionServiceStub.find.resolves(foundAdjuntoRetroalimentacion);

        // WHEN
        comp.retrieveAdjuntoRetroalimentacion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.adjuntoRetroalimentacion).toBe(foundAdjuntoRetroalimentacion);
      });
    });
  });
});
