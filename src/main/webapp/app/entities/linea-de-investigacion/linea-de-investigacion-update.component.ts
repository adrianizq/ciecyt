import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILineaDeInvestigacion, LineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';
import { LineaDeInvestigacionService } from './linea-de-investigacion.service';

@Component({
  selector: 'jhi-linea-de-investigacion-update',
  templateUrl: './linea-de-investigacion-update.component.html'
})
export class LineaDeInvestigacionUpdateComponent implements OnInit {
  lineaDeInvestigacion: ILineaDeInvestigacion;
  isSaving: boolean;

  lineadeinvestigacions: ILineaDeInvestigacion[];

  editForm = this.fb.group({
    id: [],
    linea: [],
    padre: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected lineaDeInvestigacionService: LineaDeInvestigacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ lineaDeInvestigacion }) => {
      this.updateForm(lineaDeInvestigacion);
      this.lineaDeInvestigacion = lineaDeInvestigacion;
    });
    this.lineaDeInvestigacionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILineaDeInvestigacion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILineaDeInvestigacion[]>) => response.body)
      )
      .subscribe(
        (res: ILineaDeInvestigacion[]) => (this.lineadeinvestigacions = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(lineaDeInvestigacion: ILineaDeInvestigacion) {
    this.editForm.patchValue({
      id: lineaDeInvestigacion.id,
      linea: lineaDeInvestigacion.linea,
      padre: lineaDeInvestigacion.padre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const lineaDeInvestigacion = this.createFromForm();
    if (lineaDeInvestigacion.id !== undefined) {
      this.subscribeToSaveResponse(this.lineaDeInvestigacionService.update(lineaDeInvestigacion));
    } else {
      this.subscribeToSaveResponse(this.lineaDeInvestigacionService.create(lineaDeInvestigacion));
    }
  }

  private createFromForm(): ILineaDeInvestigacion {
    const entity = {
      ...new LineaDeInvestigacion(),
      id: this.editForm.get(['id']).value,
      linea: this.editForm.get(['linea']).value,
      padre: this.editForm.get(['padre']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILineaDeInvestigacion>>) {
    result.subscribe((res: HttpResponse<ILineaDeInvestigacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackLineaDeInvestigacionById(index: number, item: ILineaDeInvestigacion) {
    return item.id;
  }
}
