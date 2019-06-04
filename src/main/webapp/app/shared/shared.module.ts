import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CiecytSharedLibsModule, CiecytSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [CiecytSharedLibsModule, CiecytSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [CiecytSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytSharedModule {
  static forRoot() {
    return {
      ngModule: CiecytSharedModule
    };
  }
}
