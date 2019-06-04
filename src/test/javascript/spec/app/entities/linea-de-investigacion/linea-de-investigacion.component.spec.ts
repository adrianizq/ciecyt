/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { LineaDeInvestigacionComponent } from 'app/entities/linea-de-investigacion/linea-de-investigacion.component';
import { LineaDeInvestigacionService } from 'app/entities/linea-de-investigacion/linea-de-investigacion.service';
import { LineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';

describe('Component Tests', () => {
  describe('LineaDeInvestigacion Management Component', () => {
    let comp: LineaDeInvestigacionComponent;
    let fixture: ComponentFixture<LineaDeInvestigacionComponent>;
    let service: LineaDeInvestigacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [LineaDeInvestigacionComponent],
        providers: []
      })
        .overrideTemplate(LineaDeInvestigacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LineaDeInvestigacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LineaDeInvestigacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LineaDeInvestigacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.lineaDeInvestigacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
