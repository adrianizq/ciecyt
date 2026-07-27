/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CronogramaDetailComponent from '@/entities/cronograma/cronograma-details.vue';
import CronogramaClass from '@/entities/cronograma/cronograma-details.component';
import CronogramaService from '@/entities/cronograma/cronograma.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Cronograma Management Detail Component', () => {
    let wrapper: Wrapper<CronogramaClass>;
    let comp: CronogramaClass;
    let cronogramaServiceStub: SinonStubbedInstance<CronogramaService>;

    beforeEach(() => {
      cronogramaServiceStub = sinon.createStubInstance<CronogramaService>(CronogramaService);

      wrapper = shallowMount<CronogramaClass>(CronogramaDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cronogramaService: () => cronogramaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCronograma = { id: 123 };
        cronogramaServiceStub.find.resolves(foundCronograma);

        // WHEN
        comp.retrieveCronograma(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cronograma).toBe(foundCronograma);
      });
    });
  });
});
