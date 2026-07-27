/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AcuerdoUpdateComponent from '@/entities/acuerdo/acuerdo-update.vue';
import AcuerdoClass from '@/entities/acuerdo/acuerdo-update.component';
import AcuerdoService from '@/entities/acuerdo/acuerdo.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Acuerdo Management Update Component', () => {
    let wrapper: Wrapper<AcuerdoClass>;
    let comp: AcuerdoClass;
    let acuerdoServiceStub: SinonStubbedInstance<AcuerdoService>;

    beforeEach(() => {
      acuerdoServiceStub = sinon.createStubInstance<AcuerdoService>(AcuerdoService);

      wrapper = shallowMount<AcuerdoClass>(AcuerdoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          acuerdoService: () => acuerdoServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.acuerdo = entity;
        acuerdoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(acuerdoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.acuerdo = entity;
        acuerdoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(acuerdoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
