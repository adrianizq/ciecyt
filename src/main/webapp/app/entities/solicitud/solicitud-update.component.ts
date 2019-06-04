import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISolicitud, Solicitud } from 'app/shared/model/solicitud.model';
import { SolicitudService } from './solicitud.service';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { IntegrantesProyectoService } from 'app/entities/integrantes-proyecto';

@Component({
  selector: 'jhi-solicitud-update',
  templateUrl: './solicitud-update.component.html'
})
export class SolicitudUpdateComponent implements OnInit {
  solicitud: ISolicitud;
  isSaving: boolean;

  integrantesproyectos: IIntegrantesProyecto[];
  fechaCreancionDp: any;

  editForm = this.fb.group({
    id: [],
    estado: [],
    asunto: [],
    fechaCreancion: [],
    integrantesProyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected solicitudService: SolicitudService,
    protected integrantesProyectoService: IntegrantesProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ solicitud }) => {
      this.updateForm(solicitud);
      this.solicitud = solicitud;
    });
    this.integrantesProyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIntegrantesProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIntegrantesProyecto[]>) => response.body)
      )
      .subscribe((res: IIntegrantesProyecto[]) => (this.integrantesproyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(solicitud: ISolicitud) {
    this.editForm.patchValue({
      id: solicitud.id,
      estado: solicitud.estado,
      asunto: solicitud.asunto,
      fechaCreancion: solicitud.fechaCreancion,
      integrantesProyecto: solicitud.integrantesProyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const solicitud = this.createFromForm();
    if (solicitud.id !== undefined) {
      this.subscribeToSaveResponse(this.solicitudService.update(solicitud));
    } else {
      this.subscribeToSaveResponse(this.solicitudService.create(solicitud));
    }
  }

  private createFromForm(): ISolicitud {
    const entity = {
      ...new Solicitud(),
      id: this.editForm.get(['id']).value,
      estado: this.editForm.get(['estado']).value,
      asunto: this.editForm.get(['asunto']).value,
      fechaCreancion: this.editForm.get(['fechaCreancion']).value,
      integrantesProyecto: this.editForm.get(['integrantesProyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISolicitud>>) {
    result.subscribe((res: HttpResponse<ISolicitud>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackIntegrantesProyectoById(index: number, item: IIntegrantesProyecto) {
    return item.id;
  }
}
