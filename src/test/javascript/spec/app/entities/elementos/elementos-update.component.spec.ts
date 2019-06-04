/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { ElementosUpdateComponent } from 'app/entities/elementos/elementos-update.component';
import { ElementosService } from 'app/entities/elementos/elementos.service';
import { Elementos } from 'app/shared/model/elementos.model';

describe('Component Tests', () => {
  describe('Elementos Management Update Component', () => {
    let comp: ElementosUpdateComponent;
    let fixture: ComponentFixture<ElementosUpdateComponent>;
    let service: ElementosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [ElementosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ElementosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ElementosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElementosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Elementos(123);
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
        const entity = new Elementos();
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
