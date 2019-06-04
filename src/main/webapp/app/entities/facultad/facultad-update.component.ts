import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFacultad, Facultad } from 'app/shared/model/facultad.model';
import { FacultadService } from './facultad.service';

@Component({
  selector: 'jhi-facultad-update',
  templateUrl: './facultad-update.component.html'
})
export class FacultadUpdateComponent implements OnInit {
  facultad: IFacultad;
  isSaving: boolean;

  facultads: IFacultad[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    padre: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected facultadService: FacultadService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ facultad }) => {
      this.updateForm(facultad);
      this.facultad = facultad;
    });
    this.facultadService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFacultad[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFacultad[]>) => response.body)
      )
      .subscribe((res: IFacultad[]) => (this.facultads = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(facultad: IFacultad) {
    this.editForm.patchValue({
      id: facultad.id,
      nombre: facultad.nombre,
      padre: facultad.padre
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const facultad = this.createFromForm();
    if (facultad.id !== undefined) {
      this.subscribeToSaveResponse(this.facultadService.update(facultad));
    } else {
      this.subscribeToSaveResponse(this.facultadService.create(facultad));
    }
  }

  private createFromForm(): IFacultad {
    const entity = {
      ...new Facultad(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      padre: this.editForm.get(['padre']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFacultad>>) {
    result.subscribe((res: HttpResponse<IFacultad>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackFacultadById(index: number, item: IFacultad) {
    return item.id;
  }
}
