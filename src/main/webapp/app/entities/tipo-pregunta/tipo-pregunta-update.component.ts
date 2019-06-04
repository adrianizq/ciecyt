import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipoPregunta, TipoPregunta } from 'app/shared/model/tipo-pregunta.model';
import { TipoPreguntaService } from './tipo-pregunta.service';

@Component({
  selector: 'jhi-tipo-pregunta-update',
  templateUrl: './tipo-pregunta-update.component.html'
})
export class TipoPreguntaUpdateComponent implements OnInit {
  tipoPregunta: ITipoPregunta;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    tipoPregunta: [],
    tipoDato: []
  });

  constructor(protected tipoPreguntaService: TipoPreguntaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipoPregunta }) => {
      this.updateForm(tipoPregunta);
      this.tipoPregunta = tipoPregunta;
    });
  }

  updateForm(tipoPregunta: ITipoPregunta) {
    this.editForm.patchValue({
      id: tipoPregunta.id,
      tipoPregunta: tipoPregunta.tipoPregunta,
      tipoDato: tipoPregunta.tipoDato
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipoPregunta = this.createFromForm();
    if (tipoPregunta.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoPreguntaService.update(tipoPregunta));
    } else {
      this.subscribeToSaveResponse(this.tipoPreguntaService.create(tipoPregunta));
    }
  }

  private createFromForm(): ITipoPregunta {
    const entity = {
      ...new TipoPregunta(),
      id: this.editForm.get(['id']).value,
      tipoPregunta: this.editForm.get(['tipoPregunta']).value,
      tipoDato: this.editForm.get(['tipoDato']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoPregunta>>) {
    result.subscribe((res: HttpResponse<ITipoPregunta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
