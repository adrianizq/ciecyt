/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CronogramaCiecytFasesUpdateComponent from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases-update.vue';
import CronogramaCiecytFasesClass from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases-update.component';
import CronogramaCiecytFasesService from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.service';

import CronogramaCiecytService from '@/entities/cronograma-ciecyt/cronograma-ciecyt.service';

import FasesService from '@/entities/fases/fases.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CronogramaCiecytFases Management Update Component', () => {
    let wrapper: Wrapper<CronogramaCiecytFasesClass>;
    let comp: CronogramaCiecytFasesClass;
    let cronogramaCiecytFasesServiceStub: SinonStubbedInstance<CronogramaCiecytFasesService>;

    beforeEach(() => {
      cronogramaCiecytFasesServiceStub = sinon.createStubInstance<CronogramaCiecytFasesService>(CronogramaCiecytFasesService);

      wrapper = shallowMount<CronogramaCiecytFasesClass>(CronogramaCiecytFasesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cronogramaCiecytFasesService: () => cronogramaCiecytFasesServiceStub,

          cronogramaCiecytService: () => new CronogramaCiecytService(),

          fasesService: () => new FasesService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cronogramaCiecytFases = entity;
        cronogramaCiecytFasesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cronogramaCiecytFasesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cronogramaCiecytFases = entity;
        cronogramaCiecytFasesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cronogramaCiecytFasesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
