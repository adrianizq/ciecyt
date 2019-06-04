/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { PreguntasDetailComponent } from 'app/entities/preguntas/preguntas-detail.component';
import { Preguntas } from 'app/shared/model/preguntas.model';

describe('Component Tests', () => {
  describe('Preguntas Management Detail Component', () => {
    let comp: PreguntasDetailComponent;
    let fixture: ComponentFixture<PreguntasDetailComponent>;
    const route = ({ data: of({ preguntas: new Preguntas(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PreguntasDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PreguntasDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PreguntasDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.preguntas).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
