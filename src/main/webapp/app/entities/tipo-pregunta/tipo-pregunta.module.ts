import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  TipoPreguntaComponent,
  TipoPreguntaDetailComponent,
  TipoPreguntaUpdateComponent,
  TipoPreguntaDeletePopupComponent,
  TipoPreguntaDeleteDialogComponent,
  tipoPreguntaRoute,
  tipoPreguntaPopupRoute
} from './';

const ENTITY_STATES = [...tipoPreguntaRoute, ...tipoPreguntaPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipoPreguntaComponent,
    TipoPreguntaDetailComponent,
    TipoPreguntaUpdateComponent,
    TipoPreguntaDeleteDialogComponent,
    TipoPreguntaDeletePopupComponent
  ],
  entryComponents: [
    TipoPreguntaComponent,
    TipoPreguntaUpdateComponent,
    TipoPreguntaDeleteDialogComponent,
    TipoPreguntaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytTipoPreguntaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
