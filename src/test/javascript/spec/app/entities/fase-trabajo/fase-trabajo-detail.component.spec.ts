/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { FaseTrabajoDetailComponent } from 'app/entities/fase-trabajo/fase-trabajo-detail.component';
import { FaseTrabajo } from 'app/shared/model/fase-trabajo.model';

describe('Component Tests', () => {
  describe('FaseTrabajo Management Detail Component', () => {
    let comp: FaseTrabajoDetailComponent;
    let fixture: ComponentFixture<FaseTrabajoDetailComponent>;
    const route = ({ data: of({ faseTrabajo: new FaseTrabajo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FaseTrabajoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FaseTrabajoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FaseTrabajoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.faseTrabajo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
