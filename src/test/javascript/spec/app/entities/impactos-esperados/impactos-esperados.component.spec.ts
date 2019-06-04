/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { ImpactosEsperadosComponent } from 'app/entities/impactos-esperados/impactos-esperados.component';
import { ImpactosEsperadosService } from 'app/entities/impactos-esperados/impactos-esperados.service';
import { ImpactosEsperados } from 'app/shared/model/impactos-esperados.model';

describe('Component Tests', () => {
  describe('ImpactosEsperados Management Component', () => {
    let comp: ImpactosEsperadosComponent;
    let fixture: ComponentFixture<ImpactosEsperadosComponent>;
    let service: ImpactosEsperadosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ImpactosEsperadosComponent],
        providers: []
      })
        .overrideTemplate(ImpactosEsperadosComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ImpactosEsperadosComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ImpactosEsperadosService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ImpactosEsperados(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.impactosEsperados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
