/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { CategorizacionDetailComponent } from 'app/entities/categorizacion/categorizacion-detail.component';
import { Categorizacion } from 'app/shared/model/categorizacion.model';

describe('Component Tests', () => {
  describe('Categorizacion Management Detail Component', () => {
    let comp: CategorizacionDetailComponent;
    let fixture: ComponentFixture<CategorizacionDetailComponent>;
    const route = ({ data: of({ categorizacion: new Categorizacion(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CategorizacionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CategorizacionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CategorizacionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.categorizacion).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
