<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2 id="ecommerceApp.brand.home.createOrEditLabel" data-cy="BrandCreateUpdateHeading">Crear o editar Brand</h2>
        <div>
          <div class="form-group" v-if="brand.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="brand.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="brand-name">Name</label>
            <input
              type="text"
              class="form-control"
              name="name"
              id="brand-name"
              data-cy="name"
              :class="{ valid: !v$.name.$invalid, invalid: v$.name.$invalid }"
              v-model="v$.name.$model"
            />
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
<script lang="ts" src="./brand-update.component.ts"></script>
