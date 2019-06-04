import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFaseTrabajo, FaseTrabajo } from 'app/shared/model/fase-trabajo.model';
import { FaseTrabajoService } from './fase-trabajo.service';

@Component({
  selector: 'jhi-fase-trabajo-update',
  templateUrl: './fase-trabajo-update.component.html'
})
export class FaseTrabajoUpdateComponent implements OnInit {
  faseTrabajo: IFaseTrabajo;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    faseTrabajo: []
  });

  constructor(protected faseTrabajoService: FaseTrabajoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ faseTrabajo }) => {
      this.updateForm(faseTrabajo);
      this.faseTrabajo = faseTrabajo;
    });
  }

  updateForm(faseTrabajo: IFaseTrabajo) {
    this.editForm.patchValue({
      id: faseTrabajo.id,
      faseTrabajo: faseTrabajo.faseTrabajo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const faseTrabajo = this.createFromForm();
    if (faseTrabajo.id !== undefined) {
      this.subscribeToSaveResponse(this.faseTrabajoService.update(faseTrabajo));
    } else {
      this.subscribeToSaveResponse(this.faseTrabajoService.create(faseTrabajo));
    }
  }

  private createFromForm(): IFaseTrabajo {
    const entity = {
      ...new FaseTrabajo(),
      id: this.editForm.get(['id']).value,
      faseTrabajo: this.editForm.get(['faseTrabajo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFaseTrabajo>>) {
    result.subscribe((res: HttpResponse<IFaseTrabajo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
