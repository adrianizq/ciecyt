/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FacultadDetailComponent from '@/entities/facultad/facultad-details.vue';
import FacultadClass from '@/entities/facultad/facultad-details.component';
import FacultadService from '@/entities/facultad/facultad.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Facultad Management Detail Component', () => {
    let wrapper: Wrapper<FacultadClass>;
    let comp: FacultadClass;
    let facultadServiceStub: SinonStubbedInstance<FacultadService>;

    beforeEach(() => {
      facultadServiceStub = sinon.createStubInstance<FacultadService>(FacultadService);

      wrapper = shallowMount<FacultadClass>(FacultadDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { facultadService: () => facultadServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFacultad = { id: 123 };
        facultadServiceStub.find.resolves(foundFacultad);

        // WHEN
        comp.retrieveFacultad(123);
        await comp.$nextTick();

        // THEN
        expect(comp.facultad).toBe(foundFacultad);
      });
    });
  });
});
