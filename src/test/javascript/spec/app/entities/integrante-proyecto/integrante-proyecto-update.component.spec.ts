/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import IntegranteProyectoUpdateComponent from '@/entities/integrante-proyecto/integrante-proyecto-update.vue';
import IntegranteProyectoClass from '@/entities/integrante-proyecto/integrante-proyecto-update.component';
import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

import UserService from '@/admin/user-management/user-management.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('IntegranteProyecto Management Update Component', () => {
    let wrapper: Wrapper<IntegranteProyectoClass>;
    let comp: IntegranteProyectoClass;
    let integranteProyectoServiceStub: SinonStubbedInstance<IntegranteProyectoService>;

    beforeEach(() => {
      integranteProyectoServiceStub = sinon.createStubInstance<IntegranteProyectoService>(IntegranteProyectoService);

      wrapper = shallowMount<IntegranteProyectoClass>(IntegranteProyectoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          integranteProyectoService: () => integranteProyectoServiceStub,

          userService: () => new UserService(),

          proyectoService: () => new ProyectoService(),

          rolesModalidadService: () => new RolesModalidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.integranteProyecto = entity;
        integranteProyectoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(integranteProyectoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.integranteProyecto = entity;
        integranteProyectoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(integranteProyectoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
