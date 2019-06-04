/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { EntidadFinanciadoraComponent } from 'app/entities/entidad-financiadora/entidad-financiadora.component';
import { EntidadFinanciadoraService } from 'app/entities/entidad-financiadora/entidad-financiadora.service';
import { EntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';

describe('Component Tests', () => {
  describe('EntidadFinanciadora Management Component', () => {
    let comp: EntidadFinanciadoraComponent;
    let fixture: ComponentFixture<EntidadFinanciadoraComponent>;
    let service: EntidadFinanciadoraService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [EntidadFinanciadoraComponent],
        providers: []
      })
        .overrideTemplate(EntidadFinanciadoraComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EntidadFinanciadoraComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EntidadFinanciadoraService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EntidadFinanciadora(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.entidadFinanciadoras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
