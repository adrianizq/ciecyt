/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ImpactosEsperadosUpdateComponent from '@/entities/impactos-esperados/impactos-esperados-update.vue';
import ImpactosEsperadosClass from '@/entities/impactos-esperados/impactos-esperados-update.component';
import ImpactosEsperadosService from '@/entities/impactos-esperados/impactos-esperados.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ImpactosEsperados Management Update Component', () => {
    let wrapper: Wrapper<ImpactosEsperadosClass>;
    let comp: ImpactosEsperadosClass;
    let impactosEsperadosServiceStub: SinonStubbedInstance<ImpactosEsperadosService>;

    beforeEach(() => {
      impactosEsperadosServiceStub = sinon.createStubInstance<ImpactosEsperadosService>(ImpactosEsperadosService);

      wrapper = shallowMount<ImpactosEsperadosClass>(ImpactosEsperadosUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          impactosEsperadosService: () => impactosEsperadosServiceStub,

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.impactosEsperados = entity;
        impactosEsperadosServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(impactosEsperadosServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.impactosEsperados = entity;
        impactosEsperadosServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(impactosEsperadosServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
