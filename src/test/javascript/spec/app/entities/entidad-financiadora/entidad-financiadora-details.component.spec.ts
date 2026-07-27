/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EntidadFinanciadoraDetailComponent from '@/entities/entidad-financiadora/entidad-financiadora-details.vue';
import EntidadFinanciadoraClass from '@/entities/entidad-financiadora/entidad-financiadora-details.component';
import EntidadFinanciadoraService from '@/entities/entidad-financiadora/entidad-financiadora.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('EntidadFinanciadora Management Detail Component', () => {
    let wrapper: Wrapper<EntidadFinanciadoraClass>;
    let comp: EntidadFinanciadoraClass;
    let entidadFinanciadoraServiceStub: SinonStubbedInstance<EntidadFinanciadoraService>;

    beforeEach(() => {
      entidadFinanciadoraServiceStub = sinon.createStubInstance<EntidadFinanciadoraService>(EntidadFinanciadoraService);

      wrapper = shallowMount<EntidadFinanciadoraClass>(EntidadFinanciadoraDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { entidadFinanciadoraService: () => entidadFinanciadoraServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEntidadFinanciadora = { id: 123 };
        entidadFinanciadoraServiceStub.find.resolves(foundEntidadFinanciadora);

        // WHEN
        comp.retrieveEntidadFinanciadora(123);
        await comp.$nextTick();

        // THEN
        expect(comp.entidadFinanciadora).toBe(foundEntidadFinanciadora);
      });
    });
  });
});
