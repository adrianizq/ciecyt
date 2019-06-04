import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IPreguntas, Preguntas } from 'app/shared/model/preguntas.model';
import { PreguntasService } from './preguntas.service';
import { IElementos } from 'app/shared/model/elementos.model';
import { ElementosService } from 'app/entities/elementos';
import { IFaseTrabajo } from 'app/shared/model/fase-trabajo.model';
import { FaseTrabajoService } from 'app/entities/fase-trabajo';

@Component({
  selector: 'jhi-preguntas-update',
  templateUrl: './preguntas-update.component.html'
})
export class PreguntasUpdateComponent implements OnInit {
  preguntas: IPreguntas;
  isSaving: boolean;

  elementos: IElementos[];

  fasetrabajos: IFaseTrabajo[];

  editForm = this.fb.group({
    id: [],
    pregunta: [],
    elementos: [],
    faseTrabajo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected preguntasService: PreguntasService,
    protected elementosService: ElementosService,
    protected faseTrabajoService: FaseTrabajoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ preguntas }) => {
      this.updateForm(preguntas);
      this.preguntas = preguntas;
    });
    this.elementosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IElementos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElementos[]>) => response.body)
      )
      .subscribe((res: IElementos[]) => (this.elementos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.faseTrabajoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFaseTrabajo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFaseTrabajo[]>) => response.body)
      )
      .subscribe((res: IFaseTrabajo[]) => (this.fasetrabajos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(preguntas: IPreguntas) {
    this.editForm.patchValue({
      id: preguntas.id,
      pregunta: preguntas.pregunta,
      elementos: preguntas.elementos,
      faseTrabajo: preguntas.faseTrabajo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const preguntas = this.createFromForm();
    if (preguntas.id !== undefined) {
      this.subscribeToSaveResponse(this.preguntasService.update(preguntas));
    } else {
      this.subscribeToSaveResponse(this.preguntasService.create(preguntas));
    }
  }

  private createFromForm(): IPreguntas {
    const entity = {
      ...new Preguntas(),
      id: this.editForm.get(['id']).value,
      pregunta: this.editForm.get(['pregunta']).value,
      elementos: this.editForm.get(['elementos']).value,
      faseTrabajo: this.editForm.get(['faseTrabajo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPreguntas>>) {
    result.subscribe((res: HttpResponse<IPreguntas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackElementosById(index: number, item: IElementos) {
    return item.id;
  }

  trackFaseTrabajoById(index: number, item: IFaseTrabajo) {
    return item.id;
  }
}
