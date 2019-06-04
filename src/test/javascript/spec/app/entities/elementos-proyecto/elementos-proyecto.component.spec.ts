/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { ElementosProyectoComponent } from 'app/entities/elementos-proyecto/elementos-proyecto.component';
import { ElementosProyectoService } from 'app/entities/elementos-proyecto/elementos-proyecto.service';
import { ElementosProyecto } from 'app/shared/model/elementos-proyecto.model';

describe('Component Tests', () => {
  describe('ElementosProyecto Management Component', () => {
    let comp: ElementosProyectoComponent;
    let fixture: ComponentFixture<ElementosProyectoComponent>;
    let service: ElementosProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ElementosProyectoComponent],
        providers: []
      })
        .overrideTemplate(ElementosProyectoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElementosProyectoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElementosProyectoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ElementosProyecto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.elementosProyectos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
