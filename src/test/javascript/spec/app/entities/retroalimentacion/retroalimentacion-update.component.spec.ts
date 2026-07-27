/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RetroalimentacionUpdateComponent from '@/entities/retroalimentacion/retroalimentacion-update.vue';
import RetroalimentacionClass from '@/entities/retroalimentacion/retroalimentacion-update.component';
import RetroalimentacionService from '@/entities/retroalimentacion/retroalimentacion.service';

import ProyectoFaseService from '@/entities/proyecto-fase/proyecto-fase.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Retroalimentacion Management Update Component', () => {
    let wrapper: Wrapper<RetroalimentacionClass>;
    let comp: RetroalimentacionClass;
    let retroalimentacionServiceStub: SinonStubbedInstance<RetroalimentacionService>;

    beforeEach(() => {
      retroalimentacionServiceStub = sinon.createStubInstance<RetroalimentacionService>(RetroalimentacionService);

      wrapper = shallowMount<RetroalimentacionClass>(RetroalimentacionUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          retroalimentacionService: () => retroalimentacionServiceStub,

          proyectoFaseService: () => new ProyectoFaseService(),

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.retroalimentacion = entity;
        retroalimentacionServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(retroalimentacionServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.retroalimentacion = entity;
        retroalimentacionServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(retroalimentacionServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
