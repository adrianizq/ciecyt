/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { FichaTecnicaComponent } from 'app/entities/ficha-tecnica/ficha-tecnica.component';
import { FichaTecnicaService } from 'app/entities/ficha-tecnica/ficha-tecnica.service';
import { FichaTecnica } from 'app/shared/model/ficha-tecnica.model';

describe('Component Tests', () => {
  describe('FichaTecnica Management Component', () => {
    let comp: FichaTecnicaComponent;
    let fixture: ComponentFixture<FichaTecnicaComponent>;
    let service: FichaTecnicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FichaTecnicaComponent],
        providers: []
      })
        .overrideTemplate(FichaTecnicaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaTecnicaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaTecnicaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FichaTecnica(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.fichaTecnicas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
