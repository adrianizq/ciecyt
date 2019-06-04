import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  GrupoSemilleroComponent,
  GrupoSemilleroDetailComponent,
  GrupoSemilleroUpdateComponent,
  GrupoSemilleroDeletePopupComponent,
  GrupoSemilleroDeleteDialogComponent,
  grupoSemilleroRoute,
  grupoSemilleroPopupRoute
} from './';

const ENTITY_STATES = [...grupoSemilleroRoute, ...grupoSemilleroPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    GrupoSemilleroComponent,
    GrupoSemilleroDetailComponent,
    GrupoSemilleroUpdateComponent,
    GrupoSemilleroDeleteDialogComponent,
    GrupoSemilleroDeletePopupComponent
  ],
  entryComponents: [
    GrupoSemilleroComponent,
    GrupoSemilleroUpdateComponent,
    GrupoSemilleroDeleteDialogComponent,
    GrupoSemilleroDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytGrupoSemilleroModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
