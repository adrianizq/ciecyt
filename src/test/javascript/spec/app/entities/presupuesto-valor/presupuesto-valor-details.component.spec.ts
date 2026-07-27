/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PresupuestoValorDetailComponent from '@/entities/presupuesto-valor/presupuesto-valor-details.vue';
import PresupuestoValorClass from '@/entities/presupuesto-valor/presupuesto-valor-details.component';
import PresupuestoValorService from '@/entities/presupuesto-valor/presupuesto-valor.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('PresupuestoValor Management Detail Component', () => {
    let wrapper: Wrapper<PresupuestoValorClass>;
    let comp: PresupuestoValorClass;
    let presupuestoValorServiceStub: SinonStubbedInstance<PresupuestoValorService>;

    beforeEach(() => {
      presupuestoValorServiceStub = sinon.createStubInstance<PresupuestoValorService>(PresupuestoValorService);

      wrapper = shallowMount<PresupuestoValorClass>(PresupuestoValorDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { presupuestoValorService: () => presupuestoValorServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPresupuestoValor = { id: 123 };
        presupuestoValorServiceStub.find.resolves(foundPresupuestoValor);

        // WHEN
        comp.retrievePresupuestoValor(123);
        await comp.$nextTick();

        // THEN
        expect(comp.presupuestoValor).toBe(foundPresupuestoValor);
      });
    });
  });
});
