import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IImpactosEsperados, ImpactosEsperados } from 'app/shared/model/impactos-esperados.model';
import { ImpactosEsperadosService } from './impactos-esperados.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';

@Component({
  selector: 'jhi-impactos-esperados-update',
  templateUrl: './impactos-esperados-update.component.html'
})
export class ImpactosEsperadosUpdateComponent implements OnInit {
  impactosEsperados: IImpactosEsperados;
  isSaving: boolean;

  proyectos: IProyecto[];

  editForm = this.fb.group({
    id: [],
    impacto: [],
    plazo: [],
    indicador: [],
    supuestos: [],
    proyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected impactosEsperadosService: ImpactosEsperadosService,
    protected proyectoService: ProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ impactosEsperados }) => {
      this.updateForm(impactosEsperados);
      this.impactosEsperados = impactosEsperados;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(impactosEsperados: IImpactosEsperados) {
    this.editForm.patchValue({
      id: impactosEsperados.id,
      impacto: impactosEsperados.impacto,
      plazo: impactosEsperados.plazo,
      indicador: impactosEsperados.indicador,
      supuestos: impactosEsperados.supuestos,
      proyecto: impactosEsperados.proyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const impactosEsperados = this.createFromForm();
    if (impactosEsperados.id !== undefined) {
      this.subscribeToSaveResponse(this.impactosEsperadosService.update(impactosEsperados));
    } else {
      this.subscribeToSaveResponse(this.impactosEsperadosService.create(impactosEsperados));
    }
  }

  private createFromForm(): IImpactosEsperados {
    const entity = {
      ...new ImpactosEsperados(),
      id: this.editForm.get(['id']).value,
      impacto: this.editForm.get(['impacto']).value,
      plazo: this.editForm.get(['plazo']).value,
      indicador: this.editForm.get(['indicador']).value,
      supuestos: this.editForm.get(['supuestos']).value,
      proyecto: this.editForm.get(['proyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IImpactosEsperados>>) {
    result.subscribe((res: HttpResponse<IImpactosEsperados>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
