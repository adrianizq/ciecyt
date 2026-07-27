/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import AdjuntoProyectoFaseUpdateComponent from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase-update.vue';
import AdjuntoProyectoFaseClass from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase-update.component';
import AdjuntoProyectoFaseService from '@/entities/adjunto-proyecto-fase/adjunto-proyecto-fase.service';

import ProyectoFaseService from '@/entities/proyecto-fase/proyecto-fase.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('AdjuntoProyectoFase Management Update Component', () => {
    let wrapper: Wrapper<AdjuntoProyectoFaseClass>;
    let comp: AdjuntoProyectoFaseClass;
    let adjuntoProyectoFaseServiceStub: SinonStubbedInstance<AdjuntoProyectoFaseService>;

    beforeEach(() => {
      adjuntoProyectoFaseServiceStub = sinon.createStubInstance<AdjuntoProyectoFaseService>(AdjuntoProyectoFaseService);

      wrapper = shallowMount<AdjuntoProyectoFaseClass>(AdjuntoProyectoFaseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          adjuntoProyectoFaseService: () => adjuntoProyectoFaseServiceStub,

          proyectoFaseService: () => new ProyectoFaseService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.adjuntoProyectoFase = entity;
        adjuntoProyectoFaseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adjuntoProyectoFaseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.adjuntoProyectoFase = entity;
        adjuntoProyectoFaseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(adjuntoProyectoFaseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
