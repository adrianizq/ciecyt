/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { CronogramaUpdateComponent } from 'app/entities/cronograma/cronograma-update.component';
import { CronogramaService } from 'app/entities/cronograma/cronograma.service';
import { Cronograma } from 'app/shared/model/cronograma.model';

describe('Component Tests', () => {
  describe('Cronograma Management Update Component', () => {
    let comp: CronogramaUpdateComponent;
    let fixture: ComponentFixture<CronogramaUpdateComponent>;
    let service: CronogramaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [CronogramaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CronogramaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CronogramaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CronogramaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Cronograma(123);
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
        const entity = new Cronograma();
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
