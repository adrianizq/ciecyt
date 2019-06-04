/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ProductoProyectoUpdateComponent } from 'app/entities/producto-proyecto/producto-proyecto-update.component';
import { ProductoProyectoService } from 'app/entities/producto-proyecto/producto-proyecto.service';
import { ProductoProyecto } from 'app/shared/model/producto-proyecto.model';

describe('Component Tests', () => {
  describe('ProductoProyecto Management Update Component', () => {
    let comp: ProductoProyectoUpdateComponent;
    let fixture: ComponentFixture<ProductoProyectoUpdateComponent>;
    let service: ProductoProyectoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ProductoProyectoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductoProyectoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoProyectoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoProyectoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductoProyecto(123);
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
        const entity = new ProductoProyecto();
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
