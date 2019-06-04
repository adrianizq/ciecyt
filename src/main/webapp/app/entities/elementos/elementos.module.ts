import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ElementosComponent,
  ElementosDetailComponent,
  ElementosUpdateComponent,
  ElementosDeletePopupComponent,
  ElementosDeleteDialogComponent,
  elementosRoute,
  elementosPopupRoute
} from './';

const ENTITY_STATES = [...elementosRoute, ...elementosPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ElementosComponent,
    ElementosDetailComponent,
    ElementosUpdateComponent,
    ElementosDeleteDialogComponent,
    ElementosDeletePopupComponent
  ],
  entryComponents: [ElementosComponent, ElementosUpdateComponent, ElementosDeleteDialogComponent, ElementosDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytElementosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
