/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import RolMenuDetailComponent from '@/entities/rol-menu/rol-menu-details.vue';
import RolMenuClass from '@/entities/rol-menu/rol-menu-details.component';
import RolMenuService from '@/entities/rol-menu/rol-menu.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RolMenu Management Detail Component', () => {
    let wrapper: Wrapper<RolMenuClass>;
    let comp: RolMenuClass;
    let rolMenuServiceStub: SinonStubbedInstance<RolMenuService>;

    beforeEach(() => {
      rolMenuServiceStub = sinon.createStubInstance<RolMenuService>(RolMenuService);

      wrapper = shallowMount<RolMenuClass>(RolMenuDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { rolMenuService: () => rolMenuServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRolMenu = { id: 123 };
        rolMenuServiceStub.find.resolves(foundRolMenu);

        // WHEN
        comp.retrieveRolMenu(123);
        await comp.$nextTick();

        // THEN
        expect(comp.rolMenu).toEqual(foundRolMenu);
      });
    });
  });
});
