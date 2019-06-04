/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { PresupuestoValorDeleteDialogComponent } from 'app/entities/presupuesto-valor/presupuesto-valor-delete-dialog.component';
import { PresupuestoValorService } from 'app/entities/presupuesto-valor/presupuesto-valor.service';

describe('Component Tests', () => {
  describe('PresupuestoValor Management Delete Component', () => {
    let comp: PresupuestoValorDeleteDialogComponent;
    let fixture: ComponentFixture<PresupuestoValorDeleteDialogComponent>;
    let service: PresupuestoValorService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [PresupuestoValorDeleteDialogComponent]
      })
        .overrideTemplate(PresupuestoValorDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PresupuestoValorDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PresupuestoValorService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
