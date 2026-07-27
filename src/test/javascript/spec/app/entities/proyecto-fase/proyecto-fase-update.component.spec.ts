/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProyectoFaseUpdateComponent from '@/entities/proyecto-fase/proyecto-fase-update.vue';
import ProyectoFaseClass from '@/entities/proyecto-fase/proyecto-fase-update.component';
import ProyectoFaseService from '@/entities/proyecto-fase/proyecto-fase.service';

import FasesService from '@/entities/fases/fases.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProyectoFase Management Update Component', () => {
    let wrapper: Wrapper<ProyectoFaseClass>;
    let comp: ProyectoFaseClass;
    let proyectoFaseServiceStub: SinonStubbedInstance<ProyectoFaseService>;

    beforeEach(() => {
      proyectoFaseServiceStub = sinon.createStubInstance<ProyectoFaseService>(ProyectoFaseService);

      wrapper = shallowMount<ProyectoFaseClass>(ProyectoFaseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          proyectoFaseService: () => proyectoFaseServiceStub,

          fasesService: () => new FasesService(),

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.proyectoFase = entity;
        proyectoFaseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoFaseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.proyectoFase = entity;
        proyectoFaseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoFaseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
