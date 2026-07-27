/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MenuDetailComponent from '@/entities/menu/menu-details.vue';
import MenuClass from '@/entities/menu/menu-details.component';
import MenuService from '@/entities/menu/menu.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Menu Management Detail Component', () => {
    let wrapper: Wrapper<MenuClass>;
    let comp: MenuClass;
    let menuServiceStub: SinonStubbedInstance<MenuService>;

    beforeEach(() => {
      menuServiceStub = sinon.createStubInstance<MenuService>(MenuService);

      wrapper = shallowMount<MenuClass>(MenuDetailComponent, { store, i18n, localVue, provide: { menuService: () => menuServiceStub } });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMenu = { id: 123 };
        menuServiceStub.find.resolves(foundMenu);

        // WHEN
        comp.retrieveMenu(123);
        await comp.$nextTick();

        // THEN
        expect(comp.menu).toBe(foundMenu);
      });
    });
  });
});
