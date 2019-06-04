import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ModalidadDeGradoComponent,
  ModalidadDeGradoDetailComponent,
  ModalidadDeGradoUpdateComponent,
  ModalidadDeGradoDeletePopupComponent,
  ModalidadDeGradoDeleteDialogComponent,
  modalidadDeGradoRoute,
  modalidadDeGradoPopupRoute
} from './';

const ENTITY_STATES = [...modalidadDeGradoRoute, ...modalidadDeGradoPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ModalidadDeGradoComponent,
    ModalidadDeGradoDetailComponent,
    ModalidadDeGradoUpdateComponent,
    ModalidadDeGradoDeleteDialogComponent,
    ModalidadDeGradoDeletePopupComponent
  ],
  entryComponents: [
    ModalidadDeGradoComponent,
    ModalidadDeGradoUpdateComponent,
    ModalidadDeGradoDeleteDialogComponent,
    ModalidadDeGradoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytModalidadDeGradoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
