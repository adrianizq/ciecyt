/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { CategorizacionComponent } from 'app/entities/categorizacion/categorizacion.component';
import { CategorizacionService } from 'app/entities/categorizacion/categorizacion.service';
import { Categorizacion } from 'app/shared/model/categorizacion.model';

describe('Component Tests', () => {
  describe('Categorizacion Management Component', () => {
    let comp: CategorizacionComponent;
    let fixture: ComponentFixture<CategorizacionComponent>;
    let service: CategorizacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CategorizacionComponent],
        providers: []
      })
        .overrideTemplate(CategorizacionComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorizacionComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorizacionService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Categorizacion(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.categorizacions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
