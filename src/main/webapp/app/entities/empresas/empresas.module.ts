import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  EmpresasComponent,
  EmpresasDetailComponent,
  EmpresasUpdateComponent,
  EmpresasDeletePopupComponent,
  EmpresasDeleteDialogComponent,
  empresasRoute,
  empresasPopupRoute
} from './';

const ENTITY_STATES = [...empresasRoute, ...empresasPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    EmpresasComponent,
    EmpresasDetailComponent,
    EmpresasUpdateComponent,
    EmpresasDeleteDialogComponent,
    EmpresasDeletePopupComponent
  ],
  entryComponents: [EmpresasComponent, EmpresasUpdateComponent, EmpresasDeleteDialogComponent, EmpresasDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytEmpresasModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
