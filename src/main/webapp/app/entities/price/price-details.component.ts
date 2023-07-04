import { defineComponent, inject, ref, Ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

import { useDateFormat } from '@/shared/composables';
import { IPrice } from '@/shared/model/price.model';
import PriceService from './price.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PriceDetails',
  setup() {
    const dateFormat = useDateFormat();
    const priceService = inject('priceService', () => new PriceService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const price: Ref<IPrice> = ref({});

    const retrievePrice = async priceId => {
      try {
        const res = await priceService().find(priceId);
        price.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.priceId) {
      retrievePrice(route.params.priceId);
    }

    return {
      ...dateFormat,
      alertService,
      price,

      previousState,
    };
  },
});
