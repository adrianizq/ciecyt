/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CicloPropedeuticoDetailComponent from '@/entities/ciclo-propedeutico/ciclo-propedeutico-details.vue';
import CicloPropedeuticoClass from '@/entities/ciclo-propedeutico/ciclo-propedeutico-details.component';
import CicloPropedeuticoService from '@/entities/ciclo-propedeutico/ciclo-propedeutico.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CicloPropedeutico Management Detail Component', () => {
    let wrapper: Wrapper<CicloPropedeuticoClass>;
    let comp: CicloPropedeuticoClass;
    let cicloPropedeuticoServiceStub: SinonStubbedInstance<CicloPropedeuticoService>;

    beforeEach(() => {
      cicloPropedeuticoServiceStub = sinon.createStubInstance<CicloPropedeuticoService>(CicloPropedeuticoService);

      wrapper = shallowMount<CicloPropedeuticoClass>(CicloPropedeuticoDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cicloPropedeuticoService: () => cicloPropedeuticoServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCicloPropedeutico = { id: 123 };
        cicloPropedeuticoServiceStub.find.resolves(foundCicloPropedeutico);

        // WHEN
        comp.retrieveCicloPropedeutico(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cicloPropedeutico).toBe(foundCicloPropedeutico);
      });
    });
  });
});
