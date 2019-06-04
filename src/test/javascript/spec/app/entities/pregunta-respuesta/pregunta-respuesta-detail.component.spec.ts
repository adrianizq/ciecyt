/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { PreguntaRespuestaDetailComponent } from 'app/entities/pregunta-respuesta/pregunta-respuesta-detail.component';
import { PreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';

describe('Component Tests', () => {
  describe('PreguntaRespuesta Management Detail Component', () => {
    let comp: PreguntaRespuestaDetailComponent;
    let fixture: ComponentFixture<PreguntaRespuestaDetailComponent>;
    const route = ({ data: of({ preguntaRespuesta: new PreguntaRespuesta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntaRespuestaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PreguntaRespuestaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PreguntaRespuestaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.preguntaRespuesta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
