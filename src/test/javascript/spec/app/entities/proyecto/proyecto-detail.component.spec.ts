/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ProyectoDetailComponent } from 'app/entities/proyecto/proyecto-detail.component';
import { Proyecto } from 'app/shared/model/proyecto.model';

describe('Component Tests', () => {
  describe('Proyecto Management Detail Component', () => {
    let comp: ProyectoDetailComponent;
    let fixture: ComponentFixture<ProyectoDetailComponent>;
    const route = ({ data: of({ proyecto: new Proyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProyectoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.proyecto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
