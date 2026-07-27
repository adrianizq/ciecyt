/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ModalidadDetailComponent from '@/entities/modalidad/modalidad-details.vue';
import ModalidadClass from '@/entities/modalidad/modalidad-details.component';
import ModalidadService from '@/entities/modalidad/modalidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Modalidad Management Detail Component', () => {
    let wrapper: Wrapper<ModalidadClass>;
    let comp: ModalidadClass;
    let modalidadServiceStub: SinonStubbedInstance<ModalidadService>;

    beforeEach(() => {
      modalidadServiceStub = sinon.createStubInstance<ModalidadService>(ModalidadService);

      wrapper = shallowMount<ModalidadClass>(ModalidadDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { modalidadService: () => modalidadServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundModalidad = { id: 123 };
        modalidadServiceStub.find.resolves(foundModalidad);

        // WHEN
        comp.retrieveModalidad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.modalidad).toBe(foundModalidad);
      });
    });
  });
});
