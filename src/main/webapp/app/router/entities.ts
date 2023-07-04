import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Brand = () => import('@/entities/brand/brand.vue');
const BrandUpdate = () => import('@/entities/brand/brand-update.vue');
const BrandDetails = () => import('@/entities/brand/brand-details.vue');

const Product = () => import('@/entities/product/product.vue');
const ProductUpdate = () => import('@/entities/product/product-update.vue');
const ProductDetails = () => import('@/entities/product/product-details.vue');

const Price = () => import('@/entities/price/price.vue');
const PriceUpdate = () => import('@/entities/price/price-update.vue');
const PriceDetails = () => import('@/entities/price/price-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'brand',
      name: 'Brand',
      component: Brand,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand/new',
      name: 'BrandCreate',
      component: BrandUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand/:brandId/edit',
      name: 'BrandEdit',
      component: BrandUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'brand/:brandId/view',
      name: 'BrandView',
      component: BrandDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product',
      name: 'Product',
      component: Product,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/new',
      name: 'ProductCreate',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/edit',
      name: 'ProductEdit',
      component: ProductUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'product/:productId/view',
      name: 'ProductView',
      component: ProductDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'price',
      name: 'Price',
      component: Price,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'price/new',
      name: 'PriceCreate',
      component: PriceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'price/:priceId/edit',
      name: 'PriceEdit',
      component: PriceUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'price/:priceId/view',
      name: 'PriceView',
      component: PriceDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
