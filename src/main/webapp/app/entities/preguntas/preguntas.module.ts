import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  PreguntasComponent,
  PreguntasDetailComponent,
  PreguntasUpdateComponent,
  PreguntasDeletePopupComponent,
  PreguntasDeleteDialogComponent,
  preguntasRoute,
  preguntasPopupRoute
} from './';

const ENTITY_STATES = [...preguntasRoute, ...preguntasPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PreguntasComponent,
    PreguntasDetailComponent,
    PreguntasUpdateComponent,
    PreguntasDeleteDialogComponent,
    PreguntasDeletePopupComponent
  ],
  entryComponents: [PreguntasComponent, PreguntasUpdateComponent, PreguntasDeleteDialogComponent, PreguntasDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytPreguntasModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
