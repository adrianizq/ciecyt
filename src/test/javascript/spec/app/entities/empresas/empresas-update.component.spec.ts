/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { EmpresasUpdateComponent } from 'app/entities/empresas/empresas-update.component';
import { EmpresasService } from 'app/entities/empresas/empresas.service';
import { Empresas } from 'app/shared/model/empresas.model';

describe('Component Tests', () => {
  describe('Empresas Management Update Component', () => {
    let comp: EmpresasUpdateComponent;
    let fixture: ComponentFixture<EmpresasUpdateComponent>;
    let service: EmpresasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [EmpresasUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EmpresasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EmpresasUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EmpresasService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Empresas(123);
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
        const entity = new Empresas();
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
