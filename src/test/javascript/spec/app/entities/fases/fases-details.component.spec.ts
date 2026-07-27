/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FasesDetailComponent from '@/entities/fases/fases-details.vue';
import FasesClass from '@/entities/fases/fases-details.component';
import FasesService from '@/entities/fases/fases.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Fases Management Detail Component', () => {
    let wrapper: Wrapper<FasesClass>;
    let comp: FasesClass;
    let fasesServiceStub: SinonStubbedInstance<FasesService>;

    beforeEach(() => {
      fasesServiceStub = sinon.createStubInstance<FasesService>(FasesService);

      wrapper = shallowMount<FasesClass>(FasesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { fasesService: () => fasesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFases = { id: 123 };
        fasesServiceStub.find.resolves(foundFases);

        // WHEN
        comp.retrieveFases(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fases).toBe(foundFases);
      });
    });
  });
});
