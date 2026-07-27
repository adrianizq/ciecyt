/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProgramaDetailComponent from '@/entities/programa/programa-details.vue';
import ProgramaClass from '@/entities/programa/programa-details.component';
import ProgramaService from '@/entities/programa/programa.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Programa Management Detail Component', () => {
    let wrapper: Wrapper<ProgramaClass>;
    let comp: ProgramaClass;
    let programaServiceStub: SinonStubbedInstance<ProgramaService>;

    beforeEach(() => {
      programaServiceStub = sinon.createStubInstance<ProgramaService>(ProgramaService);

      wrapper = shallowMount<ProgramaClass>(ProgramaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { programaService: () => programaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPrograma = { id: 123 };
        programaServiceStub.find.resolves(foundPrograma);

        // WHEN
        comp.retrievePrograma(123);
        await comp.$nextTick();

        // THEN
        expect(comp.programa).toBe(foundPrograma);
      });
    });
  });
});
