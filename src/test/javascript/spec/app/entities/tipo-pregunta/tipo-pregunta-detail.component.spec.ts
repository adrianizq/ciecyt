/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { TipoPreguntaDetailComponent } from 'app/entities/tipo-pregunta/tipo-pregunta-detail.component';
import { TipoPregunta } from 'app/shared/model/tipo-pregunta.model';

describe('Component Tests', () => {
  describe('TipoPregunta Management Detail Component', () => {
    let comp: TipoPreguntaDetailComponent;
    let fixture: ComponentFixture<TipoPreguntaDetailComponent>;
    const route = ({ data: of({ tipoPregunta: new TipoPregunta(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [TipoPreguntaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoPreguntaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoPreguntaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoPregunta).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
