/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { ElementosComponent } from 'app/entities/elementos/elementos.component';
import { ElementosService } from 'app/entities/elementos/elementos.service';
import { Elementos } from 'app/shared/model/elementos.model';

describe('Component Tests', () => {
  describe('Elementos Management Component', () => {
    let comp: ElementosComponent;
    let fixture: ComponentFixture<ElementosComponent>;
    let service: ElementosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ElementosComponent],
        providers: []
      })
        .overrideTemplate(ElementosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElementosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElementosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Elementos(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.elementos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
