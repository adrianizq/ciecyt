/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FichaTecnicaDetailComponent from '@/entities/ficha-tecnica/ficha-tecnica-details.vue';
import FichaTecnicaClass from '@/entities/ficha-tecnica/ficha-tecnica-details.component';
import FichaTecnicaService from '@/entities/ficha-tecnica/ficha-tecnica.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('FichaTecnica Management Detail Component', () => {
    let wrapper: Wrapper<FichaTecnicaClass>;
    let comp: FichaTecnicaClass;
    let fichaTecnicaServiceStub: SinonStubbedInstance<FichaTecnicaService>;

    beforeEach(() => {
      fichaTecnicaServiceStub = sinon.createStubInstance<FichaTecnicaService>(FichaTecnicaService);

      wrapper = shallowMount<FichaTecnicaClass>(FichaTecnicaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { fichaTecnicaService: () => fichaTecnicaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFichaTecnica = { id: 123 };
        fichaTecnicaServiceStub.find.resolves(foundFichaTecnica);

        // WHEN
        comp.retrieveFichaTecnica(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fichaTecnica).toBe(foundFichaTecnica);
      });
    });
  });
});
