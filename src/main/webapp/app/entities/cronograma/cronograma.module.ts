import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  CronogramaComponent,
  CronogramaDetailComponent,
  CronogramaUpdateComponent,
  CronogramaDeletePopupComponent,
  CronogramaDeleteDialogComponent,
  cronogramaRoute,
  cronogramaPopupRoute
} from './';

const ENTITY_STATES = [...cronogramaRoute, ...cronogramaPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CronogramaComponent,
    CronogramaDetailComponent,
    CronogramaUpdateComponent,
    CronogramaDeleteDialogComponent,
    CronogramaDeletePopupComponent
  ],
  entryComponents: [CronogramaComponent, CronogramaUpdateComponent, CronogramaDeleteDialogComponent, CronogramaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytCronogramaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
