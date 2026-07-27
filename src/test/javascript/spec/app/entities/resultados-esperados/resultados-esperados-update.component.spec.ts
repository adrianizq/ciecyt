/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ResultadosEsperadosUpdateComponent from '@/entities/resultados-esperados/resultados-esperados-update.vue';
import ResultadosEsperadosClass from '@/entities/resultados-esperados/resultados-esperados-update.component';
import ResultadosEsperadosService from '@/entities/resultados-esperados/resultados-esperados.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ResultadosEsperados Management Update Component', () => {
    let wrapper: Wrapper<ResultadosEsperadosClass>;
    let comp: ResultadosEsperadosClass;
    let resultadosEsperadosServiceStub: SinonStubbedInstance<ResultadosEsperadosService>;

    beforeEach(() => {
      resultadosEsperadosServiceStub = sinon.createStubInstance<ResultadosEsperadosService>(ResultadosEsperadosService);

      wrapper = shallowMount<ResultadosEsperadosClass>(ResultadosEsperadosUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          resultadosEsperadosService: () => resultadosEsperadosServiceStub,

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.resultadosEsperados = entity;
        resultadosEsperadosServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resultadosEsperadosServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.resultadosEsperados = entity;
        resultadosEsperadosServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(resultadosEsperadosServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
