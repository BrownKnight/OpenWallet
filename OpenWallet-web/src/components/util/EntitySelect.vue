<template>
  <b-input-group :prepend="label" class="mt-2">
    <label class="sr-only" :id="`input-label-${id}`" :for="`input-${id}`">{{ label }}</label>
    <b-form-select :id="id" v-model="nestedValue" required :options="options"></b-form-select>
  </b-input-group>
</template>

<script lang="ts">
import "reflect-metadata";
import { Component, Prop, Watch } from "vue-property-decorator";
import { BaseComponent } from "@/components/BaseComponent";

@Component({
  components: {}
})
export default class EntitySelect extends BaseComponent {
  @Prop({ required: true })
  id: string | undefined;

  @Prop()
  value: string | undefined;

  @Prop({ required: true })
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  optionsFunction!: () => Promise<any[]>;

  @Prop({ required: true })
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  optionTextFunction!: (entity: any) => string;

  options: Array<{ value: string; text: string }> = [];

  @Prop({ required: true })
  label!: string;

  get nestedValue() {
    return this.value;
  }

  set nestedValue(nestedValue) {
    this.$emit("input", nestedValue);
  }

  @Watch("optionsEndpoint")
  async loadOptionList() {
    const entities = await this.optionsFunction();
    this.options = entities.map(entity => {
      return { value: entity, text: this.optionTextFunction(entity) };
    });
  }

  created() {
    this.loadOptionList();
  }
}
</script>

<style lang="scss"></style>
