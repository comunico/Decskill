import { defineComponent, provide } from 'vue';

import UserService from '@/entities/user/user.service';
import BrandService from './brand/brand.service';
import ProductService from './product/product.service';
import PriceService from './price/price.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('brandService', () => new BrandService());
    provide('productService', () => new ProductService());
    provide('priceService', () => new PriceService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
