import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  IntegrantesProyectoComponent,
  IntegrantesProyectoDetailComponent,
  IntegrantesProyectoUpdateComponent,
  IntegrantesProyectoDeletePopupComponent,
  IntegrantesProyectoDeleteDialogComponent,
  integrantesProyectoRoute,
  integrantesProyectoPopupRoute
} from './';

const ENTITY_STATES = [...integrantesProyectoRoute, ...integrantesProyectoPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IntegrantesProyectoComponent,
    IntegrantesProyectoDetailComponent,
    IntegrantesProyectoUpdateComponent,
    IntegrantesProyectoDeleteDialogComponent,
    IntegrantesProyectoDeletePopupComponent
  ],
  entryComponents: [
    IntegrantesProyectoComponent,
    IntegrantesProyectoUpdateComponent,
    IntegrantesProyectoDeleteDialogComponent,
    IntegrantesProyectoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytIntegrantesProyectoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
