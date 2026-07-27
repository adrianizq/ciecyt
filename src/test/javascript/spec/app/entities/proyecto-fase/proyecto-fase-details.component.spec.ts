/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProyectoFaseDetailComponent from '@/entities/proyecto-fase/proyecto-fase-details.vue';
import ProyectoFaseClass from '@/entities/proyecto-fase/proyecto-fase-details.component';
import ProyectoFaseService from '@/entities/proyecto-fase/proyecto-fase.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('ProyectoFase Management Detail Component', () => {
    let wrapper: Wrapper<ProyectoFaseClass>;
    let comp: ProyectoFaseClass;
    let proyectoFaseServiceStub: SinonStubbedInstance<ProyectoFaseService>;

    beforeEach(() => {
      proyectoFaseServiceStub = sinon.createStubInstance<ProyectoFaseService>(ProyectoFaseService);

      wrapper = shallowMount<ProyectoFaseClass>(ProyectoFaseDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { proyectoFaseService: () => proyectoFaseServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProyectoFase = { id: 123 };
        proyectoFaseServiceStub.find.resolves(foundProyectoFase);

        // WHEN
        comp.retrieveProyectoFase(123);
        await comp.$nextTick();

        // THEN
        expect(comp.proyectoFase).toBe(foundProyectoFase);
      });
    });
  });
});
