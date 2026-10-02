/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import FichaTecnicaUpdateComponent from '@/entities/ficha-tecnica/ficha-tecnica-update.vue';
import FichaTecnicaClass from '@/entities/ficha-tecnica/ficha-tecnica-update.component';
import FichaTecnicaService from '@/entities/ficha-tecnica/ficha-tecnica.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('FichaTecnica Management Update Component', () => {
    let wrapper: Wrapper<FichaTecnicaClass>;
    let comp: FichaTecnicaClass;
    let fichaTecnicaServiceStub: SinonStubbedInstance<FichaTecnicaService>;

    beforeEach(() => {
      fichaTecnicaServiceStub = sinon.createStubInstance<FichaTecnicaService>(FichaTecnicaService);

      wrapper = shallowMount<FichaTecnicaClass>(FichaTecnicaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          fichaTecnicaService: () => fichaTecnicaServiceStub,

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.fichaTecnica = entity;
        fichaTecnicaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fichaTecnicaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fichaTecnica = entity;
        fichaTecnicaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fichaTecnicaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
