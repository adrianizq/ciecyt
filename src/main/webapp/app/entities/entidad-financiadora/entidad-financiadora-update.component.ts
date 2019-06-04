import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEntidadFinanciadora, EntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';
import { EntidadFinanciadoraService } from './entidad-financiadora.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';

@Component({
  selector: 'jhi-entidad-financiadora-update',
  templateUrl: './entidad-financiadora-update.component.html'
})
export class EntidadFinanciadoraUpdateComponent implements OnInit {
  entidadFinanciadora: IEntidadFinanciadora;
  isSaving: boolean;

  proyectos: IProyecto[];

  editForm = this.fb.group({
    id: [],
    valor: [],
    estadoFinanciacion: [],
    proyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected entidadFinanciadoraService: EntidadFinanciadoraService,
    protected proyectoService: ProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ entidadFinanciadora }) => {
      this.updateForm(entidadFinanciadora);
      this.entidadFinanciadora = entidadFinanciadora;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(entidadFinanciadora: IEntidadFinanciadora) {
    this.editForm.patchValue({
      id: entidadFinanciadora.id,
      valor: entidadFinanciadora.valor,
      estadoFinanciacion: entidadFinanciadora.estadoFinanciacion,
      proyecto: entidadFinanciadora.proyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const entidadFinanciadora = this.createFromForm();
    if (entidadFinanciadora.id !== undefined) {
      this.subscribeToSaveResponse(this.entidadFinanciadoraService.update(entidadFinanciadora));
    } else {
      this.subscribeToSaveResponse(this.entidadFinanciadoraService.create(entidadFinanciadora));
    }
  }

  private createFromForm(): IEntidadFinanciadora {
    const entity = {
      ...new EntidadFinanciadora(),
      id: this.editForm.get(['id']).value,
      valor: this.editForm.get(['valor']).value,
      estadoFinanciacion: this.editForm.get(['estadoFinanciacion']).value,
      proyecto: this.editForm.get(['proyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEntidadFinanciadora>>) {
    result.subscribe((res: HttpResponse<IEntidadFinanciadora>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackProyectoById(index: number, item: IProyecto) {
    return item.id;
  }
}
