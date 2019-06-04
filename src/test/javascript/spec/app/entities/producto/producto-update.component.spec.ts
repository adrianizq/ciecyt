/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ProductoUpdateComponent } from 'app/entities/producto/producto-update.component';
import { ProductoService } from 'app/entities/producto/producto.service';
import { Producto } from 'app/shared/model/producto.model';

describe('Component Tests', () => {
  describe('Producto Management Update Component', () => {
    let comp: ProductoUpdateComponent;
    let fixture: ComponentFixture<ProductoUpdateComponent>;
    let service: ProductoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ProductoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Producto(123);
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
        const entity = new Producto();
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
