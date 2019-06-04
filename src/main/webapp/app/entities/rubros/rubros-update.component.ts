import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IRubros, Rubros } from 'app/shared/model/rubros.model';
import { RubrosService } from './rubros.service';

@Component({
  selector: 'jhi-rubros-update',
  templateUrl: './rubros-update.component.html'
})
export class RubrosUpdateComponent implements OnInit {
  rubros: IRubros;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    rubro: []
  });

  constructor(protected rubrosService: RubrosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ rubros }) => {
      this.updateForm(rubros);
      this.rubros = rubros;
    });
  }

  updateForm(rubros: IRubros) {
    this.editForm.patchValue({
      id: rubros.id,
      rubro: rubros.rubro
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const rubros = this.createFromForm();
    if (rubros.id !== undefined) {
      this.subscribeToSaveResponse(this.rubrosService.update(rubros));
    } else {
      this.subscribeToSaveResponse(this.rubrosService.create(rubros));
    }
  }

  private createFromForm(): IRubros {
    const entity = {
      ...new Rubros(),
      id: this.editForm.get(['id']).value,
      rubro: this.editForm.get(['rubro']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRubros>>) {
    result.subscribe((res: HttpResponse<IRubros>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
