import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  RolesUsuarioProyectoComponent,
  RolesUsuarioProyectoDetailComponent,
  RolesUsuarioProyectoUpdateComponent,
  RolesUsuarioProyectoDeletePopupComponent,
  RolesUsuarioProyectoDeleteDialogComponent,
  rolesUsuarioProyectoRoute,
  rolesUsuarioProyectoPopupRoute
} from './';

const ENTITY_STATES = [...rolesUsuarioProyectoRoute, ...rolesUsuarioProyectoPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    RolesUsuarioProyectoComponent,
    RolesUsuarioProyectoDetailComponent,
    RolesUsuarioProyectoUpdateComponent,
    RolesUsuarioProyectoDeleteDialogComponent,
    RolesUsuarioProyectoDeletePopupComponent
  ],
  entryComponents: [
    RolesUsuarioProyectoComponent,
    RolesUsuarioProyectoUpdateComponent,
    RolesUsuarioProyectoDeleteDialogComponent,
    RolesUsuarioProyectoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytRolesUsuarioProyectoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
