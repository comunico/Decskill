<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="ecommerceApp.price.home.createOrEditLabel" data-cy="PriceCreateUpdateHeading">Crear o editar Price</h2>
        <div>
          <div class="form-group" v-if="price.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="price.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-startDate">Start Date</label>
            <div class="d-flex">
              <input
                id="price-startDate"
                data-cy="startDate"
                type="datetime-local"
                class="form-control"
                name="startDate"
                :class="{ valid: !v$.startDate.$invalid, invalid: v$.startDate.$invalid }"
                :value="convertDateTimeFromServer(v$.startDate.$model)"
                @change="updateInstantField('startDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-endDate">End Date</label>
            <div class="d-flex">
              <input
                id="price-endDate"
                data-cy="endDate"
                type="datetime-local"
                class="form-control"
                name="endDate"
                :class="{ valid: !v$.endDate.$invalid, invalid: v$.endDate.$invalid }"
                :value="convertDateTimeFromServer(v$.endDate.$model)"
                @change="updateInstantField('endDate', $event)"
              />
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-priority">Priority</label>
            <input
              type="number"
              class="form-control"
              name="priority"
              id="price-priority"
              data-cy="priority"
              :class="{ valid: !v$.priority.$invalid, invalid: v$.priority.$invalid }"
              v-model.number="v$.priority.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-price">Price</label>
            <input
              type="number"
              class="form-control"
              name="price"
              id="price-price"
              data-cy="price"
              :class="{ valid: !v$.price.$invalid, invalid: v$.price.$invalid }"
              v-model.number="v$.price.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-currency">Currency</label>
            <select
              class="form-control"
              name="currency"
              :class="{ valid: !v$.currency.$invalid, invalid: v$.currency.$invalid }"
              v-model="v$.currency.$model"
              id="price-currency"
              data-cy="currency"
            >
              <option v-for="currecny in currecnyValues" :key="currecny" v-bind:value="currecny">{{ currecny }}</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-brand">Brand</label>
            <select class="form-control" id="price-brand" data-cy="brand" name="brand" v-model="price.brand">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="price.brand && brandOption.id === price.brand.id ? price.brand : brandOption"
                v-for="brandOption in brands"
                :key="brandOption.id"
              >
                {{ brandOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="price-product">Product</label>
            <select class="form-control" id="price-product" data-cy="product" name="product" v-model="price.product">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="price.product && productOption.id === price.product.id ? price.product : productOption"
                v-for="productOption in products"
                :key="productOption.id"
              >
                {{ productOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancelar</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./price-update.component.ts"></script>
