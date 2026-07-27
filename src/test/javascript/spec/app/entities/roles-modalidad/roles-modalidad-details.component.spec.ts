/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RolesModalidadDetailComponent from '@/entities/roles-modalidad/roles-modalidad-details.vue';
import RolesModalidadClass from '@/entities/roles-modalidad/roles-modalidad-details.component';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RolesModalidad Management Detail Component', () => {
    let wrapper: Wrapper<RolesModalidadClass>;
    let comp: RolesModalidadClass;
    let rolesModalidadServiceStub: SinonStubbedInstance<RolesModalidadService>;

    beforeEach(() => {
      rolesModalidadServiceStub = sinon.createStubInstance<RolesModalidadService>(RolesModalidadService);

      wrapper = shallowMount<RolesModalidadClass>(RolesModalidadDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rolesModalidadService: () => rolesModalidadServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRolesModalidad = { id: 123 };
        rolesModalidadServiceStub.find.resolves(foundRolesModalidad);

        // WHEN
        comp.retrieveRolesModalidad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.rolesModalidad).toBe(foundRolesModalidad);
      });
    });
  });
});
