/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { IntegrantesProyectoComponent } from 'app/entities/integrantes-proyecto/integrantes-proyecto.component';
import { IntegrantesProyectoService } from 'app/entities/integrantes-proyecto/integrantes-proyecto.service';
import { IntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

describe('Component Tests', () => {
  describe('IntegrantesProyecto Management Component', () => {
    let comp: IntegrantesProyectoComponent;
    let fixture: ComponentFixture<IntegrantesProyectoComponent>;
    let service: IntegrantesProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [IntegrantesProyectoComponent],
        providers: []
      })
        .overrideTemplate(IntegrantesProyectoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(IntegrantesProyectoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(IntegrantesProyectoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new IntegrantesProyecto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.integrantesProyectos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
