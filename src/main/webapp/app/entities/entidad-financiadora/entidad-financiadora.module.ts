import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  EntidadFinanciadoraComponent,
  EntidadFinanciadoraDetailComponent,
  EntidadFinanciadoraUpdateComponent,
  EntidadFinanciadoraDeletePopupComponent,
  EntidadFinanciadoraDeleteDialogComponent,
  entidadFinanciadoraRoute,
  entidadFinanciadoraPopupRoute
} from './';

const ENTITY_STATES = [...entidadFinanciadoraRoute, ...entidadFinanciadoraPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EntidadFinanciadoraComponent,
    EntidadFinanciadoraDetailComponent,
    EntidadFinanciadoraUpdateComponent,
    EntidadFinanciadoraDeleteDialogComponent,
    EntidadFinanciadoraDeletePopupComponent
  ],
  entryComponents: [
    EntidadFinanciadoraComponent,
    EntidadFinanciadoraUpdateComponent,
    EntidadFinanciadoraDeleteDialogComponent,
    EntidadFinanciadoraDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytEntidadFinanciadoraModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
