import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IElementosProyecto, ElementosProyecto } from 'app/shared/model/elementos-proyecto.model';
import { ElementosProyectoService } from './elementos-proyecto.service';
import { IProyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from 'app/entities/proyecto';
import { IElementos } from 'app/shared/model/elementos.model';
import { ElementosService } from 'app/entities/elementos';

@Component({
  selector: 'jhi-elementos-proyecto-update',
  templateUrl: './elementos-proyecto-update.component.html'
})
export class ElementosProyectoUpdateComponent implements OnInit {
  elementosProyecto: IElementosProyecto;
  isSaving: boolean;

  proyectos: IProyecto[];

  elementos: IElementos[];

  editForm = this.fb.group({
    id: [],
    dato: [],
    proyecto: [],
    elementos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected elementosProyectoService: ElementosProyectoService,
    protected proyectoService: ProyectoService,
    protected elementosService: ElementosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ elementosProyecto }) => {
      this.updateForm(elementosProyecto);
      this.elementosProyecto = elementosProyecto;
    });
    this.proyectoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProyecto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProyecto[]>) => response.body)
      )
      .subscribe((res: IProyecto[]) => (this.proyectos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.elementosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IElementos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IElementos[]>) => response.body)
      )
      .subscribe((res: IElementos[]) => (this.elementos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(elementosProyecto: IElementosProyecto) {
    this.editForm.patchValue({
      id: elementosProyecto.id,
      dato: elementosProyecto.dato,
      proyecto: elementosProyecto.proyecto,
      elementos: elementosProyecto.elementos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const elementosProyecto = this.createFromForm();
    if (elementosProyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.elementosProyectoService.update(elementosProyecto));
    } else {
      this.subscribeToSaveResponse(this.elementosProyectoService.create(elementosProyecto));
    }
  }

  private createFromForm(): IElementosProyecto {
    const entity = {
      ...new ElementosProyecto(),
      id: this.editForm.get(['id']).value,
      dato: this.editForm.get(['dato']).value,
      proyecto: this.editForm.get(['proyecto']).value,
      elementos: this.editForm.get(['elementos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IElementosProyecto>>) {
    result.subscribe((res: HttpResponse<IElementosProyecto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackElementosById(index: number, item: IElementos) {
    return item.id;
  }
}
