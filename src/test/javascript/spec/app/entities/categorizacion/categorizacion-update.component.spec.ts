/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { CategorizacionUpdateComponent } from 'app/entities/categorizacion/categorizacion-update.component';
import { CategorizacionService } from 'app/entities/categorizacion/categorizacion.service';
import { Categorizacion } from 'app/shared/model/categorizacion.model';

describe('Component Tests', () => {
  describe('Categorizacion Management Update Component', () => {
    let comp: CategorizacionUpdateComponent;
    let fixture: ComponentFixture<CategorizacionUpdateComponent>;
    let service: CategorizacionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CategorizacionUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CategorizacionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CategorizacionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CategorizacionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Categorizacion(123);
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
        const entity = new Categorizacion();
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
