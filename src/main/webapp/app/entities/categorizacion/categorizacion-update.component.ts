import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICategorizacion, Categorizacion } from 'app/shared/model/categorizacion.model';
import { CategorizacionService } from './categorizacion.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';

@Component({
  selector: 'jhi-categorizacion-update',
  templateUrl: './categorizacion-update.component.html'
})
export class CategorizacionUpdateComponent implements OnInit {
  categorizacion: ICategorizacion;
  isSaving: boolean;

  proyectos: IProyecto[];

  editForm = this.fb.group({
    id: [],
    categoria: [],
    proyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected categorizacionService: CategorizacionService,
    protected proyectoService: ProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ categorizacion }) => {
      this.updateForm(categorizacion);
      this.categorizacion = categorizacion;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(categorizacion: ICategorizacion) {
    this.editForm.patchValue({
      id: categorizacion.id,
      categoria: categorizacion.categoria,
      proyecto: categorizacion.proyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const categorizacion = this.createFromForm();
    if (categorizacion.id !== undefined) {
      this.subscribeToSaveResponse(this.categorizacionService.update(categorizacion));
    } else {
      this.subscribeToSaveResponse(this.categorizacionService.create(categorizacion));
    }
  }

  private createFromForm(): ICategorizacion {
    const entity = {
      ...new Categorizacion(),
      id: this.editForm.get(['id']).value,
      categoria: this.editForm.get(['categoria']).value,
      proyecto: this.editForm.get(['proyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategorizacion>>) {
    result.subscribe((res: HttpResponse<ICategorizacion>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
