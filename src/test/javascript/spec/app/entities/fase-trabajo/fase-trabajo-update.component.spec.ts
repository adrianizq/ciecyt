/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { FaseTrabajoUpdateComponent } from 'app/entities/fase-trabajo/fase-trabajo-update.component';
import { FaseTrabajoService } from 'app/entities/fase-trabajo/fase-trabajo.service';
import { FaseTrabajo } from 'app/shared/model/fase-trabajo.model';

describe('Component Tests', () => {
  describe('FaseTrabajo Management Update Component', () => {
    let comp: FaseTrabajoUpdateComponent;
    let fixture: ComponentFixture<FaseTrabajoUpdateComponent>;
    let service: FaseTrabajoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FaseTrabajoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FaseTrabajoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FaseTrabajoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FaseTrabajoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FaseTrabajo(123);
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
        const entity = new FaseTrabajo();
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
