/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import ModalidadUpdateComponent from '@/entities/modalidad/modalidad-update.vue';
import ModalidadClass from '@/entities/modalidad/modalidad-update.component';
import ModalidadService from '@/entities/modalidad/modalidad.service';

import CicloPropedeuticoService from '@/entities/ciclo-propedeutico/ciclo-propedeutico.service';

import AcuerdoService from '@/entities/acuerdo/acuerdo.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('Modalidad Management Update Component', () => {
    let wrapper: Wrapper<ModalidadClass>;
    let comp: ModalidadClass;
    let modalidadServiceStub: SinonStubbedInstance<ModalidadService>;

    beforeEach(() => {
      modalidadServiceStub = sinon.createStubInstance<ModalidadService>(ModalidadService);

      wrapper = shallowMount<ModalidadClass>(ModalidadUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          modalidadService: () => modalidadServiceStub,

          cicloPropedeuticoService: () => new CicloPropedeuticoService(),

          acuerdoService: () => new AcuerdoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.modalidad = entity;
        modalidadServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(modalidadServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.modalidad = entity;
        modalidadServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(modalidadServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
