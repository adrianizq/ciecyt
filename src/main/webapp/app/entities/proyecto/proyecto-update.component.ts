import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProyecto, Proyecto } from 'app/shared/model/proyecto.model';
import { ProyectoService } from './proyecto.service';
import { ILineaDeInvestigacion } from 'app/shared/model/linea-de-investigacion.model';
import { LineaDeInvestigacionService } from 'app/entities/linea-de-investigacion';
import { IGrupoSemillero } from 'app/shared/model/grupo-semillero.model';
import { GrupoSemilleroService } from 'app/entities/grupo-semillero';
import { IFacultad } from 'app/shared/model/facultad.model';
import { FacultadService } from 'app/entities/facultad';
import { IModalidadDeGrado } from 'app/shared/model/modalidad-de-grado.model';
import { ModalidadDeGradoService } from 'app/entities/modalidad-de-grado';

@Component({
  selector: 'jhi-proyecto-update',
  templateUrl: './proyecto-update.component.html'
})
export class ProyectoUpdateComponent implements OnInit {
  proyecto: IProyecto;
  isSaving: boolean;

  lineadeinvestigacions: ILineaDeInvestigacion[];

  gruposemilleros: IGrupoSemillero[];

  facultads: IFacultad[];

  modalidaddegrados: IModalidadDeGrado[];
  fechaIniDp: any;
  fechaFinDp: any;

  editForm = this.fb.group({
    id: [],
    titulo: [],
    url: [],
    lugarEjecucion: [],
    duracion: [],
    fechaIni: [],
    fechaFin: [],
    contrapartidaPesos: [],
    contrapartidaEspecie: [],
    palabraClave: [],
    convocatoria: [],
    lineaDeInvestigacion: [],
    grupoSemillero: [],
    facultad: [],
    modalidadDeGrado: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected proyectoService: ProyectoService,
    protected lineaDeInvestigacionService: LineaDeInvestigacionService,
    protected grupoSemilleroService: GrupoSemilleroService,
    protected facultadService: FacultadService,
    protected modalidadDeGradoService: ModalidadDeGradoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ proyecto }) => {
      this.updateForm(proyecto);
      this.proyecto = proyecto;
    });
    this.lineaDeInvestigacionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILineaDeInvestigacion[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILineaDeInvestigacion[]>) => response.body)
      )
      .subscribe(
        (res: ILineaDeInvestigacion[]) => (this.lineadeinvestigacions = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.grupoSemilleroService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IGrupoSemillero[]>) => mayBeOk.ok),
        map((response: HttpResponse<IGrupoSemillero[]>) => response.body)
      )
      .subscribe((res: IGrupoSemillero[]) => (this.gruposemilleros = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.facultadService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFacultad[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFacultad[]>) => response.body)
      )
      .subscribe((res: IFacultad[]) => (this.facultads = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.modalidadDeGradoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IModalidadDeGrado[]>) => mayBeOk.ok),
        map((response: HttpResponse<IModalidadDeGrado[]>) => response.body)
      )
      .subscribe((res: IModalidadDeGrado[]) => (this.modalidaddegrados = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(proyecto: IProyecto) {
    this.editForm.patchValue({
      id: proyecto.id,
      titulo: proyecto.titulo,
      url: proyecto.url,
      lugarEjecucion: proyecto.lugarEjecucion,
      duracion: proyecto.duracion,
      fechaIni: proyecto.fechaIni,
      fechaFin: proyecto.fechaFin,
      contrapartidaPesos: proyecto.contrapartidaPesos,
      contrapartidaEspecie: proyecto.contrapartidaEspecie,
      palabraClave: proyecto.palabraClave,
      convocatoria: proyecto.convocatoria,
      lineaDeInvestigacion: proyecto.lineaDeInvestigacion,
      grupoSemillero: proyecto.grupoSemillero,
      facultad: proyecto.facultad,
      modalidadDeGrado: proyecto.modalidadDeGrado
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const proyecto = this.createFromForm();
    if (proyecto.id !== undefined) {
      this.subscribeToSaveResponse(this.proyectoService.update(proyecto));
    } else {
      this.subscribeToSaveResponse(this.proyectoService.create(proyecto));
    }
  }

  private createFromForm(): IProyecto {
    const entity = {
      ...new Proyecto(),
      id: this.editForm.get(['id']).value,
      titulo: this.editForm.get(['titulo']).value,
      url: this.editForm.get(['url']).value,
      lugarEjecucion: this.editForm.get(['lugarEjecucion']).value,
      duracion: this.editForm.get(['duracion']).value,
      fechaIni: this.editForm.get(['fechaIni']).value,
      fechaFin: this.editForm.get(['fechaFin']).value,
      contrapartidaPesos: this.editForm.get(['contrapartidaPesos']).value,
      contrapartidaEspecie: this.editForm.get(['contrapartidaEspecie']).value,
      palabraClave: this.editForm.get(['palabraClave']).value,
      convocatoria: this.editForm.get(['convocatoria']).value,
      lineaDeInvestigacion: this.editForm.get(['lineaDeInvestigacion']).value,
      grupoSemillero: this.editForm.get(['grupoSemillero']).value,
      facultad: this.editForm.get(['facultad']).value,
      modalidadDeGrado: this.editForm.get(['modalidadDeGrado']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProyecto>>) {
    result.subscribe((res: HttpResponse<IProyecto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackLineaDeInvestigacionById(index: number, item: ILineaDeInvestigacion) {
    return item.id;
  }

  trackGrupoSemilleroById(index: number, item: IGrupoSemillero) {
    return item.id;
  }

  trackFacultadById(index: number, item: IFacultad) {
    return item.id;
  }

  trackModalidadDeGradoById(index: number, item: IModalidadDeGrado) {
    return item.id;
  }
}
