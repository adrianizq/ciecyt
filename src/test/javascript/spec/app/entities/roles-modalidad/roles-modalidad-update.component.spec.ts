/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RolesModalidadUpdateComponent from '@/entities/roles-modalidad/roles-modalidad-update.vue';
import RolesModalidadClass from '@/entities/roles-modalidad/roles-modalidad-update.component';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';

import ModalidadService from '@/entities/modalidad/modalidad.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RolesModalidad Management Update Component', () => {
    let wrapper: Wrapper<RolesModalidadClass>;
    let comp: RolesModalidadClass;
    let rolesModalidadServiceStub: SinonStubbedInstance<RolesModalidadService>;

    beforeEach(() => {
      rolesModalidadServiceStub = sinon.createStubInstance<RolesModalidadService>(RolesModalidadService);

      wrapper = shallowMount<RolesModalidadClass>(RolesModalidadUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rolesModalidadService: () => rolesModalidadServiceStub,

          modalidadService: () => new ModalidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.rolesModalidad = entity;
        rolesModalidadServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rolesModalidadServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rolesModalidad = entity;
        rolesModalidadServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rolesModalidadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
