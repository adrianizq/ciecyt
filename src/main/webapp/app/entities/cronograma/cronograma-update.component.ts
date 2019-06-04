import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICronograma, Cronograma } from 'app/shared/model/cronograma.model';
import { CronogramaService } from './cronograma.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';

@Component({
  selector: 'jhi-cronograma-update',
  templateUrl: './cronograma-update.component.html'
})
export class CronogramaUpdateComponent implements OnInit {
  cronograma: ICronograma;
  isSaving: boolean;

  proyectos: IProyecto[];
  fechaIniDp: any;
  fechaFinDp: any;

  editForm = this.fb.group({
    id: [],
    actividad: [],
    duracion: [],
    fechaIni: [],
    fechaFin: [],
    proyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected cronogramaService: CronogramaService,
    protected proyectoService: ProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cronograma }) => {
      this.updateForm(cronograma);
      this.cronograma = cronograma;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cronograma: ICronograma) {
    this.editForm.patchValue({
      id: cronograma.id,
      actividad: cronograma.actividad,
      duracion: cronograma.duracion,
      fechaIni: cronograma.fechaIni,
      fechaFin: cronograma.fechaFin,
      proyecto: cronograma.proyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cronograma = this.createFromForm();
    if (cronograma.id !== undefined) {
      this.subscribeToSaveResponse(this.cronogramaService.update(cronograma));
    } else {
      this.subscribeToSaveResponse(this.cronogramaService.create(cronograma));
    }
  }

  private createFromForm(): ICronograma {
    const entity = {
      ...new Cronograma(),
      id: this.editForm.get(['id']).value,
      actividad: this.editForm.get(['actividad']).value,
      duracion: this.editForm.get(['duracion']).value,
      fechaIni: this.editForm.get(['fechaIni']).value,
      fechaFin: this.editForm.get(['fechaFin']).value,
      proyecto: this.editForm.get(['proyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICronograma>>) {
    result.subscribe((res: HttpResponse<ICronograma>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
