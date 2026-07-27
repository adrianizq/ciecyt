/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import SolicitudUpdateComponent from '@/entities/solicitud/solicitud-update.vue';
import SolicitudClass from '@/entities/solicitud/solicitud-update.component';
import SolicitudService from '@/entities/solicitud/solicitud.service';

import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Solicitud Management Update Component', () => {
    let wrapper: Wrapper<SolicitudClass>;
    let comp: SolicitudClass;
    let solicitudServiceStub: SinonStubbedInstance<SolicitudService>;

    beforeEach(() => {
      solicitudServiceStub = sinon.createStubInstance<SolicitudService>(SolicitudService);

      wrapper = shallowMount<SolicitudClass>(SolicitudUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          solicitudService: () => solicitudServiceStub,

          integranteProyectoService: () => new IntegranteProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.solicitud = entity;
        solicitudServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(solicitudServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.solicitud = entity;
        solicitudServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(solicitudServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
