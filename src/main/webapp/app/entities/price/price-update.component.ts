import { computed, defineComponent, inject, ref, Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import { useValidation, useDateFormat } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import BrandService from '@/entities/brand/brand.service';
import { IBrand } from '@/shared/model/brand.model';
import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';
import { IPrice, Price } from '@/shared/model/price.model';
import PriceService from './price.service';
import { Currecny } from '@/shared/model/enumerations/currecny.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PriceUpdate',
  setup() {
    const priceService = inject('priceService', () => new PriceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const price: Ref<IPrice> = ref(new Price());
    const brandService = inject('brandService', () => new BrandService());
    const brands: Ref<IBrand[]> = ref([]);
    const productService = inject('productService', () => new ProductService());
    const products: Ref<IProduct[]> = ref([]);
    const currecnyValues: Ref<string[]> = ref(Object.keys(Currecny));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePrice = async priceId => {
      try {
        const res = await priceService().find(priceId);
        res.startDate = new Date(res.startDate);
        res.endDate = new Date(res.endDate);
        price.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.priceId) {
      retrievePrice(route.params.priceId);
    }

    const initRelationships = () => {
      brandService()
        .retrieve()
        .then(res => {
          brands.value = res.data;
        });
      productService()
        .retrieve()
        .then(res => {
          products.value = res.data;
        });
    };

    initRelationships();

    const validations = useValidation();
    const validationRules = {
      startDate: {},
      endDate: {},
      priority: {},
      price: {},
      currency: {},
      brand: {},
      product: {},
    };
    const v$ = useVuelidate(validationRules, price as any);
    v$.value.$validate();

    return {
      priceService,
      alertService,
      price,
      previousState,
      currecnyValues,
      isSaving,
      currentLanguage,
      brands,
      products,
      v$,
      ...useDateFormat({ entityRef: price }),
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.price.id) {
        this.priceService()
          .update(this.price)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo('A Price is updated with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.priceService()
          .create(this.price)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess('A Price is created with identifier ' + param.id);
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
