import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ResultadosEsperadosComponent,
  ResultadosEsperadosDetailComponent,
  ResultadosEsperadosUpdateComponent,
  ResultadosEsperadosDeletePopupComponent,
  ResultadosEsperadosDeleteDialogComponent,
  resultadosEsperadosRoute,
  resultadosEsperadosPopupRoute
} from './';

const ENTITY_STATES = [...resultadosEsperadosRoute, ...resultadosEsperadosPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ResultadosEsperadosComponent,
    ResultadosEsperadosDetailComponent,
    ResultadosEsperadosUpdateComponent,
    ResultadosEsperadosDeleteDialogComponent,
    ResultadosEsperadosDeletePopupComponent
  ],
  entryComponents: [
    ResultadosEsperadosComponent,
    ResultadosEsperadosUpdateComponent,
    ResultadosEsperadosDeleteDialogComponent,
    ResultadosEsperadosDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytResultadosEsperadosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
