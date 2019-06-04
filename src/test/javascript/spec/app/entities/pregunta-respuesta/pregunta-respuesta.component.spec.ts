/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { PreguntaRespuestaComponent } from 'app/entities/pregunta-respuesta/pregunta-respuesta.component';
import { PreguntaRespuestaService } from 'app/entities/pregunta-respuesta/pregunta-respuesta.service';
import { PreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

describe('Component Tests', () => {
  describe('PreguntaRespuesta Management Component', () => {
    let comp: PreguntaRespuestaComponent;
    let fixture: ComponentFixture<PreguntaRespuestaComponent>;
    let service: PreguntaRespuestaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntaRespuestaComponent],
        providers: []
      })
        .overrideTemplate(PreguntaRespuestaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PreguntaRespuestaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PreguntaRespuestaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PreguntaRespuesta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.preguntaRespuestas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
