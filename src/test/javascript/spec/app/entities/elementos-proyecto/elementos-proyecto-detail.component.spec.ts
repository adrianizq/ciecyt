/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ElementosProyectoDetailComponent } from 'app/entities/elementos-proyecto/elementos-proyecto-detail.component';
import { ElementosProyecto } from 'app/shared/model/elementos-proyecto.model';

describe('Component Tests', () => {
  describe('ElementosProyecto Management Detail Component', () => {
    let comp: ElementosProyectoDetailComponent;
    let fixture: ComponentFixture<ElementosProyectoDetailComponent>;
    const route = ({ data: of({ elementosProyecto: new ElementosProyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ElementosProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ElementosProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElementosProyectoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.elementosProyecto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
