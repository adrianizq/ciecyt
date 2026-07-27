/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PresupuestoValorUpdateComponent from '@/entities/presupuesto-valor/presupuesto-valor-update.vue';
import PresupuestoValorClass from '@/entities/presupuesto-valor/presupuesto-valor-update.component';
import PresupuestoValorService from '@/entities/presupuesto-valor/presupuesto-valor.service';

import RubroService from '@/entities/rubro/rubro.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

import EntidadService from '@/entities/entidad/entidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('PresupuestoValor Management Update Component', () => {
    let wrapper: Wrapper<PresupuestoValorClass>;
    let comp: PresupuestoValorClass;
    let presupuestoValorServiceStub: SinonStubbedInstance<PresupuestoValorService>;

    beforeEach(() => {
      presupuestoValorServiceStub = sinon.createStubInstance<PresupuestoValorService>(PresupuestoValorService);

      wrapper = shallowMount<PresupuestoValorClass>(PresupuestoValorUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          presupuestoValorService: () => presupuestoValorServiceStub,

          rubroService: () => new RubroService(),

          proyectoService: () => new ProyectoService(),

          entidadService: () => new EntidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.presupuestoValor = entity;
        presupuestoValorServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(presupuestoValorServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.presupuestoValor = entity;
        presupuestoValorServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(presupuestoValorServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
