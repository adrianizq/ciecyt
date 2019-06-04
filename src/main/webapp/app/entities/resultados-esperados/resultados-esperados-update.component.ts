import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IResultadosEsperados, ResultadosEsperados } from 'app/shared/model/resultados-esperados.model';
import { ResultadosEsperadosService } from './resultados-esperados.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';

@Component({
  selector: 'jhi-resultados-esperados-update',
  templateUrl: './resultados-esperados-update.component.html'
})
export class ResultadosEsperadosUpdateComponent implements OnInit {
  resultadosEsperados: IResultadosEsperados;
  isSaving: boolean;

  proyectos: IProyecto[];

  editForm = this.fb.group({
    id: [],
    resultado: [],
    indicador: [],
    beneficiario: [],
    proyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected resultadosEsperadosService: ResultadosEsperadosService,
    protected proyectoService: ProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ resultadosEsperados }) => {
      this.updateForm(resultadosEsperados);
      this.resultadosEsperados = resultadosEsperados;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(resultadosEsperados: IResultadosEsperados) {
    this.editForm.patchValue({
      id: resultadosEsperados.id,
      resultado: resultadosEsperados.resultado,
      indicador: resultadosEsperados.indicador,
      beneficiario: resultadosEsperados.beneficiario,
      proyecto: resultadosEsperados.proyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const resultadosEsperados = this.createFromForm();
    if (resultadosEsperados.id !== undefined) {
      this.subscribeToSaveResponse(this.resultadosEsperadosService.update(resultadosEsperados));
    } else {
      this.subscribeToSaveResponse(this.resultadosEsperadosService.create(resultadosEsperados));
    }
  }

  private createFromForm(): IResultadosEsperados {
    const entity = {
      ...new ResultadosEsperados(),
      id: this.editForm.get(['id']).value,
      resultado: this.editForm.get(['resultado']).value,
      indicador: this.editForm.get(['indicador']).value,
      beneficiario: this.editForm.get(['beneficiario']).value,
      proyecto: this.editForm.get(['proyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IResultadosEsperados>>) {
    result.subscribe((res: HttpResponse<IResultadosEsperados>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
