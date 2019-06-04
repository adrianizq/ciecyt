/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CiecytTestModule } from '../../../test.module';
import { RolesUsuarioProyectoComponent } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto.component';
import { RolesUsuarioProyectoService } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto.service';
import { RolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

describe('Component Tests', () => {
  describe('RolesUsuarioProyecto Management Component', () => {
    let comp: RolesUsuarioProyectoComponent;
    let fixture: ComponentFixture<RolesUsuarioProyectoComponent>;
    let service: RolesUsuarioProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RolesUsuarioProyectoComponent],
        providers: []
      })
        .overrideTemplate(RolesUsuarioProyectoComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RolesUsuarioProyectoComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RolesUsuarioProyectoService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new RolesUsuarioProyecto(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.rolesUsuarioProyectos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
