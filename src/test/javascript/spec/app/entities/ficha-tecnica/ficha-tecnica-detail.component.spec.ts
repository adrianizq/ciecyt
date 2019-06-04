/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { FichaTecnicaDetailComponent } from 'app/entities/ficha-tecnica/ficha-tecnica-detail.component';
import { FichaTecnica } from 'app/shared/model/ficha-tecnica.model';

describe('Component Tests', () => {
  describe('FichaTecnica Management Detail Component', () => {
    let comp: FichaTecnicaDetailComponent;
    let fixture: ComponentFixture<FichaTecnicaDetailComponent>;
    const route = ({ data: of({ fichaTecnica: new FichaTecnica(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FichaTecnicaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FichaTecnicaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FichaTecnicaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fichaTecnica).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
