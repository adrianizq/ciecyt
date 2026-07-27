/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import InvestigacionTipoDetailComponent from '@/entities/investigacion-tipo/investigacion-tipo-details.vue';
import InvestigacionTipoClass from '@/entities/investigacion-tipo/investigacion-tipo-details.component';
import InvestigacionTipoService from '@/entities/investigacion-tipo/investigacion-tipo.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('InvestigacionTipo Management Detail Component', () => {
    let wrapper: Wrapper<InvestigacionTipoClass>;
    let comp: InvestigacionTipoClass;
    let investigacionTipoServiceStub: SinonStubbedInstance<InvestigacionTipoService>;

    beforeEach(() => {
      investigacionTipoServiceStub = sinon.createStubInstance<InvestigacionTipoService>(InvestigacionTipoService);

      wrapper = shallowMount<InvestigacionTipoClass>(InvestigacionTipoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { investigacionTipoService: () => investigacionTipoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundInvestigacionTipo = { id: 123 };
        investigacionTipoServiceStub.find.resolves(foundInvestigacionTipo);

        // WHEN
        comp.retrieveInvestigacionTipo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.investigacionTipo).toBe(foundInvestigacionTipo);
      });
    });
  });
});
