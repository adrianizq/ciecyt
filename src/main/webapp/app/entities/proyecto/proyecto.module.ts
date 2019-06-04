import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ProyectoComponent,
  ProyectoDetailComponent,
  ProyectoUpdateComponent,
  ProyectoDeletePopupComponent,
  ProyectoDeleteDialogComponent,
  proyectoRoute,
  proyectoPopupRoute
} from './';

const ENTITY_STATES = [...proyectoRoute, ...proyectoPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProyectoComponent,
    ProyectoDetailComponent,
    ProyectoUpdateComponent,
    ProyectoDeleteDialogComponent,
    ProyectoDeletePopupComponent
  ],
  entryComponents: [ProyectoComponent, ProyectoUpdateComponent, ProyectoDeleteDialogComponent, ProyectoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytProyectoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
