/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { SolicitudComponent } from 'app/entities/solicitud/solicitud.component';
import { SolicitudService } from 'app/entities/solicitud/solicitud.service';
import { Solicitud } from 'app/shared/model/solicitud.model';

describe('Component Tests', () => {
  describe('Solicitud Management Component', () => {
    let comp: SolicitudComponent;
    let fixture: ComponentFixture<SolicitudComponent>;
    let service: SolicitudService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [SolicitudComponent],
        providers: []
      })
        .overrideTemplate(SolicitudComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicitudComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicitudService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Solicitud(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.solicituds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
