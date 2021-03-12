<template>
  <b-input-group :prepend="label" class="mt-2">
    <label class="sr-only" :id="`input-label-${id}`" :for="`input-${id}`">{{ label }}</label>
    <b-form-input
      :id="`input-${id}`"
      v-model="nestedValue"
      :type="type"
      :autocomplete="autocomplete"
      :required="required"
    ></b-form-input>
  </b-input-group>
</template>

<script lang="ts">
import { Component, Prop } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";

@Component
export default class LabelledInput extends BaseComponent {
  @Prop({ required: true })
  id!: string;
  @Prop({ required: true })
  label!: string;
  @Prop({ required: true })
  type!: string;
  @Prop({ default: undefined })
  autocomplete!: string | undefined;
  @Prop({ default: false })
  required!: boolean;

  @Prop()
  value!: string;

  get nestedValue() {
    return this.value;
  }

  set nestedValue(nestedValue) {
    this.$emit("input", nestedValue);
  }
}
</script>

<style scoped lang="scss"></style>
