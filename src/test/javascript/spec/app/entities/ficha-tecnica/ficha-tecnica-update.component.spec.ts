/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { FichaTecnicaUpdateComponent } from 'app/entities/ficha-tecnica/ficha-tecnica-update.component';
import { FichaTecnicaService } from 'app/entities/ficha-tecnica/ficha-tecnica.service';
import { FichaTecnica } from 'app/shared/model/ficha-tecnica.model';

describe('Component Tests', () => {
  describe('FichaTecnica Management Update Component', () => {
    let comp: FichaTecnicaUpdateComponent;
    let fixture: ComponentFixture<FichaTecnicaUpdateComponent>;
    let service: FichaTecnicaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [FichaTecnicaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FichaTecnicaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FichaTecnicaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FichaTecnicaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new FichaTecnica(123);
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
        const entity = new FichaTecnica();
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
