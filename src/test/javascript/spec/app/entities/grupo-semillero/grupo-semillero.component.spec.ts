/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { GrupoSemilleroComponent } from 'app/entities/grupo-semillero/grupo-semillero.component';
import { GrupoSemilleroService } from 'app/entities/grupo-semillero/grupo-semillero.service';
import { GrupoSemillero } from 'app/shared/model/grupo-semillero.model';

describe('Component Tests', () => {
  describe('GrupoSemillero Management Component', () => {
    let comp: GrupoSemilleroComponent;
    let fixture: ComponentFixture<GrupoSemilleroComponent>;
    let service: GrupoSemilleroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [GrupoSemilleroComponent],
        providers: []
      })
        .overrideTemplate(GrupoSemilleroComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrupoSemilleroComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoSemilleroService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new GrupoSemillero(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.grupoSemilleros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
