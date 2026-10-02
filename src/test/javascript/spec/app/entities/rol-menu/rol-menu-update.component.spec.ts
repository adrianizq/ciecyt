/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import RolMenuUpdateComponent from '@/entities/rol-menu/rol-menu-update.vue';
import RolMenuClass from '@/entities/rol-menu/rol-menu-update.component';
import RolMenuService from '@/entities/rol-menu/rol-menu.service';

import MenuService from '@/entities/menu/menu.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('RolMenu Management Update Component', () => {
    let wrapper: Wrapper<RolMenuClass>;
    let comp: RolMenuClass;
    let rolMenuServiceStub: SinonStubbedInstance<RolMenuService>;

    beforeEach(() => {
      rolMenuServiceStub = sinon.createStubInstance<RolMenuService>(RolMenuService);

      wrapper = shallowMount<RolMenuClass>(RolMenuUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          rolMenuService: () => rolMenuServiceStub,

          menuService: () => new MenuService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.rolMenu = entity;
        rolMenuServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rolMenuServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.rolMenu = entity;
        rolMenuServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(rolMenuServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
