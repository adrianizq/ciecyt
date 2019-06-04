/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { ProductoProyectoComponent } from 'app/entities/producto-proyecto/producto-proyecto.component';
import { ProductoProyectoService } from 'app/entities/producto-proyecto/producto-proyecto.service';
import { ProductoProyecto } from 'app/shared/model/producto-proyecto.model';

describe('Component Tests', () => {
  describe('ProductoProyecto Management Component', () => {
    let comp: ProductoProyectoComponent;
    let fixture: ComponentFixture<ProductoProyectoComponent>;
    let service: ProductoProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ProductoProyectoComponent],
        providers: []
      })
        .overrideTemplate(ProductoProyectoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoProyectoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoProyectoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductoProyecto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productoProyectos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
