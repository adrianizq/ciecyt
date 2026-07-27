/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AcuerdoDetailComponent from '@/entities/acuerdo/acuerdo-details.vue';
import AcuerdoClass from '@/entities/acuerdo/acuerdo-details.component';
import AcuerdoService from '@/entities/acuerdo/acuerdo.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Acuerdo Management Detail Component', () => {
    let wrapper: Wrapper<AcuerdoClass>;
    let comp: AcuerdoClass;
    let acuerdoServiceStub: SinonStubbedInstance<AcuerdoService>;

    beforeEach(() => {
      acuerdoServiceStub = sinon.createStubInstance<AcuerdoService>(AcuerdoService);

      wrapper = shallowMount<AcuerdoClass>(AcuerdoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { acuerdoService: () => acuerdoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAcuerdo = { id: 123 };
        acuerdoServiceStub.find.resolves(foundAcuerdo);

        // WHEN
        comp.retrieveAcuerdo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.acuerdo).toBe(foundAcuerdo);
      });
    });
  });
});
