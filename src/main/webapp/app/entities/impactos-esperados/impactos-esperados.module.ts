import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ImpactosEsperadosComponent,
  ImpactosEsperadosDetailComponent,
  ImpactosEsperadosUpdateComponent,
  ImpactosEsperadosDeletePopupComponent,
  ImpactosEsperadosDeleteDialogComponent,
  impactosEsperadosRoute,
  impactosEsperadosPopupRoute
} from './';

const ENTITY_STATES = [...impactosEsperadosRoute, ...impactosEsperadosPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ImpactosEsperadosComponent,
    ImpactosEsperadosDetailComponent,
    ImpactosEsperadosUpdateComponent,
    ImpactosEsperadosDeleteDialogComponent,
    ImpactosEsperadosDeletePopupComponent
  ],
  entryComponents: [
    ImpactosEsperadosComponent,
    ImpactosEsperadosUpdateComponent,
    ImpactosEsperadosDeleteDialogComponent,
    ImpactosEsperadosDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytImpactosEsperadosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
