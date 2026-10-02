import { Prop, Vue, Component, Watch } from 'vue-facing-decorator';

@Component
export default class MenuUpdate extends Vue {
  // dataModel: boolean;

  @Prop({
    default: ['No', 'Si'],
  })
  options: object;

  @Prop() default: boolean;

  /*created() {
    console.log('Cargando la información: ' + this.default);
    this.dataModel = this.default;
  }*/

  get dataModel() {
    return this.default;
  }

  set dataModel(value: boolean) {}

  public change(value) {
    this.$emit('change', value);
  }
}
