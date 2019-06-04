import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ElementosProyectoComponent,
  ElementosProyectoDetailComponent,
  ElementosProyectoUpdateComponent,
  ElementosProyectoDeletePopupComponent,
  ElementosProyectoDeleteDialogComponent,
  elementosProyectoRoute,
  elementosProyectoPopupRoute
} from './';

const ENTITY_STATES = [...elementosProyectoRoute, ...elementosProyectoPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ElementosProyectoComponent,
    ElementosProyectoDetailComponent,
    ElementosProyectoUpdateComponent,
    ElementosProyectoDeleteDialogComponent,
    ElementosProyectoDeletePopupComponent
  ],
  entryComponents: [
    ElementosProyectoComponent,
    ElementosProyectoUpdateComponent,
    ElementosProyectoDeleteDialogComponent,
    ElementosProyectoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytElementosProyectoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
