/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ProductoProyectoDetailComponent } from 'app/entities/producto-proyecto/producto-proyecto-detail.component';
import { ProductoProyecto } from 'app/shared/model/producto-proyecto.model';

describe('Component Tests', () => {
  describe('ProductoProyecto Management Detail Component', () => {
    let comp: ProductoProyectoDetailComponent;
    let fixture: ComponentFixture<ProductoProyectoDetailComponent>;
    const route = ({ data: of({ productoProyecto: new ProductoProyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ProductoProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductoProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductoProyectoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productoProyecto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
