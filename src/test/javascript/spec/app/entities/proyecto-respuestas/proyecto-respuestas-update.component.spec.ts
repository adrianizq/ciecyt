/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProyectoRespuestasUpdateComponent from '@/entities/proyecto-respuestas/proyecto-respuestas-update.vue';
import ProyectoRespuestasClass from '@/entities/proyecto-respuestas/proyecto-respuestas-update.component';
import ProyectoRespuestasService from '@/entities/proyecto-respuestas/proyecto-respuestas.service';

import PreguntaService from '@/entities/pregunta/pregunta.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('ProyectoRespuestas Management Update Component', () => {
    let wrapper: Wrapper<ProyectoRespuestasClass>;
    let comp: ProyectoRespuestasClass;
    let proyectoRespuestasServiceStub: SinonStubbedInstance<ProyectoRespuestasService>;

    beforeEach(() => {
      proyectoRespuestasServiceStub = sinon.createStubInstance<ProyectoRespuestasService>(ProyectoRespuestasService);

      wrapper = shallowMount<ProyectoRespuestasClass>(ProyectoRespuestasUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          proyectoRespuestasService: () => proyectoRespuestasServiceStub,

          preguntaService: () => new PreguntaService(),

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.proyectoRespuestas = entity;
        proyectoRespuestasServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoRespuestasServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.proyectoRespuestas = entity;
        proyectoRespuestasServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoRespuestasServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
