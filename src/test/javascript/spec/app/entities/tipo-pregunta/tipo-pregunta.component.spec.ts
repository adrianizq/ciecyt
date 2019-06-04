/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { TipoPreguntaComponent } from 'app/entities/tipo-pregunta/tipo-pregunta.component';
import { TipoPreguntaService } from 'app/entities/tipo-pregunta/tipo-pregunta.service';
import { TipoPregunta } from 'app/shared/model/tipo-pregunta.model';

describe('Component Tests', () => {
  describe('TipoPregunta Management Component', () => {
    let comp: TipoPreguntaComponent;
    let fixture: ComponentFixture<TipoPreguntaComponent>;
    let service: TipoPreguntaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [TipoPreguntaComponent],
        providers: []
      })
        .overrideTemplate(TipoPreguntaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipoPreguntaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipoPreguntaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TipoPregunta(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.tipoPreguntas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
