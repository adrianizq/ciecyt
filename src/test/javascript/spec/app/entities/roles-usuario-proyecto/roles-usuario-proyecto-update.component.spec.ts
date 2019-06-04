/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { RolesUsuarioProyectoUpdateComponent } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto-update.component';
import { RolesUsuarioProyectoService } from 'app/entities/roles-usuario-proyecto/roles-usuario-proyecto.service';
import { RolesUsuarioProyecto } from 'app/shared/model/roles-usuario-proyecto.model';

describe('Component Tests', () => {
  describe('RolesUsuarioProyecto Management Update Component', () => {
    let comp: RolesUsuarioProyectoUpdateComponent;
    let fixture: ComponentFixture<RolesUsuarioProyectoUpdateComponent>;
    let service: RolesUsuarioProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [RolesUsuarioProyectoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RolesUsuarioProyectoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RolesUsuarioProyectoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RolesUsuarioProyectoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RolesUsuarioProyecto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RolesUsuarioProyecto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
