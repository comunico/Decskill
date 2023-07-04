<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="price">
        <h2 class="jh-entity-heading" data-cy="priceDetailsHeading"><span>Price</span> {{ price.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Start Date</span>
          </dt>
          <dd>
            <span v-if="price.startDate">{{ formatDateLong(price.startDate) }}</span>
          </dd>
          <dt>
            <span>End Date</span>
          </dt>
          <dd>
            <span v-if="price.endDate">{{ formatDateLong(price.endDate) }}</span>
          </dd>
          <dt>
            <span>Priority</span>
          </dt>
          <dd>
            <span>{{ price.priority }}</span>
          </dd>
          <dt>
            <span>Price</span>
          </dt>
          <dd>
            <span>{{ price.price }}</span>
          </dd>
          <dt>
            <span>Currency</span>
          </dt>
          <dd>
            <span>{{ price.currency }}</span>
          </dd>
          <dt>
            <span>Brand</span>
          </dt>
          <dd>
            <div v-if="price.brand">
              <router-link :to="{ name: 'BrandView', params: { brandId: price.brand.id } }">{{ price.brand.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span>Product</span>
          </dt>
          <dd>
            <div v-if="price.product">
              <router-link :to="{ name: 'ProductView', params: { productId: price.product.id } }">{{ price.product.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>Volver</span>
        </button>
        <router-link v-if="price.id" :to="{ name: 'PriceEdit', params: { priceId: price.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>Editar</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./price-details.component.ts"></script>
