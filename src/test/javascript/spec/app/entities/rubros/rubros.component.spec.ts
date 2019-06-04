/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { RubrosComponent } from 'app/entities/rubros/rubros.component';
import { RubrosService } from 'app/entities/rubros/rubros.service';
import { Rubros } from 'app/shared/model/rubros.model';

describe('Component Tests', () => {
  describe('Rubros Management Component', () => {
    let comp: RubrosComponent;
    let fixture: ComponentFixture<RubrosComponent>;
    let service: RubrosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RubrosComponent],
        providers: []
      })
        .overrideTemplate(RubrosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RubrosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RubrosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Rubros(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rubros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
