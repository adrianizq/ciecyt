/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { IntegrantesProyectoDetailComponent } from 'app/entities/integrantes-proyecto/integrantes-proyecto-detail.component';
import { IntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';

describe('Component Tests', () => {
  describe('IntegrantesProyecto Management Detail Component', () => {
    let comp: IntegrantesProyectoDetailComponent;
    let fixture: ComponentFixture<IntegrantesProyectoDetailComponent>;
    const route = ({ data: of({ integrantesProyecto: new IntegrantesProyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [IntegrantesProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(IntegrantesProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(IntegrantesProyectoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.integrantesProyecto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
