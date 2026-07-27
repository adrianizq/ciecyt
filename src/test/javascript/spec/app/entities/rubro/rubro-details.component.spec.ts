/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RubroDetailComponent from '@/entities/rubro/rubro-details.vue';
import RubroClass from '@/entities/rubro/rubro-details.component';
import RubroService from '@/entities/rubro/rubro.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Rubro Management Detail Component', () => {
    let wrapper: Wrapper<RubroClass>;
    let comp: RubroClass;
    let rubroServiceStub: SinonStubbedInstance<RubroService>;

    beforeEach(() => {
      rubroServiceStub = sinon.createStubInstance<RubroService>(RubroService);

      wrapper = shallowMount<RubroClass>(RubroDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rubroService: () => rubroServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRubro = { id: 123 };
        rubroServiceStub.find.resolves(foundRubro);

        // WHEN
        comp.retrieveRubro(123);
        await comp.$nextTick();

        // THEN
        expect(comp.rubro).toBe(foundRubro);
      });
    });
  });
});
