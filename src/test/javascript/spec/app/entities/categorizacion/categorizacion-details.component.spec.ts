/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CategorizacionDetailComponent from '@/entities/categorizacion/categorizacion-details.vue';
import CategorizacionClass from '@/entities/categorizacion/categorizacion-details.component';
import CategorizacionService from '@/entities/categorizacion/categorizacion.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Categorizacion Management Detail Component', () => {
    let wrapper: Wrapper<CategorizacionClass>;
    let comp: CategorizacionClass;
    let categorizacionServiceStub: SinonStubbedInstance<CategorizacionService>;

    beforeEach(() => {
      categorizacionServiceStub = sinon.createStubInstance<CategorizacionService>(CategorizacionService);

      wrapper = shallowMount<CategorizacionClass>(CategorizacionDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { categorizacionService: () => categorizacionServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCategorizacion = { id: 123 };
        categorizacionServiceStub.find.resolves(foundCategorizacion);

        // WHEN
        comp.retrieveCategorizacion(123);
        await comp.$nextTick();

        // THEN
        expect(comp.categorizacion).toEqual(foundCategorizacion);
      });
    });
  });
});
