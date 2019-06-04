import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmpresas, Empresas } from 'app/shared/model/empresas.model';
import { EmpresasService } from './empresas.service';
import { IEntidadFinanciadora } from 'app/shared/model/entidad-financiadora.model';
import { EntidadFinanciadoraService } from 'app/entities/entidad-financiadora';

@Component({
  selector: 'jhi-empresas-update',
  templateUrl: './empresas-update.component.html'
})
export class EmpresasUpdateComponent implements OnInit {
  empresas: IEmpresas;
  isSaving: boolean;

  entidadfinanciadoras: IEntidadFinanciadora[];

  editForm = this.fb.group({
    id: [],
    entidad: [],
    entidadFinanciadora: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected empresasService: EmpresasService,
    protected entidadFinanciadoraService: EntidadFinanciadoraService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ empresas }) => {
      this.updateForm(empresas);
      this.empresas = empresas;
    });
    this.entidadFinanciadoraService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IEntidadFinanciadora[]>) => mayBeOk.ok),
        map((response: HttpResponse<IEntidadFinanciadora[]>) => response.body)
      )
      .subscribe((res: IEntidadFinanciadora[]) => (this.entidadfinanciadoras = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(empresas: IEmpresas) {
    this.editForm.patchValue({
      id: empresas.id,
      entidad: empresas.entidad,
      entidadFinanciadora: empresas.entidadFinanciadora
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const empresas = this.createFromForm();
    if (empresas.id !== undefined) {
      this.subscribeToSaveResponse(this.empresasService.update(empresas));
    } else {
      this.subscribeToSaveResponse(this.empresasService.create(empresas));
    }
  }

  private createFromForm(): IEmpresas {
    const entity = {
      ...new Empresas(),
      id: this.editForm.get(['id']).value,
      entidad: this.editForm.get(['entidad']).value,
      entidadFinanciadora: this.editForm.get(['entidadFinanciadora']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmpresas>>) {
    result.subscribe((res: HttpResponse<IEmpresas>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackEntidadFinanciadoraById(index: number, item: IEntidadFinanciadora) {
    return item.id;
  }
}
