/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SolicitudDetailComponent from '@/entities/solicitud/solicitud-details.vue';
import SolicitudClass from '@/entities/solicitud/solicitud-details.component';
import SolicitudService from '@/entities/solicitud/solicitud.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Solicitud Management Detail Component', () => {
    let wrapper: Wrapper<SolicitudClass>;
    let comp: SolicitudClass;
    let solicitudServiceStub: SinonStubbedInstance<SolicitudService>;

    beforeEach(() => {
      solicitudServiceStub = sinon.createStubInstance<SolicitudService>(SolicitudService);

      wrapper = shallowMount<SolicitudClass>(SolicitudDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { solicitudService: () => solicitudServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSolicitud = { id: 123 };
        solicitudServiceStub.find.resolves(foundSolicitud);

        // WHEN
        comp.retrieveSolicitud(123);
        await comp.$nextTick();

        // THEN
        expect(comp.solicitud).toBe(foundSolicitud);
      });
    });
  });
});
