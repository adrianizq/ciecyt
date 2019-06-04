import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IModalidadDeGrado, ModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';
import { ModalidadDeGradoService } from './modalidad-de-grado.service';

@Component({
  selector: 'jhi-modalidad-de-grado-update',
  templateUrl: './modalidad-de-grado-update.component.html'
})
export class ModalidadDeGradoUpdateComponent implements OnInit {
  modalidadDeGrado: IModalidadDeGrado;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    modalidad: []
  });

  constructor(
    protected modalidadDeGradoService: ModalidadDeGradoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ modalidadDeGrado }) => {
      this.updateForm(modalidadDeGrado);
      this.modalidadDeGrado = modalidadDeGrado;
    });
  }

  updateForm(modalidadDeGrado: IModalidadDeGrado) {
    this.editForm.patchValue({
      id: modalidadDeGrado.id,
      modalidad: modalidadDeGrado.modalidad
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const modalidadDeGrado = this.createFromForm();
    if (modalidadDeGrado.id !== undefined) {
      this.subscribeToSaveResponse(this.modalidadDeGradoService.update(modalidadDeGrado));
    } else {
      this.subscribeToSaveResponse(this.modalidadDeGradoService.create(modalidadDeGrado));
    }
  }

  private createFromForm(): IModalidadDeGrado {
    const entity = {
      ...new ModalidadDeGrado(),
      id: this.editForm.get(['id']).value,
      modalidad: this.editForm.get(['modalidad']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IModalidadDeGrado>>) {
    result.subscribe((res: HttpResponse<IModalidadDeGrado>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
