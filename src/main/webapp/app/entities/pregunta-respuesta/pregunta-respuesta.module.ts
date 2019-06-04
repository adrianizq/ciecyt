import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  PreguntaRespuestaComponent,
  PreguntaRespuestaDetailComponent,
  PreguntaRespuestaUpdateComponent,
  PreguntaRespuestaDeletePopupComponent,
  PreguntaRespuestaDeleteDialogComponent,
  preguntaRespuestaRoute,
  preguntaRespuestaPopupRoute
} from './';

const ENTITY_STATES = [...preguntaRespuestaRoute, ...preguntaRespuestaPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PreguntaRespuestaComponent,
    PreguntaRespuestaDetailComponent,
    PreguntaRespuestaUpdateComponent,
    PreguntaRespuestaDeleteDialogComponent,
    PreguntaRespuestaDeletePopupComponent
  ],
  entryComponents: [
    PreguntaRespuestaComponent,
    PreguntaRespuestaUpdateComponent,
    PreguntaRespuestaDeleteDialogComponent,
    PreguntaRespuestaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytPreguntaRespuestaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
