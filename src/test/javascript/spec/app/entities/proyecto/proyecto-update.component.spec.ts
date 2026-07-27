/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ProyectoUpdateComponent from '@/entities/proyecto/proyecto-update.vue';
import ProyectoClass from '@/entities/proyecto/proyecto-update.component';
import ProyectoService from '@/entities/proyecto/proyecto.service';

import LineaInvestigacionService from '@/entities/linea-investigacion/linea-investigacion.service';

import GrupoSemilleroService from '@/entities/grupo-semillero/grupo-semillero.service';

import ModalidadService from '@/entities/modalidad/modalidad.service';

import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';

import FacultadService from '@/entities/facultad/facultad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Proyecto Management Update Component', () => {
    let wrapper: Wrapper<ProyectoClass>;
    let comp: ProyectoClass;
    let proyectoServiceStub: SinonStubbedInstance<ProyectoService>;

    beforeEach(() => {
      proyectoServiceStub = sinon.createStubInstance<ProyectoService>(ProyectoService);

      wrapper = shallowMount<ProyectoClass>(ProyectoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          proyectoService: () => proyectoServiceStub,

          lineaInvestigacionService: () => new LineaInvestigacionService(),

          grupoSemilleroService: () => new GrupoSemilleroService(),

          modalidadService: () => new ModalidadService(),

          informacionPasantiaService: () => new InformacionPasantiaService(),

          facultadService: () => new FacultadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.proyecto = entity;
        proyectoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.proyecto = entity;
        proyectoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(proyectoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
