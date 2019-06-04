import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  PresupuestoValorComponent,
  PresupuestoValorDetailComponent,
  PresupuestoValorUpdateComponent,
  PresupuestoValorDeletePopupComponent,
  PresupuestoValorDeleteDialogComponent,
  presupuestoValorRoute,
  presupuestoValorPopupRoute
} from './';

const ENTITY_STATES = [...presupuestoValorRoute, ...presupuestoValorPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PresupuestoValorComponent,
    PresupuestoValorDetailComponent,
    PresupuestoValorUpdateComponent,
    PresupuestoValorDeleteDialogComponent,
    PresupuestoValorDeletePopupComponent
  ],
  entryComponents: [
    PresupuestoValorComponent,
    PresupuestoValorUpdateComponent,
    PresupuestoValorDeleteDialogComponent,
    PresupuestoValorDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytPresupuestoValorModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
