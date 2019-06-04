/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { FacultadComponent } from 'app/entities/facultad/facultad.component';
import { FacultadService } from 'app/entities/facultad/facultad.service';
import { Facultad } from 'app/shared/model/facultad.model';

describe('Component Tests', () => {
  describe('Facultad Management Component', () => {
    let comp: FacultadComponent;
    let fixture: ComponentFixture<FacultadComponent>;
    let service: FacultadService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FacultadComponent],
        providers: []
      })
        .overrideTemplate(FacultadComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FacultadComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FacultadService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Facultad(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.facultads[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
