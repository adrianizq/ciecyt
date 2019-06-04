/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { RubrosDetailComponent } from 'app/entities/rubros/rubros-detail.component';
import { Rubros } from 'app/shared/model/rubros.model';

describe('Component Tests', () => {
  describe('Rubros Management Detail Component', () => {
    let comp: RubrosDetailComponent;
    let fixture: ComponentFixture<RubrosDetailComponent>;
    const route = ({ data: of({ rubros: new Rubros(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RubrosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RubrosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RubrosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rubros).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
