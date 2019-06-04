/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { CronogramaComponent } from 'app/entities/cronograma/cronograma.component';
import { CronogramaService } from 'app/entities/cronograma/cronograma.service';
import { Cronograma } from 'app/shared/model/cronograma.model';

describe('Component Tests', () => {
  describe('Cronograma Management Component', () => {
    let comp: CronogramaComponent;
    let fixture: ComponentFixture<CronogramaComponent>;
    let service: CronogramaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CronogramaComponent],
        providers: []
      })
        .overrideTemplate(CronogramaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CronogramaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CronogramaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Cronograma(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cronogramas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
