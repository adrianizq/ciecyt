/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { PreguntasComponent } from 'app/entities/preguntas/preguntas.component';
import { PreguntasService } from 'app/entities/preguntas/preguntas.service';
import { Preguntas } from 'app/shared/model/preguntas.model';

describe('Component Tests', () => {
  describe('Preguntas Management Component', () => {
    let comp: PreguntasComponent;
    let fixture: ComponentFixture<PreguntasComponent>;
    let service: PreguntasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntasComponent],
        providers: []
      })
        .overrideTemplate(PreguntasComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PreguntasComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PreguntasService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Preguntas(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.preguntas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
