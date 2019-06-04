/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ModalidadDeGradoDetailComponent } from 'app/entities/modalidad-de-grado/modalidad-de-grado-detail.component';
import { ModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';

describe('Component Tests', () => {
  describe('ModalidadDeGrado Management Detail Component', () => {
    let comp: ModalidadDeGradoDetailComponent;
    let fixture: ComponentFixture<ModalidadDeGradoDetailComponent>;
    const route = ({ data: of({ modalidadDeGrado: new ModalidadDeGrado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ModalidadDeGradoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ModalidadDeGradoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ModalidadDeGradoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.modalidadDeGrado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
