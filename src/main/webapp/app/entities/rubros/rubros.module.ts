import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  RubrosComponent,
  RubrosDetailComponent,
  RubrosUpdateComponent,
  RubrosDeletePopupComponent,
  RubrosDeleteDialogComponent,
  rubrosRoute,
  rubrosPopupRoute
} from './';

const ENTITY_STATES = [...rubrosRoute, ...rubrosPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [RubrosComponent, RubrosDetailComponent, RubrosUpdateComponent, RubrosDeleteDialogComponent, RubrosDeletePopupComponent],
  entryComponents: [RubrosComponent, RubrosUpdateComponent, RubrosDeleteDialogComponent, RubrosDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytRubrosModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
