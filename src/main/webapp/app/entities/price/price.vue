<template>
  <div>
    <h2 id="page-heading" data-cy="PriceHeading">
      <span id="price-heading">Prices</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refrescar lista</span>
        </button>
        <router-link :to="{ name: 'PriceCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-price"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Crear nuevo Price</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && prices && prices.length === 0">
      <span>Ningún Prices encontrado</span>
    </div>
    <div class="table-responsive" v-if="prices && prices.length > 0">
      <table class="table table-striped" aria-describedby="prices">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Start Date</span></th>
            <th scope="row"><span>End Date</span></th>
            <th scope="row"><span>Priority</span></th>
            <th scope="row"><span>Price</span></th>
            <th scope="row"><span>Currency</span></th>
            <th scope="row"><span>Brand</span></th>
            <th scope="row"><span>Product</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="price in prices" :key="price.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PriceView', params: { priceId: price.id } }">{{ price.id }}</router-link>
            </td>
            <td>{{ formatDateShort(price.startDate) || '' }}</td>
            <td>{{ formatDateShort(price.endDate) || '' }}</td>
            <td>{{ price.priority }}</td>
            <td>{{ price.price }}</td>
            <td>{{ price.currency }}</td>
            <td>
              <div v-if="price.brand">
                <router-link :to="{ name: 'BrandView', params: { brandId: price.brand.id } }">{{ price.brand.id }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="price.product">
                <router-link :to="{ name: 'ProductView', params: { productId: price.product.id } }">{{ price.product.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PriceView', params: { priceId: price.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">Vista</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PriceEdit', params: { priceId: price.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Editar</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(price)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Eliminar</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span id="ecommerceApp.price.delete.question" data-cy="priceDeleteDialogHeading">Confirmar operación de borrado</span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-price-heading">¿Seguro que quiere eliminar Price {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancelar</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-price"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removePrice()"
          >
            Eliminar
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./price.component.ts"></script>
