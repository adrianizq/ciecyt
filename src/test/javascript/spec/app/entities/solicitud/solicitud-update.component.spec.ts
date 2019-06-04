/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CiecytTestModule } from '../../../test.module';
import { SolicitudUpdateComponent } from 'app/entities/solicitud/solicitud-update.component';
import { SolicitudService } from 'app/entities/solicitud/solicitud.service';
import { Solicitud } from 'app/shared/model/solicitud.model';

describe('Component Tests', () => {
  describe('Solicitud Management Update Component', () => {
    let comp: SolicitudUpdateComponent;
    let fixture: ComponentFixture<SolicitudUpdateComponent>;
    let service: SolicitudService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [SolicitudUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SolicitudUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SolicitudUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SolicitudService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Solicitud(123);
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
        const entity = new Solicitud();
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
