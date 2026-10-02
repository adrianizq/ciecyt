/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@/shared/test/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { createMemoryHistory, createRouter } from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import PreguntaUpdateComponent from '@/entities/pregunta/pregunta-update.vue';
import PreguntaClass from '@/entities/pregunta/pregunta-update.component';
import PreguntaService from '@/entities/pregunta/pregunta.service';

import TipoPreguntaService from '@/entities/tipo-pregunta/tipo-pregunta.service';

import ModalidadService from '@/entities/modalidad/modalidad.service';

import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';

const localVue = createLocalVue();

const i18n = config.initI18N();
const store = config.initVueXStore();
const router = createRouter({ history: createMemoryHistory(), routes: [] });
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Pregunta Management Update Component', () => {
    let wrapper: Wrapper<PreguntaClass>;
    let comp: PreguntaClass;
    let preguntaServiceStub: SinonStubbedInstance<PreguntaService>;

    beforeEach(() => {
      preguntaServiceStub = sinon.createStubInstance<PreguntaService>(PreguntaService);

      wrapper = shallowMount<PreguntaClass>(PreguntaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          preguntaService: () => preguntaServiceStub,

          tipoPreguntaService: () => new TipoPreguntaService(),

          modalidadService: () => new ModalidadService(),

          rolesModalidadService: () => new RolesModalidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.pregunta = entity;
        preguntaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(preguntaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.pregunta = entity;
        preguntaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(preguntaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
