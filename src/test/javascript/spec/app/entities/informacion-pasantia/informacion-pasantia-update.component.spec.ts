/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import AlertService from '@/shared/alert/alert.service';
import * as config from '@/shared/config/config';
import InformacionPasantiaUpdateComponent from '@/entities/informacion-pasantia/informacion-pasantia-update.vue';
import InformacionPasantiaClass from '@/entities/informacion-pasantia/informacion-pasantia-update.component';
import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';

import ProyectoService from '@/entities/proyecto/proyecto.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});

describe('Component Tests', () => {
  describe('InformacionPasantia Management Update Component', () => {
    let wrapper: Wrapper<InformacionPasantiaClass>;
    let comp: InformacionPasantiaClass;
    let informacionPasantiaServiceStub: SinonStubbedInstance<InformacionPasantiaService>;

    beforeEach(() => {
      informacionPasantiaServiceStub = sinon.createStubInstance<InformacionPasantiaService>(InformacionPasantiaService);

      wrapper = shallowMount<InformacionPasantiaClass>(InformacionPasantiaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          alertService: () => new AlertService(store),
          informacionPasantiaService: () => informacionPasantiaServiceStub,

          proyectoService: () => new ProyectoService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.informacionPasantia = entity;
        informacionPasantiaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(informacionPasantiaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.informacionPasantia = entity;
        informacionPasantiaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(informacionPasantiaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });
  });
});
