/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { GrupoSemilleroUpdateComponent } from 'app/entities/grupo-semillero/grupo-semillero-update.component';
import { GrupoSemilleroService } from 'app/entities/grupo-semillero/grupo-semillero.service';
import { GrupoSemillero } from 'app/shared/model/grupo-semillero.model';

describe('Component Tests', () => {
  describe('GrupoSemillero Management Update Component', () => {
    let comp: GrupoSemilleroUpdateComponent;
    let fixture: ComponentFixture<GrupoSemilleroUpdateComponent>;
    let service: GrupoSemilleroService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [GrupoSemilleroUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(GrupoSemilleroUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GrupoSemilleroUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoSemilleroService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new GrupoSemillero(123);
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
        const entity = new GrupoSemillero();
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
