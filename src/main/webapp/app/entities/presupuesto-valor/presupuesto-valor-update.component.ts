import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPresupuestoValor, PresupuestoValor } from 'app/shared/model/presupuesto-valor.model';
import { PresupuestoValorService } from './presupuesto-valor.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';
import { IRubros } from 'app/shared/model/rubros.model';
import { RubrosService } from 'app/entities/rubros';

@Component({
  selector: 'jhi-presupuesto-valor-update',
  templateUrl: './presupuesto-valor-update.component.html'
})
export class PresupuestoValorUpdateComponent implements OnInit {
  presupuestoValor: IPresupuestoValor;
  isSaving: boolean;

  proyectos: IProyecto[];

  rubros: IRubros[];

  editForm = this.fb.group({
    id: [],
    descripcion: [],
    justificacion: [],
    cantidad: [],
    valorUnitario: [],
    especie: [],
    dinero: [],
    entidadFinanciadora: [],
    proyecto: [],
    rubros: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected presupuestoValorService: PresupuestoValorService,
    protected proyectoService: ProyectoService,
    protected rubrosService: RubrosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ presupuestoValor }) => {
      this.updateForm(presupuestoValor);
      this.presupuestoValor = presupuestoValor;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.rubrosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IRubros[]>) => mayBeOk.ok),
        map((response: HttpResponse<IRubros[]>) => response.body)
      )
      .subscribe((res: IRubros[]) => (this.rubros = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(presupuestoValor: IPresupuestoValor) {
    this.editForm.patchValue({
      id: presupuestoValor.id,
      descripcion: presupuestoValor.descripcion,
      justificacion: presupuestoValor.justificacion,
      cantidad: presupuestoValor.cantidad,
      valorUnitario: presupuestoValor.valorUnitario,
      especie: presupuestoValor.especie,
      dinero: presupuestoValor.dinero,
      entidadFinanciadora: presupuestoValor.entidadFinanciadora,
      proyecto: presupuestoValor.proyecto,
      rubros: presupuestoValor.rubros
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const presupuestoValor = this.createFromForm();
    if (presupuestoValor.id !== undefined) {
      this.subscribeToSaveResponse(this.presupuestoValorService.update(presupuestoValor));
    } else {
      this.subscribeToSaveResponse(this.presupuestoValorService.create(presupuestoValor));
    }
  }

  private createFromForm(): IPresupuestoValor {
    const entity = {
      ...new PresupuestoValor(),
      id: this.editForm.get(['id']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      justificacion: this.editForm.get(['justificacion']).value,
      cantidad: this.editForm.get(['cantidad']).value,
      valorUnitario: this.editForm.get(['valorUnitario']).value,
      especie: this.editForm.get(['especie']).value,
      dinero: this.editForm.get(['dinero']).value,
      entidadFinanciadora: this.editForm.get(['entidadFinanciadora']).value,
      proyecto: this.editForm.get(['proyecto']).value,
      rubros: this.editForm.get(['rubros']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPresupuestoValor>>) {
    result.subscribe((res: HttpResponse<IPresupuestoValor>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackRubrosById(index: number, item: IRubros) {
    return item.id;
  }
}
