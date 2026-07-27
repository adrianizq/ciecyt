/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CronogramaCiecytFasesDetailComponent from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases-details.vue';
import CronogramaCiecytFasesClass from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases-details.component';
import CronogramaCiecytFasesService from '@/entities/cronograma-ciecyt-fases/cronograma-ciecyt-fases.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CronogramaCiecytFases Management Detail Component', () => {
    let wrapper: Wrapper<CronogramaCiecytFasesClass>;
    let comp: CronogramaCiecytFasesClass;
    let cronogramaCiecytFasesServiceStub: SinonStubbedInstance<CronogramaCiecytFasesService>;

    beforeEach(() => {
      cronogramaCiecytFasesServiceStub = sinon.createStubInstance<CronogramaCiecytFasesService>(CronogramaCiecytFasesService);

      wrapper = shallowMount<CronogramaCiecytFasesClass>(CronogramaCiecytFasesDetailComponent, {
        store,
        i18n,
        localVue,
        provide: { cronogramaCiecytFasesService: () => cronogramaCiecytFasesServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCronogramaCiecytFases = { id: 123 };
        cronogramaCiecytFasesServiceStub.find.resolves(foundCronogramaCiecytFases);

        // WHEN
        comp.retrieveCronogramaCiecytFases(123);
        await comp.$nextTick();

        // THEN
        expect(comp.cronogramaCiecytFases).toBe(foundCronogramaCiecytFases);
      });
    });
  });
});
