import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  FacultadComponent,
  FacultadDetailComponent,
  FacultadUpdateComponent,
  FacultadDeletePopupComponent,
  FacultadDeleteDialogComponent,
  facultadRoute,
  facultadPopupRoute
} from './';

const ENTITY_STATES = [...facultadRoute, ...facultadPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FacultadComponent,
    FacultadDetailComponent,
    FacultadUpdateComponent,
    FacultadDeleteDialogComponent,
    FacultadDeletePopupComponent
  ],
  entryComponents: [FacultadComponent, FacultadUpdateComponent, FacultadDeleteDialogComponent, FacultadDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytFacultadModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
