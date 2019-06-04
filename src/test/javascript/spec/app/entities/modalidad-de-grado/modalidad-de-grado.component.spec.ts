/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { ModalidadDeGradoComponent } from 'app/entities/modalidad-de-grado/modalidad-de-grado.component';
import { ModalidadDeGradoService } from 'app/entities/modalidad-de-grado/modalidad-de-grado.service';
import { ModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';

describe('Component Tests', () => {
  describe('ModalidadDeGrado Management Component', () => {
    let comp: ModalidadDeGradoComponent;
    let fixture: ComponentFixture<ModalidadDeGradoComponent>;
    let service: ModalidadDeGradoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ModalidadDeGradoComponent],
        providers: []
      })
        .overrideTemplate(ModalidadDeGradoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ModalidadDeGradoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ModalidadDeGradoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ModalidadDeGrado(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.modalidadDeGrados[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
