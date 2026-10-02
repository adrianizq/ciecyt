import { Component, Vue } from 'vue-facing-decorator';

@Component
export default class Ribbon extends Vue {
  public get ribbonEnv(): string {
    return this.$store.getters.ribbonOnProfiles;
  }

  public get ribbonEnabled(): boolean {
    return this.$store.getters.ribbonOnProfiles && this.$store.getters.activeProfiles.indexOf(this.$store.getters.ribbonOnProfiles) > -1;
  }
}
