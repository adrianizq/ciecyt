import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CiecytSharedModule } from 'app/shared';
import {
  ProductoProyectoComponent,
  ProductoProyectoDetailComponent,
  ProductoProyectoUpdateComponent,
  ProductoProyectoDeletePopupComponent,
  ProductoProyectoDeleteDialogComponent,
  productoProyectoRoute,
  productoProyectoPopupRoute
} from './';

const ENTITY_STATES = [...productoProyectoRoute, ...productoProyectoPopupRoute];

@NgModule({
  imports: [CiecytSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductoProyectoComponent,
    ProductoProyectoDetailComponent,
    ProductoProyectoUpdateComponent,
    ProductoProyectoDeleteDialogComponent,
    ProductoProyectoDeletePopupComponent
  ],
  entryComponents: [
    ProductoProyectoComponent,
    ProductoProyectoUpdateComponent,
    ProductoProyectoDeleteDialogComponent,
    ProductoProyectoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CiecytProductoProyectoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
