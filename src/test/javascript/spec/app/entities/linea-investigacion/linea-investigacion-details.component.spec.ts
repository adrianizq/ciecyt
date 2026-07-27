/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import LineaInvestigacionDetailComponent from '@/entities/linea-investigacion/linea-investigacion-details.vue';
import LineaInvestigacionClass from '@/entities/linea-investigacion/linea-investigacion-details.component';
import LineaInvestigacionService from '@/entities/linea-investigacion/linea-investigacion.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('LineaInvestigacion Management Detail Component', () => {
    let wrapper: Wrapper<LineaInvestigacionClass>;
    let comp: LineaInvestigacionClass;
    let lineaInvestigacionServiceStub: SinonStubbedInstance<LineaInvestigacionService>;

    beforeEach(() => {
      lineaInvestigacionServiceStub = sinon.createStubInstance<LineaInvestigacionService>(LineaInvestigacionService);

      wrapper = shallowMount<LineaInvestigacionClass>(LineaInvestigacionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { lineaInvestigacionService: () => lineaInvestigacionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundLineaInvestigacion = { id: 123 };
        lineaInvestigacionServiceStub.find.resolves(foundLineaInvestigacion);

        // WHEN
        comp.retrieveLineaInvestigacion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.lineaInvestigacion).toBe(foundLineaInvestigacion);
      });
    });
  });
});
