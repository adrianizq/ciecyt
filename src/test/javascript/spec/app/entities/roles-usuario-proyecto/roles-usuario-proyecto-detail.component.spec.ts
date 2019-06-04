/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { RolesUsuarioProyectoDetailComponent } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto-detail.component';
import { RolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

describe('Component Tests', () => {
  describe('RolesUsuarioProyecto Management Detail Component', () => {
    let comp: RolesUsuarioProyectoDetailComponent;
    let fixture: ComponentFixture<RolesUsuarioProyectoDetailComponent>;
    const route = ({ data: of({ rolesUsuarioProyecto: new RolesUsuarioProyecto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RolesUsuarioProyectoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(RolesUsuarioProyectoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RolesUsuarioProyectoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.rolesUsuarioProyecto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
