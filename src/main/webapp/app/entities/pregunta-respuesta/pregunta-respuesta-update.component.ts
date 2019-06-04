import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPreguntaRespuesta, PreguntaRespuesta } from 'app/shared/model/pregunta-respuesta.model';
import { PreguntaRespuestaService } from './pregunta-respuesta.service';
import { IPreguntas } from 'app/shared/model/preguntas.model';
import { PreguntasService } from 'app/entities/preguntas';
import { ITipoPregunta } from 'app/shared/model/tipo-pregunta.model';
import { TipoPreguntaService } from 'app/entities/tipo-pregunta';
import { IIntegrantesProyecto } from 'app/shared/model/integrantes-proyecto.model';
import { IntegrantesProyectoService } from 'app/entities/integrantes-proyecto';

@Component({
  selector: 'jhi-pregunta-respuesta-update',
  templateUrl: './pregunta-respuesta-update.component.html'
})
export class PreguntaRespuestaUpdateComponent implements OnInit {
  preguntaRespuesta: IPreguntaRespuesta;
  isSaving: boolean;

  preguntas: IPreguntas[];

  tipopreguntas: ITipoPregunta[];

  integrantesproyectos: IIntegrantesProyecto[];

  editForm = this.fb.group({
    id: [],
    preguntas: [],
    tipoPregunta: [],
    integrantesProyecto: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected preguntaRespuestaService: PreguntaRespuestaService,
    protected preguntasService: PreguntasService,
    protected tipoPreguntaService: TipoPreguntaService,
    protected integrantesProyectoService: IntegrantesProyectoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ preguntaRespuesta }) => {
      this.updateForm(preguntaRespuesta);
      this.preguntaRespuesta = preguntaRespuesta;
    });
    this.preguntasService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPreguntas[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPreguntas[]>) => response.body)
      )
      .subscribe((res: IPreguntas[]) => (this.preguntas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.tipoPreguntaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITipoPregunta[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipoPregunta[]>) => response.body)
      )
      .subscribe((res: ITipoPregunta[]) => (this.tipopreguntas = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.integrantesProyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIntegrantesProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIntegrantesProyecto[]>) => response.body)
      )
      .subscribe((res: IIntegrantesProyecto[]) => (this.integrantesproyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(preguntaRespuesta: IPreguntaRespuesta) {
    this.editForm.patchValue({
      id: preguntaRespuesta.id,
      preguntas: preguntaRespuesta.preguntas,
      tipoPregunta: preguntaRespuesta.tipoPregunta,
      integrantesProyecto: preguntaRespuesta.integrantesProyecto
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const preguntaRespuesta = this.createFromForm();
    if (preguntaRespuesta.id !== undefined) {
      this.subscribeToSaveResponse(this.preguntaRespuestaService.update(preguntaRespuesta));
    } else {
      this.subscribeToSaveResponse(this.preguntaRespuestaService.create(preguntaRespuesta));
    }
  }

  private createFromForm(): IPreguntaRespuesta {
    const entity = {
      ...new PreguntaRespuesta(),
      id: this.editForm.get(['id']).value,
      preguntas: this.editForm.get(['preguntas']).value,
      tipoPregunta: this.editForm.get(['tipoPregunta']).value,
      integrantesProyecto: this.editForm.get(['integrantesProyecto']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreguntaRespuesta>>) {
    result.subscribe((res: HttpResponse<IPreguntaRespuesta>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPreguntasById(index: number, item: IPreguntas) {
    return item.id;
  }

  trackTipoPreguntaById(index: number, item: ITipoPregunta) {
    return item.id;
  }

  trackIntegrantesProyectoById(index: number, item: IIntegrantesProyecto) {
    return item.id;
  }
}
