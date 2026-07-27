/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RetroalimentacionDetailComponent from '@/entities/retroalimentacion/retroalimentacion-details.vue';
import RetroalimentacionClass from '@/entities/retroalimentacion/retroalimentacion-details.component';
import RetroalimentacionService from '@/entities/retroalimentacion/retroalimentacion.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Retroalimentacion Management Detail Component', () => {
    let wrapper: Wrapper<RetroalimentacionClass>;
    let comp: RetroalimentacionClass;
    let retroalimentacionServiceStub: SinonStubbedInstance<RetroalimentacionService>;

    beforeEach(() => {
      retroalimentacionServiceStub = sinon.createStubInstance<RetroalimentacionService>(RetroalimentacionService);

      wrapper = shallowMount<RetroalimentacionClass>(RetroalimentacionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { retroalimentacionService: () => retroalimentacionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRetroalimentacion = { id: 123 };
        retroalimentacionServiceStub.find.resolves(foundRetroalimentacion);

        // WHEN
        comp.retrieveRetroalimentacion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.retroalimentacion).toBe(foundRetroalimentacion);
      });
    });
  });
});
