/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { FaseTrabajoComponent } from 'app/entities/fase-trabajo/fase-trabajo.component';
import { FaseTrabajoService } from 'app/entities/fase-trabajo/fase-trabajo.service';
import { FaseTrabajo } from 'app/shared/model/fase-trabajo.model';

describe('Component Tests', () => {
  describe('FaseTrabajo Management Component', () => {
    let comp: FaseTrabajoComponent;
    let fixture: ComponentFixture<FaseTrabajoComponent>;
    let service: FaseTrabajoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FaseTrabajoComponent],
        providers: []
      })
        .overrideTemplate(FaseTrabajoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FaseTrabajoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FaseTrabajoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new FaseTrabajo(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.faseTrabajos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
