/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { EmpresasComponent } from 'app/entities/empresas/empresas.component';
import { EmpresasService } from 'app/entities/empresas/empresas.service';
import { Empresas } from 'app/shared/model/empresas.model';

describe('Component Tests', () => {
  describe('Empresas Management Component', () => {
    let comp: EmpresasComponent;
    let fixture: ComponentFixture<EmpresasComponent>;
    let service: EmpresasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [EmpresasComponent],
        providers: []
      })
        .overrideTemplate(EmpresasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpresasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpresasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Empresas(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.empresas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
