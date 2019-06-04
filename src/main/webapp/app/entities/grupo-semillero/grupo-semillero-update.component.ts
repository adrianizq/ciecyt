import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IGrupoSemillero, GrupoSemillero } from 'app/shared/model/grupo-semillero.model';
import { GrupoSemilleroService } from './grupo-semillero.service';

@Component({
  selector: 'jhi-grupo-semillero-update',
  templateUrl: './grupo-semillero-update.component.html'
})
export class GrupoSemilleroUpdateComponent implements OnInit {
  grupoSemillero: IGrupoSemillero;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    tipo: []
  });

  constructor(protected grupoSemilleroService: GrupoSemilleroService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ grupoSemillero }) => {
      this.updateForm(grupoSemillero);
      this.grupoSemillero = grupoSemillero;
    });
  }

  updateForm(grupoSemillero: IGrupoSemillero) {
    this.editForm.patchValue({
      id: grupoSemillero.id,
      nombre: grupoSemillero.nombre,
      tipo: grupoSemillero.tipo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const grupoSemillero = this.createFromForm();
    if (grupoSemillero.id !== undefined) {
      this.subscribeToSaveResponse(this.grupoSemilleroService.update(grupoSemillero));
    } else {
      this.subscribeToSaveResponse(this.grupoSemilleroService.create(grupoSemillero));
    }
  }

  private createFromForm(): IGrupoSemillero {
    const entity = {
      ...new GrupoSemillero(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      tipo: this.editForm.get(['tipo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGrupoSemillero>>) {
    result.subscribe((res: HttpResponse<IGrupoSemillero>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
