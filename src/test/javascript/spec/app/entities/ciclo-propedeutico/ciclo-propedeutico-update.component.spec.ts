/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import CicloPropedeuticoUpdateComponent from '@/entities/ciclo-propedeutico/ciclo-propedeutico-update.vue';
import CicloPropedeuticoClass from '@/entities/ciclo-propedeutico/ciclo-propedeutico-update.component';
import CicloPropedeuticoService from '@/entities/ciclo-propedeutico/ciclo-propedeutico.service';

import AcuerdoService from '@/entities/acuerdo/acuerdo.service';

import ModalidadService from '@/entities/modalidad/modalidad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('CicloPropedeutico Management Update Component', () => {
    let wrapper: Wrapper<CicloPropedeuticoClass>;
    let comp: CicloPropedeuticoClass;
    let cicloPropedeuticoServiceStub: SinonStubbedInstance<CicloPropedeuticoService>;

    beforeEach(() => {
      cicloPropedeuticoServiceStub = sinon.createStubInstance<CicloPropedeuticoService>(CicloPropedeuticoService);

      wrapper = shallowMount<CicloPropedeuticoClass>(CicloPropedeuticoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          cicloPropedeuticoService: () => cicloPropedeuticoServiceStub,

          acuerdoService: () => new AcuerdoService(),

          modalidadService: () => new ModalidadService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.cicloPropedeutico = entity;
        cicloPropedeuticoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cicloPropedeuticoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.cicloPropedeutico = entity;
        cicloPropedeuticoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(cicloPropedeuticoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
