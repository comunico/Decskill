/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, MountingOptions } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { RouteLocation } from 'vue-router';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '../../../../../../main/webapp/app/shared/composables/date-format';
import PriceUpdate from '../../../../../../main/webapp/app/entities/price/price-update.vue';
import PriceService from '../../../../../../main/webapp/app/entities/price/price.service';
import AlertService from '../../../../../../main/webapp/app/shared/alert/alert.service';

import BrandService from '../../../../../../main/webapp/app/entities/brand/brand.service';
import ProductService from '../../../../../../main/webapp/app/entities/product/product.service';

type PriceUpdateComponentType = InstanceType<typeof PriceUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const priceSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PriceUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Price Management Update Component', () => {
    let comp: PriceUpdateComponentType;
    let priceServiceStub: SinonStubbedInstance<PriceService>;

    beforeEach(() => {
      route = {};
      priceServiceStub = sinon.createStubInstance<PriceService>(PriceService);

      alertService = new AlertService({
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          priceService: () => priceServiceStub,
          brandService: () =>
            sinon.createStubInstance<BrandService>(BrandService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          productService: () =>
            sinon.createStubInstance<ProductService>(ProductService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('load', () => {
      beforeEach(() => {
        const wrapper = shallowMount(PriceUpdate, { global: mountOptions });
        comp = wrapper.vm;
      });
      it('Should convert date from string', () => {
        // GIVEN
        const date = new Date('2019-10-15T11:42:02Z');

        // WHEN
        const convertedDate = comp.convertDateTimeFromServer(date);

        // THEN
        expect(convertedDate).toEqual(dayjs(date).format(DATE_TIME_LONG_FORMAT));
      });

      it('Should not convert date if date is not present', () => {
        expect(comp.convertDateTimeFromServer(null)).toBeNull();
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(PriceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.price = priceSample;
        priceServiceStub.update.resolves(priceSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(priceServiceStub.update.calledWith(priceSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        priceServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PriceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.price = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(priceServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        priceServiceStub.find.resolves(priceSample);
        priceServiceStub.retrieve.resolves([priceSample]);

        // WHEN
        route = {
          params: {
            priceId: '' + priceSample.id,
          },
        };
        const wrapper = shallowMount(PriceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.price).toMatchObject(priceSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        priceServiceStub.find.resolves(priceSample);
        const wrapper = shallowMount(PriceUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
