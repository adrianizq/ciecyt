import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  CategorizacionComponent,
  CategorizacionDetailComponent,
  CategorizacionUpdateComponent,
  CategorizacionDeletePopupComponent,
  CategorizacionDeleteDialogComponent,
  categorizacionRoute,
  categorizacionPopupRoute
} from './';

const ENTITY_STATES = [...categorizacionRoute, ...categorizacionPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CategorizacionComponent,
    CategorizacionDetailComponent,
    CategorizacionUpdateComponent,
    CategorizacionDeleteDialogComponent,
    CategorizacionDeletePopupComponent
  ],
  entryComponents: [
    CategorizacionComponent,
    CategorizacionUpdateComponent,
    CategorizacionDeleteDialogComponent,
    CategorizacionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytCategorizacionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
