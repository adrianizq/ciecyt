import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IElementos, Elementos } from 'app/shared/model/elementos.model';
import { ElementosService } from './elementos.service';

@Component({
  selector: 'jhi-elementos-update',
  templateUrl: './elementos-update.component.html'
})
export class ElementosUpdateComponent implements OnInit {
  elementos: IElementos;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    elemento: []
  });

  constructor(protected elementosService: ElementosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ elementos }) => {
      this.updateForm(elementos);
      this.elementos = elementos;
    });
  }

  updateForm(elementos: IElementos) {
    this.editForm.patchValue({
      id: elementos.id,
      elemento: elementos.elemento
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const elementos = this.createFromForm();
    if (elementos.id !== undefined) {
      this.subscribeToSaveResponse(this.elementosService.update(elementos));
    } else {
      this.subscribeToSaveResponse(this.elementosService.create(elementos));
    }
  }

  private createFromForm(): IElementos {
    const entity = {
      ...new Elementos(),
      id: this.editForm.get(['id']).value,
      elemento: this.editForm.get(['elemento']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElementos>>) {
    result.subscribe((res: HttpResponse<IElementos>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
