/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { FacultadDetailComponent } from 'app/entities/facultad/facultad-detail.component';
import { Facultad } from 'app/shared/model/facultad.model';

describe('Component Tests', () => {
  describe('Facultad Management Detail Component', () => {
    let comp: FacultadDetailComponent;
    let fixture: ComponentFixture<FacultadDetailComponent>;
    const route = ({ data: of({ facultad: new Facultad(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FacultadDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FacultadDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FacultadDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.facultad).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
