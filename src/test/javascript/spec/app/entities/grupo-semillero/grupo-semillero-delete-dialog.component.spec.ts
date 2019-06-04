/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CiecytTestModule } from '../../../test.module';
import { GrupoSemilleroDeleteDialogComponent } from 'app/entities/grupo-semillero/grupo-semillero-delete-dialog.component';
import { GrupoSemilleroService } from 'app/entities/grupo-semillero/grupo-semillero.service';

describe('Component Tests', () => {
  describe('GrupoSemillero Management Delete Component', () => {
    let comp: GrupoSemilleroDeleteDialogComponent;
    let fixture: ComponentFixture<GrupoSemilleroDeleteDialogComponent>;
    let service: GrupoSemilleroService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CiecytTestModule],
        declarations: [GrupoSemilleroDeleteDialogComponent]
      })
        .overrideTemplate(GrupoSemilleroDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GrupoSemilleroDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(GrupoSemilleroService);
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
