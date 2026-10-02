/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CronogramaCiecytUpdateComponent from '@/entities/cronograma-ciecyt/cronograma-ciecyt-update.vue';
import CronogramaCiecytClass from '@/entities/cronograma-ciecyt/cronograma-ciecyt-update.component';
import CronogramaCiecytService from '@/entities/cronograma-ciecyt/cronograma-ciecyt.service';

import ModalidadService from '@/entities/modalidad/modalidad.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CronogramaCiecyt Management Update Component', () => {
    let wrapper: Wrapper<CronogramaCiecytClass>;
    let comp: CronogramaCiecytClass;
    let cronogramaCiecytServiceStub: SinonStubbedInstance<CronogramaCiecytService>;

    beforeEach(() => {
      cronogramaCiecytServiceStub = sinon.createStubInstance<CronogramaCiecytService>(CronogramaCiecytService);

      wrapper = shallowMount<CronogramaCiecytClass>(CronogramaCiecytUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cronogramaCiecytService: () => cronogramaCiecytServiceStub,

          modalidadService: () => new ModalidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cronogramaCiecyt = entity;
        cronogramaCiecytServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cronogramaCiecytServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cronogramaCiecyt = entity;
        cronogramaCiecytServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cronogramaCiecytServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
