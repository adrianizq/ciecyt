import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  FichaTecnicaComponent,
  FichaTecnicaDetailComponent,
  FichaTecnicaUpdateComponent,
  FichaTecnicaDeletePopupComponent,
  FichaTecnicaDeleteDialogComponent,
  fichaTecnicaRoute,
  fichaTecnicaPopupRoute
} from './';

const ENTITY_STATES = [...fichaTecnicaRoute, ...fichaTecnicaPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FichaTecnicaComponent,
    FichaTecnicaDetailComponent,
    FichaTecnicaUpdateComponent,
    FichaTecnicaDeleteDialogComponent,
    FichaTecnicaDeletePopupComponent
  ],
  entryComponents: [
    FichaTecnicaComponent,
    FichaTecnicaUpdateComponent,
    FichaTecnicaDeleteDialogComponent,
    FichaTecnicaDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytFichaTecnicaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
