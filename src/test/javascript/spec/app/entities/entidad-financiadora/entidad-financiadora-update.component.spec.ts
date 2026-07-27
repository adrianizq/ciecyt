/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import EntidadFinanciadoraUpdateComponent from '@/entities/entidad-financiadora/entidad-financiadora-update.vue';
import EntidadFinanciadoraClass from '@/entities/entidad-financiadora/entidad-financiadora-update.component';
import EntidadFinanciadoraService from '@/entities/entidad-financiadora/entidad-financiadora.service';

import EntidadService from '@/entities/entidad/entidad.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('EntidadFinanciadora Management Update Component', () => {
    let wrapper: Wrapper<EntidadFinanciadoraClass>;
    let comp: EntidadFinanciadoraClass;
    let entidadFinanciadoraServiceStub: SinonStubbedInstance<EntidadFinanciadoraService>;

    beforeEach(() => {
      entidadFinanciadoraServiceStub = sinon.createStubInstance<EntidadFinanciadoraService>(EntidadFinanciadoraService);

      wrapper = shallowMount<EntidadFinanciadoraClass>(EntidadFinanciadoraUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          entidadFinanciadoraService: () => entidadFinanciadoraServiceStub,

          entidadService: () => new EntidadService(),

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.entidadFinanciadora = entity;
        entidadFinanciadoraServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entidadFinanciadoraServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.entidadFinanciadora = entity;
        entidadFinanciadoraServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(entidadFinanciadoraServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
