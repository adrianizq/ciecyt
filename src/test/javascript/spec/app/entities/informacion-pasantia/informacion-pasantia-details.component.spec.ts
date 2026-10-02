/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import InformacionPasantiaDetailComponent from '@/entities/informacion-pasantia/informacion-pasantia-details.vue';
import InformacionPasantiaClass from '@/entities/informacion-pasantia/informacion-pasantia-details.component';
import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('InformacionPasantia Management Detail Component', () => {
    let wrapper: Wrapper<InformacionPasantiaClass>;
    let comp: InformacionPasantiaClass;
    let informacionPasantiaServiceStub: SinonStubbedInstance<InformacionPasantiaService>;

    beforeEach(() => {
      informacionPasantiaServiceStub = sinon.createStubInstance<InformacionPasantiaService>(InformacionPasantiaService);

      wrapper = shallowMount<InformacionPasantiaClass>(InformacionPasantiaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { informacionPasantiaService: () => informacionPasantiaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundInformacionPasantia = { id: 123 };
        informacionPasantiaServiceStub.find.resolves(foundInformacionPasantia);

        // WHEN
        comp.retrieveInformacionPasantia(123);
        await comp.$nextTick();

        // THEN
        expect(comp.informacionPasantia).toEqual(foundInformacionPasantia);
      });
    });
  });
});
