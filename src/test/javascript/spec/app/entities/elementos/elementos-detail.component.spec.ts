/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ElementosDetailComponent } from 'app/entities/elementos/elementos-detail.component';
import { Elementos } from 'app/shared/model/elementos.model';

describe('Component Tests', () => {
  describe('Elementos Management Detail Component', () => {
    let comp: ElementosDetailComponent;
    let fixture: ComponentFixture<ElementosDetailComponent>;
    const route = ({ data: of({ elementos: new Elementos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ElementosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ElementosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElementosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.elementos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
