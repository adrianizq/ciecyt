/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EntidadDetailComponent from '@/entities/entidad/entidad-details.vue';
import EntidadClass from '@/entities/entidad/entidad-details.component';
import EntidadService from '@/entities/entidad/entidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Entidad Management Detail Component', () => {
    let wrapper: Wrapper<EntidadClass>;
    let comp: EntidadClass;
    let entidadServiceStub: SinonStubbedInstance<EntidadService>;

    beforeEach(() => {
      entidadServiceStub = sinon.createStubInstance<EntidadService>(EntidadService);

      wrapper = shallowMount<EntidadClass>(EntidadDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { entidadService: () => entidadServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEntidad = { id: 123 };
        entidadServiceStub.find.resolves(foundEntidad);

        // WHEN
        comp.retrieveEntidad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.entidad).toBe(foundEntidad);
      });
    });
  });
});
