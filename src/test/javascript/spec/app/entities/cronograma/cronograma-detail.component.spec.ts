/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { CronogramaDetailComponent } from 'app/entities/cronograma/cronograma-detail.component';
import { Cronograma } from 'app/shared/model/cronograma.model';

describe('Component Tests', () => {
  describe('Cronograma Management Detail Component', () => {
    let comp: CronogramaDetailComponent;
    let fixture: ComponentFixture<CronogramaDetailComponent>;
    const route = ({ data: of({ cronograma: new Cronograma(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CronogramaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CronogramaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CronogramaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cronograma).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
