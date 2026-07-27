/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CronogramaUpdateComponent from '@/entities/cronograma/cronograma-update.vue';
import CronogramaClass from '@/entities/cronograma/cronograma-update.component';
import CronogramaService from '@/entities/cronograma/cronograma.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Cronograma Management Update Component', () => {
    let wrapper: Wrapper<CronogramaClass>;
    let comp: CronogramaClass;
    let cronogramaServiceStub: SinonStubbedInstance<CronogramaService>;

    beforeEach(() => {
      cronogramaServiceStub = sinon.createStubInstance<CronogramaService>(CronogramaService);

      wrapper = shallowMount<CronogramaClass>(CronogramaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cronogramaService: () => cronogramaServiceStub,

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cronograma = entity;
        cronogramaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cronogramaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cronograma = entity;
        cronogramaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cronogramaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
