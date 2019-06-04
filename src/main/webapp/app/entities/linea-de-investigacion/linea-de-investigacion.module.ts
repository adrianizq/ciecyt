import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  LineaDeInvestigacionComponent,
  LineaDeInvestigacionDetailComponent,
  LineaDeInvestigacionUpdateComponent,
  LineaDeInvestigacionDeletePopupComponent,
  LineaDeInvestigacionDeleteDialogComponent,
  lineaDeInvestigacionRoute,
  lineaDeInvestigacionPopupRoute
} from './';

const ENTITY_STATES = [...lineaDeInvestigacionRoute, ...lineaDeInvestigacionPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LineaDeInvestigacionComponent,
    LineaDeInvestigacionDetailComponent,
    LineaDeInvestigacionUpdateComponent,
    LineaDeInvestigacionDeleteDialogComponent,
    LineaDeInvestigacionDeletePopupComponent
  ],
  entryComponents: [
    LineaDeInvestigacionComponent,
    LineaDeInvestigacionUpdateComponent,
    LineaDeInvestigacionDeleteDialogComponent,
    LineaDeInvestigacionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytLineaDeInvestigacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
